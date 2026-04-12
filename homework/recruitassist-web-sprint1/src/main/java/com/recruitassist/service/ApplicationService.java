package com.recruitassist.service;

import com.recruitassist.model.ActionResult;
import com.recruitassist.model.ApplicationRecord;
import com.recruitassist.model.ApplicationStatus;
import com.recruitassist.model.JobPosting;
import com.recruitassist.model.JobStatus;
import com.recruitassist.model.UserProfile;
import com.recruitassist.model.UserRole;
import com.recruitassist.repository.ApplicationRepository;
import com.recruitassist.repository.IdCounterRepository;
import com.recruitassist.repository.JobRepository;

import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ApplicationService {
    private final ApplicationRepository applicationRepository;
    private final JobRepository jobRepository;
    private final IdCounterRepository idCounterRepository;
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    public ApplicationService(
            ApplicationRepository applicationRepository,
            JobRepository jobRepository,
            IdCounterRepository idCounterRepository) {
        this.applicationRepository = applicationRepository;
        this.jobRepository = jobRepository;
        this.idCounterRepository = idCounterRepository;
    }

    public List<ApplicationRecord> findAll() {
        lock.readLock().lock();
        try {
            return applicationRepository.findAll();
        } finally {
            lock.readLock().unlock();
        }
    }

    public List<ApplicationRecord> findByApplicantId(String applicantId) {
        lock.readLock().lock();
        try {
            return applicationRepository.findByApplicantId(applicantId);
        } finally {
            lock.readLock().unlock();
        }
    }

    public List<ApplicationRecord> findByJobId(String jobId) {
        lock.readLock().lock();
        try {
            return applicationRepository.findByJobId(jobId);
        } finally {
            lock.readLock().unlock();
        }
    }

    public Optional<ApplicationRecord> findExistingApplication(String applicantId, String jobId) {
        lock.readLock().lock();
        try {
            return applicationRepository.findByApplicantId(applicantId).stream()
                    .filter(application -> application.getJobId().equalsIgnoreCase(jobId))
                    .findFirst();
        } finally {
            lock.readLock().unlock();
        }
    }

    public Map<String, ApplicationRecord> mapByJobIdForApplicant(String applicantId) {
        Map<String, ApplicationRecord> result = new LinkedHashMap<>();
        for (ApplicationRecord application : findByApplicantId(applicantId)) {
            result.putIfAbsent(application.getJobId(), application);
        }
        return result;
    }

    public Map<String, Long> countByJobId() {
        // Keep the Sprint 1 dashboard simple by deriving counts directly from stored applications.
        Map<String, Long> counts = new LinkedHashMap<>();
        for (ApplicationRecord application : findAll()) {
            counts.merge(application.getJobId(), 1L, Long::sum);
        }
        return counts;
    }

    public ActionResult submitApplication(UserProfile user, String jobId) {
        lock.writeLock().lock();
        try {
            if (user == null || user.getRole() != UserRole.TA) {
                return ActionResult.failure("Only teaching assistants can apply for jobs.");
            }
            if (!user.isProfileReady()) {
                return ActionResult.failure("Please complete your profile before applying.");
            }

            JobPosting job = jobRepository.findById(jobId == null ? "" : jobId.trim()).orElse(null);
            if (job == null) {
                return ActionResult.failure("The selected job does not exist.");
            }
            if (job.isExpired()) {
                job.setStatus(JobStatus.CLOSED);
                jobRepository.save(job);
                return ActionResult.failure("This job has passed its deadline and is no longer accepting applications.");
            }
            if (!job.isOpen()) {
                return ActionResult.failure("This job is already closed.");
            }
            if (findExistingApplication(user.getUserId(), job.getJobId()).isPresent()) {
                return ActionResult.failure("You have already applied for this job.");
            }

            // The first iteration stores only the essential application fields.
            ApplicationRecord application = new ApplicationRecord();
            application.setApplicationId(idCounterRepository.nextApplicationId());
            application.setJobId(job.getJobId());
            application.setApplicantId(user.getUserId());
            application.setApplyTime(Instant.now().toString());
            application.setStatus(ApplicationStatus.SUBMITTED);
            applicationRepository.save(application);
            return ActionResult.success("Application submitted successfully. Current status: Pending.");
        } finally {
            lock.writeLock().unlock();
        }
    }
}
