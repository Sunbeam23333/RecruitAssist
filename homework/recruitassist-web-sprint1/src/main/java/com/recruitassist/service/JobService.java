package com.recruitassist.service;

import com.recruitassist.model.ActionResult;
import com.recruitassist.model.JobPosting;
import com.recruitassist.model.JobStatus;
import com.recruitassist.model.UserProfile;
import com.recruitassist.model.UserRole;
import com.recruitassist.repository.IdCounterRepository;
import com.recruitassist.repository.JobRepository;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class JobService {
    private final JobRepository jobRepository;
    private final IdCounterRepository idCounterRepository;
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    public JobService(JobRepository jobRepository, IdCounterRepository idCounterRepository) {
        this.jobRepository = jobRepository;
        this.idCounterRepository = idCounterRepository;
    }

    public List<JobPosting> listAllJobs() {
        lock.writeLock().lock();
        try {
            // Expired jobs are normalized during reads so the UI always reflects current availability.
            for (JobPosting job : jobRepository.findAll()) {
                if (job.isOpen() && job.isExpired()) {
                    job.setStatus(JobStatus.CLOSED);
                    jobRepository.save(job);
                }
            }
            return jobRepository.findAll();
        } finally {
            lock.writeLock().unlock();
        }
    }

    public List<JobPosting> listOpenJobs() {
        return listAllJobs().stream()
                .filter(JobPosting::isOpen)
                .toList();
    }

    public List<JobPosting> listJobsForOwner(String ownerId) {
        return listAllJobs().stream()
                .filter(job -> job.getOwnerId().equalsIgnoreCase(clean(ownerId)))
                .toList();
    }

    public Optional<JobPosting> findById(String jobId) {
        return listAllJobs().stream()
                .filter(job -> job.getJobId().equalsIgnoreCase(clean(jobId)))
                .findFirst();
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

            // Sprint 1 keeps job creation straightforward and persists the posting as entered.
            JobPosting jobPosting = new JobPosting();
            jobPosting.setJobId(idCounterRepository.nextJobId());
            jobPosting.setOwnerId(actor.getUserId());
            jobPosting.setTitle(input.title());
            jobPosting.setModuleCode(input.moduleCode());
            jobPosting.setDescription(input.description());
            jobPosting.setRequiredSkills(input.requiredSkills());
            jobPosting.setPreferredSkills(input.preferredSkills());
            jobPosting.setDeadline(input.deadline());
            jobPosting.setQuota(input.quota());
            jobPosting.setWorkloadHours(input.workloadHours());
            jobPosting.setStatus(JobStatus.OPEN);

            jobRepository.save(jobPosting);
            return ActionResult.success("Job " + jobPosting.getJobId() + " created successfully.");
        } finally {
            lock.writeLock().unlock();
        }
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
        String cleanTitle = cleanText(title, 120);
        String cleanModuleCode = cleanText(moduleCode, 32).toUpperCase();
        String cleanDescription = cleanText(description, 3000);
        List<String> requiredSkills = parseSkills(requiredSkillsRaw);
        List<String> preferredSkills = parseSkills(preferredSkillsRaw);
        String cleanDeadline = cleanText(deadline, 32);
        int quota = parsePositiveInt(quotaRaw, "quota");
        int workloadHours = parsePositiveInt(workloadHoursRaw, "workload hours");

        if (cleanTitle.isBlank() || cleanModuleCode.isBlank() || cleanDescription.isBlank()) {
            throw new IllegalArgumentException("Title, module code and description are required.");
        }
        if (requiredSkills.isEmpty()) {
            throw new IllegalArgumentException("Please provide at least one required skill.");
        }
        try {
            LocalDate.parse(cleanDeadline);
        } catch (Exception ex) {
            throw new IllegalArgumentException("Please provide a valid deadline date.");
        }

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

    private List<String> parseSkills(String rawSkills) {
        if (rawSkills == null || rawSkills.isBlank()) {
            return List.of();
        }
        return rawSkills.lines()
                .flatMap(line -> Arrays.stream(line.split("[,;]")))
                .map(skill -> cleanText(skill, 60))
                .filter(skill -> !skill.isBlank())
                .distinct()
                .toList();
    }

    private int parsePositiveInt(String rawValue, String fieldName) {
        try {
            int parsed = Integer.parseInt(clean(rawValue));
            if (parsed <= 0) {
                throw new NumberFormatException("non-positive");
            }
            return parsed;
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException("Please provide a valid positive number for " + fieldName + '.');
        }
    }

    private String cleanText(String rawValue, int maxLength) {
        if (rawValue == null) {
            return "";
        }
        String cleaned = rawValue
                .replace('<', ' ')
                .replace('>', ' ')
                .replaceAll("[\\p{Cntrl}&&[^\\n\\r\\t]]", " ")
                .replace("\r", "")
                .trim();
        if (cleaned.length() <= maxLength) {
            return cleaned;
        }
        return cleaned.substring(0, maxLength).trim();
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
