package com.recruitassist.service;

import com.recruitassist.model.ActionResult;
import com.recruitassist.model.ApplicationStatus;
import com.recruitassist.model.JobPosting;
import com.recruitassist.model.JobStatus;
import com.recruitassist.model.UserProfile;
import com.recruitassist.model.UserRole;
import com.recruitassist.repository.ApplicationRepository;
import com.recruitassist.repository.AuditRepository;
import com.recruitassist.repository.IdCounterRepository;
import com.recruitassist.repository.JobRepository;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.Function;
import java.util.stream.Collectors;

public class JobService {
    private final JobRepository jobRepository;
    private final ApplicationRepository applicationRepository;
    private final IdCounterRepository idCounterRepository;
    private final AuditRepository auditRepository;
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    public JobService(
            JobRepository jobRepository,
            ApplicationRepository applicationRepository,
            IdCounterRepository idCounterRepository,
            AuditRepository auditRepository) {
        this.jobRepository = jobRepository;
        this.applicationRepository = applicationRepository;
        this.idCounterRepository = idCounterRepository;
        this.auditRepository = auditRepository;
    }

    public List<JobPosting> listAllJobs() {
        lock.readLock().lock();
        try {
            return jobRepository.findAll();
        } finally {
            lock.readLock().unlock();
        }
    }

    public List<JobPosting> listOpenJobs() {
        lock.readLock().lock();
        try {
            return jobRepository.findAll().stream()
                    .filter(JobPosting::isOpen)
                    .toList();
        } finally {
            lock.readLock().unlock();
        }
    }

    public List<JobPosting> listJobsForOwner(String ownerId) {
        lock.readLock().lock();
        try {
            return jobRepository.findByOwnerId(ownerId);
        } finally {
            lock.readLock().unlock();
        }
    }

    public Optional<JobPosting> findById(String jobId) {
        lock.readLock().lock();
        try {
            return jobRepository.findById(jobId);
        } finally {
            lock.readLock().unlock();
        }
    }

    public Map<String, JobPosting> indexById() {
        return listAllJobs().stream()
                .collect(Collectors.toMap(
                        JobPosting::getJobId,
                        Function.identity(),
                        (left, right) -> left,
                        LinkedHashMap::new));
    }

    public ActionResult createJob(
            UserProfile actor,
            String title,
            String moduleCode,
            String description,
            String requiredSkillsRaw,
            String preferredSkillsRaw,
            String deadline,
            String quotaRaw,
            String workloadHoursRaw) {
        if (actor == null || actor.getRole() != UserRole.MO) {
            return ActionResult.failure("Only module organisers can create jobs.");
        }

        lock.writeLock().lock();
        try {
            ValidatedJobInput input;
            try {
                input = validateJobInput(
                        title,
                        moduleCode,
                        description,
                        requiredSkillsRaw,
                        preferredSkillsRaw,
                        deadline,
                        quotaRaw,
                        workloadHoursRaw);
            } catch (IllegalArgumentException ex) {
                return ActionResult.failure(ex.getMessage());
            }

            JobPosting jobPosting = new JobPosting();
            jobPosting.setJobId(idCounterRepository.nextJobId());
            jobPosting.setOwnerId(actor.getUserId());
            jobPosting.setStatus(JobStatus.OPEN);
            applyJobInput(jobPosting, input);

            jobRepository.save(jobPosting);
            auditRepository.append(
                    actor.getUserId(),
                    "CREATE_JOB",
                    jobPosting.getJobId(),
                    "SUCCESS",
                    input.moduleCode() + " · " + input.title());
            return ActionResult.success("Job " + jobPosting.getJobId() + " created successfully.");
        } finally {
            lock.writeLock().unlock();
        }
    }

    public ActionResult updateJob(
            UserProfile actor,
            String jobId,
            String title,
            String moduleCode,
            String description,
            String requiredSkillsRaw,
            String preferredSkillsRaw,
            String deadline,
            String quotaRaw,
            String workloadHoursRaw) {
        if (actor == null || actor.getRole() != UserRole.MO) {
            return ActionResult.failure("Only module organisers can update jobs.");
        }

        lock.writeLock().lock();
        try {
            String cleanJobId = clean(jobId);
            if (cleanJobId.isBlank()) {
                return ActionResult.failure("A valid job id is required.");
            }

            JobPosting existingJob = jobRepository.findById(cleanJobId).orElse(null);
            if (existingJob == null) {
                return ActionResult.failure("The selected job could not be found.");
            }
            if (!Objects.equals(existingJob.getOwnerId(), actor.getUserId())) {
                return ActionResult.failure("You can only edit jobs that you own.");
            }

            ValidatedJobInput input;
            try {
                input = validateJobInput(
                        title,
                        moduleCode,
                        description,
                        requiredSkillsRaw,
                        preferredSkillsRaw,
                        deadline,
                        quotaRaw,
                        workloadHoursRaw);
            } catch (IllegalArgumentException ex) {
                return ActionResult.failure(ex.getMessage());
            }

            long acceptedCount = countAcceptedApplications(cleanJobId);
            if (input.quota() < acceptedCount) {
                return ActionResult.failure("Quota cannot be lower than the current accepted count of " + acceptedCount + '.');
            }

            applyJobInput(existingJob, input);
            jobRepository.save(existingJob);
            auditRepository.append(
                    actor.getUserId(),
                    "UPDATE_JOB",
                    existingJob.getJobId(),
                    "SUCCESS",
                    input.moduleCode() + " · " + input.title());
            return ActionResult.success("Job " + existingJob.getJobId() + " updated successfully.");
        } finally {
            lock.writeLock().unlock();
        }
    }

    public ActionResult changeJobStatus(UserProfile actor, String jobId, JobStatus targetStatus) {
        if (actor == null || actor.getRole() != UserRole.MO) {
            return ActionResult.failure("Only module organisers can change job status.");
        }
        if (targetStatus == null) {
            return ActionResult.failure("Please choose a valid job status.");
        }

        lock.writeLock().lock();
        try {
            String cleanJobId = clean(jobId);
            JobPosting jobPosting = jobRepository.findById(cleanJobId).orElse(null);
            if (jobPosting == null) {
                return ActionResult.failure("The selected job could not be found.");
            }
            if (!Objects.equals(jobPosting.getOwnerId(), actor.getUserId())) {
                return ActionResult.failure("You can only manage jobs that you own.");
            }
            if (jobPosting.getStatus() == targetStatus) {
                return ActionResult.success("Job " + cleanJobId + " is already " + targetStatus.getLabel().toLowerCase() + '.');
            }

            jobPosting.setStatus(targetStatus);
            jobRepository.save(jobPosting);
            auditRepository.append(
                    actor.getUserId(),
                    "CHANGE_JOB_STATUS",
                    cleanJobId,
                    "SUCCESS",
                    targetStatus.getCode());

            if (targetStatus == JobStatus.CLOSED) {
                return ActionResult.success("Applications are now closed for job " + cleanJobId + '.');
            }
            return ActionResult.success("Job " + cleanJobId + " has been reopened for applications.");
        } finally {
            lock.writeLock().unlock();
        }
    }

    private long countAcceptedApplications(String jobId) {
        return applicationRepository.findByJobId(jobId).stream()
                .filter(application -> application.getStatus() == ApplicationStatus.ACCEPTED)
                .count();
    }

    private ValidatedJobInput validateJobInput(
            String title,
            String moduleCode,
            String description,
            String requiredSkillsRaw,
            String preferredSkillsRaw,
            String deadline,
            String quotaRaw,
            String workloadHoursRaw) {
        String cleanTitle = clean(title);
        String cleanModuleCode = clean(moduleCode).toUpperCase();
        String cleanDescription = clean(description);
        String cleanDeadline = clean(deadline);
        List<String> requiredSkills = parseSkills(requiredSkillsRaw);
        List<String> preferredSkills = parseSkills(preferredSkillsRaw);

        if (cleanTitle.isBlank() || cleanModuleCode.isBlank() || cleanDescription.isBlank() || cleanDeadline.isBlank()) {
            throw new IllegalArgumentException("Title, module code, description and deadline are all required.");
        }
        if (requiredSkills.isEmpty()) {
            throw new IllegalArgumentException("Please provide at least one required skill.");
        }

        int quota = parsePositiveInt(quotaRaw, "quota");
        int workloadHours = parsePositiveInt(workloadHoursRaw, "workload hours");
        return new ValidatedJobInput(
                cleanTitle,
                cleanModuleCode,
                cleanDescription,
                requiredSkills,
                preferredSkills,
                cleanDeadline,
                quota,
                workloadHours);
    }

    private void applyJobInput(JobPosting jobPosting, ValidatedJobInput input) {
        jobPosting.setTitle(input.title());
        jobPosting.setModuleCode(input.moduleCode());
        jobPosting.setDescription(input.description());
        jobPosting.setRequiredSkills(input.requiredSkills());
        jobPosting.setPreferredSkills(input.preferredSkills());
        jobPosting.setDeadline(input.deadline());
        jobPosting.setQuota(input.quota());
        jobPosting.setWorkloadHours(input.workloadHours());
    }

    private List<String> parseSkills(String rawValue) {
        if (rawValue == null || rawValue.isBlank()) {
            return List.of();
        }
        return rawValue.lines()
                .flatMap(line -> Arrays.stream(line.split("[,;]")))
                .map(String::trim)
                .filter(value -> !value.isBlank())
                .distinct()
                .toList();
    }

    private int parsePositiveInt(String rawValue, String fieldName) {
        try {
            int parsed = Integer.parseInt(clean(rawValue));
            if (parsed <= 0) {
                throw new IllegalArgumentException("Please enter a positive number for " + fieldName + '.');
            }
            return parsed;
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException("Please enter a valid number for " + fieldName + '.');
        }
    }

    private String clean(String rawValue) {
        return rawValue == null ? "" : rawValue.trim();
    }

    private record ValidatedJobInput(
            String title,
            String moduleCode,
            String description,
            List<String> requiredSkills,
            List<String> preferredSkills,
            String deadline,
            int quota,
            int workloadHours) {
    }
}
