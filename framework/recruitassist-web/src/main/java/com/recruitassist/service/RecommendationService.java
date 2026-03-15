package com.recruitassist.service;

import com.recruitassist.model.ApplicationRecord;
import com.recruitassist.model.ApplicationStatus;
import com.recruitassist.model.JobPosting;
import com.recruitassist.model.SystemConfig;
import com.recruitassist.model.UserProfile;
import com.recruitassist.model.view.JobRecommendation;
import com.recruitassist.repository.ApplicationRepository;
import com.recruitassist.repository.JobRepository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RecommendationService {
    private static final Pattern TOKEN_PATTERN = Pattern.compile("[a-z0-9]{2,}");
    private static final Set<String> STOP_WORDS = Set.of(
            "the",
            "and",
            "for",
            "with",
            "that",
            "this",
            "will",
            "into",
            "from",
            "your",
            "their",
            "during",
            "while",
            "able",
            "need",
            "role",
            "support",
            "assist",
            "help",
            "provide",
            "using",
            "used",
            "weekly",
            "students",
            "student",
            "teaching",
            "assistant",
            "module",
            "sessions",
            "session");
    private static final Set<String> EVIDENCE_KEYWORDS = Set.of(
            "lab",
            "labs",
            "marking",
            "marked",
            "assessment",
            "assessments",
            "debugging",
            "debug",
            "tutoring",
            "tutor",
            "mentoring",
            "mentor",
            "invigilation",
            "invigilate",
            "communication",
            "organisation",
            "support",
            "teaching",
            "students",
            "quizzes",
            "coursework");

    private final JobRepository jobRepository;
    private final ApplicationRepository applicationRepository;
    private final WorkloadService workloadService;
    private final SystemConfig systemConfig;

    public RecommendationService(
            JobRepository jobRepository,
            ApplicationRepository applicationRepository,
            WorkloadService workloadService,
            SystemConfig systemConfig) {
        this.jobRepository = jobRepository;
        this.applicationRepository = applicationRepository;
        this.workloadService = workloadService;
        this.systemConfig = systemConfig;
    }

    public List<JobRecommendation> recommendJobsFor(UserProfile user) {
        return jobRepository.findAll().stream()
                .filter(JobPosting::isOpen)
                .map(job -> recommend(user, job))
                .sorted(Comparator.comparingDouble(JobRecommendation::getScore).reversed())
                .toList();
    }

    public JobRecommendation recommend(UserProfile user, JobPosting job) {
        Set<String> normalizedUserSkills = user.getSkills().stream()
                .map(this::normalizePhrase)
                .filter(skill -> !skill.isBlank())
                .collect(LinkedHashSet::new, LinkedHashSet::add, LinkedHashSet::addAll);

        List<String> matchedSkills = new ArrayList<>();
        List<String> missingSkills = new ArrayList<>();
        for (String skill : job.getRequiredSkills()) {
            if (normalizedUserSkills.contains(normalizePhrase(skill))) {
                matchedSkills.add(skill);
            } else {
                missingSkills.add(skill);
            }
        }

        List<String> preferredMatches = job.getPreferredSkills().stream()
                .filter(skill -> normalizedUserSkills.contains(normalizePhrase(skill)))
                .toList();

        int requiredTotal = job.getRequiredSkills().size();
        int preferredTotal = job.getPreferredSkills().size();
        int currentWorkload = workloadService.workloadForUser(user.getUserId());
        int workloadThreshold = Math.max(1, workloadService.getThreshold());
        int projectedWorkload = currentWorkload + Math.max(job.getWorkloadHours(), 0);

        double skillScore = calculateSkillScore(matchedSkills, preferredMatches, requiredTotal, preferredTotal);
        double availabilityScore = calculateAvailabilityScore(user.getAvailability());
        double experienceScore = calculateExperienceScore(user, job);
        double workloadBalance = calculateWorkloadBalance(currentWorkload, job.getWorkloadHours(), workloadThreshold);
        double profileEvidenceScore = calculateProfileEvidenceScore(user, job, matchedSkills, preferredMatches);
        CompetitionSnapshot competition = calculateCompetition(job);

        SystemConfig.RecommendationConfig weights = systemConfig.getRecommendation();
        double configuredTotal = weights.getSkillMatchWeight()
                + weights.getAvailabilityWeight()
                + weights.getExperienceWeight()
                + weights.getWorkloadBalanceWeight();
        double safeTotal = configuredTotal <= 0.0 ? 1.0 : configuredTotal;

        double coreScore = ((skillScore * weights.getSkillMatchWeight())
                + (availabilityScore * weights.getAvailabilityWeight())
                + (experienceScore * weights.getExperienceWeight())
                + (workloadBalance * weights.getWorkloadBalanceWeight())) / safeTotal;
        double score = clamp((coreScore * 0.82) + (profileEvidenceScore * 0.1) + (competition.score() * 0.08));

        List<String> evidenceHits = collectEvidenceHits(user, job, matchedSkills, preferredMatches);
        List<String> reasons = new ArrayList<>();
        reasons.add(buildSkillReason(matchedSkills, preferredMatches, missingSkills, requiredTotal, preferredTotal));
        reasons.add(buildAvailabilityReason(user.getAvailability(), availabilityScore));
        reasons.add(buildExperienceReason(user, experienceScore, evidenceHits));
        reasons.add(buildProfileEvidenceReason(profileEvidenceScore, evidenceHits));
        reasons.add(buildWorkloadReason(currentWorkload, projectedWorkload, workloadThreshold));
        reasons.add(buildCompetitionReason(competition));

        return new JobRecommendation(
                job,
                score,
                matchedSkills,
                missingSkills,
                preferredMatches,
                reasons,
                skillScore,
                availabilityScore,
                experienceScore,
                workloadBalance,
                profileEvidenceScore,
                competition.score(),
                currentWorkload,
                projectedWorkload,
                workloadThreshold,
                competition.activeApplicants(),
                competition.remainingSlots());
    }

    private double calculateSkillScore(
            List<String> matchedSkills,
            List<String> preferredMatches,
            int requiredTotal,
            int preferredTotal) {
        double requiredCoverage = requiredTotal == 0 ? 0.8 : matchedSkills.size() / (double) requiredTotal;
        double preferredCoverage = preferredTotal == 0 ? 0.55 : preferredMatches.size() / (double) preferredTotal;
        double breadthBonus = Math.min((matchedSkills.size() * 0.05) + (preferredMatches.size() * 0.04), 0.14);
        double fullCoverageBonus = requiredTotal > 0 && matchedSkills.size() == requiredTotal ? 0.08 : 0.0;
        return clamp((requiredCoverage * 0.72) + (preferredCoverage * 0.16) + breadthBonus + fullCoverageBonus);
    }

    private double calculateAvailabilityScore(String availability) {
        String normalizedAvailability = normalizeText(availability);
        if (normalizedAvailability.isBlank()) {
            return 0.35;
        }

        double score = 0.45;
        if (containsAny(normalizedAvailability, "weekday", "weekdays", "weekend", "weekends", "flexible")) {
            score += 0.18;
        }
        if (containsAny(
                normalizedAvailability,
                "monday",
                "tuesday",
                "wednesday",
                "thursday",
                "friday",
                "saturday",
                "sunday",
                "mon",
                "tue",
                "wed",
                "thu",
                "fri",
                "sat",
                "sun")) {
            score += 0.17;
        }
        if (containsAny(normalizedAvailability, "morning", "afternoon", "evening", "night")) {
            score += 0.12;
        }
        if (availability.contains("/") || availability.contains(",") || availability.length() >= 14) {
            score += 0.08;
        }
        return clamp(score);
    }

    private double calculateExperienceScore(UserProfile user, JobPosting job) {
        String experience = normalizeText(user.getExperience());
        String cvText = normalizeText(user.getCvText());
        String profileText = (experience + ' ' + cvText).trim();
        if (profileText.isBlank()) {
            return 0.3;
        }

        double score = 0.38;
        if (!experience.isBlank()) {
            score += 0.18;
        }
        if (!cvText.isBlank()) {
            score += 0.12;
        }

        double phraseCoverage = phraseCoverage(job, profileText);
        double tokenCoverage = tokenOverlap(jobKeywords(job), tokenize(profileText));
        score += phraseCoverage * 0.18;
        score += tokenCoverage * 0.14;

        if (containsAny(profileText, EVIDENCE_KEYWORDS.toArray(new String[0]))) {
            score += 0.1;
        }
        if (profileText.length() >= 40) {
            score += 0.08;
        }
        return clamp(score);
    }

    private double calculateProfileEvidenceScore(
            UserProfile user,
            JobPosting job,
            List<String> matchedSkills,
            List<String> preferredMatches) {
        double completeness = 0.0;
        if (!user.getSkills().isEmpty()) {
            completeness += 0.3;
        }
        if (!user.getAvailability().isBlank()) {
            completeness += 0.2;
        }
        if (!user.getExperience().isBlank()) {
            completeness += 0.25;
        }
        if (!user.getCvText().isBlank()) {
            completeness += 0.25;
        }

        String profileText = normalizeText(user.getExperience() + ' ' + user.getCvText());
        double phraseCoverage = phraseCoverage(job, profileText);
        double breadthSignal = Math.min((matchedSkills.size() * 0.08) + (preferredMatches.size() * 0.06), 0.3);
        return clamp((completeness * 0.55) + (phraseCoverage * 0.25) + breadthSignal);
    }

    private double calculateWorkloadBalance(int currentWorkload, int jobWorkloadHours, int workloadThreshold) {
        int projectedWorkload = currentWorkload + Math.max(jobWorkloadHours, 0);
        if (currentWorkload >= workloadThreshold) {
            return 0.1;
        }
        if (projectedWorkload <= workloadThreshold) {
            double remainingRatio = Math.max(workloadThreshold - projectedWorkload, 0) / (double) workloadThreshold;
            return clamp(0.55 + (remainingRatio * 0.45));
        }
        double overBy = projectedWorkload - workloadThreshold;
        double penaltyRatio = overBy / workloadThreshold;
        return clamp(0.38 - (penaltyRatio * 0.5));
    }

    private CompetitionSnapshot calculateCompetition(JobPosting job) {
        List<ApplicationRecord> activeApplications = applicationRepository.findByJobId(job.getJobId()).stream()
                .filter(application -> application.getStatus() != ApplicationStatus.WITHDRAWN)
                .filter(application -> application.getStatus() != ApplicationStatus.REJECTED)
                .toList();
        int acceptedCount = (int) activeApplications.stream()
                .filter(application -> application.getStatus() == ApplicationStatus.ACCEPTED)
                .count();
        int remainingSlots = Math.max(job.getQuota() - acceptedCount, 0);

        if (remainingSlots <= 0) {
            return new CompetitionSnapshot(activeApplications.size(), 0, 0.05);
        }

        double activePerSlot = activeApplications.size() / (double) remainingSlots;
        double score;
        if (activePerSlot <= 1.0) {
            score = 0.96;
        } else if (activePerSlot <= 2.0) {
            score = 0.82;
        } else if (activePerSlot <= 3.0) {
            score = 0.66;
        } else if (activePerSlot <= 4.0) {
            score = 0.52;
        } else {
            score = Math.max(0.18, 0.52 - ((activePerSlot - 4.0) * 0.07));
        }
        return new CompetitionSnapshot(activeApplications.size(), remainingSlots, clamp(score));
    }

    private List<String> collectEvidenceHits(
            UserProfile user,
            JobPosting job,
            List<String> matchedSkills,
            List<String> preferredMatches) {
        LinkedHashSet<String> hits = new LinkedHashSet<>();
        String profileText = normalizeText(user.getExperience() + ' ' + user.getCvText());

        for (String skill : matchedSkills) {
            if (profileText.contains(normalizePhrase(skill))) {
                hits.add(skill);
            }
        }
        for (String skill : preferredMatches) {
            if (profileText.contains(normalizePhrase(skill))) {
                hits.add(skill);
            }
        }
        for (String keyword : EVIDENCE_KEYWORDS) {
            if (hits.size() >= 4) {
                break;
            }
            if (profileText.contains(keyword) && normalizeText(job.getDescription()).contains(keyword)) {
                hits.add(keyword);
            }
        }
        return hits.stream().limit(4).toList();
    }

    private String buildSkillReason(
            List<String> matchedSkills,
            List<String> preferredMatches,
            List<String> missingSkills,
            int requiredTotal,
            int preferredTotal) {
        StringBuilder builder = new StringBuilder();
        builder.append("Required skill coverage: ")
                .append(matchedSkills.size())
                .append('/')
                .append(requiredTotal)
                .append('.');

        if (preferredTotal > 0) {
            builder.append(" Preferred alignment: ")
                    .append(preferredMatches.size())
                    .append('/')
                    .append(preferredTotal)
                    .append('.');
        } else {
            builder.append(" No preferred-skill constraints were configured.");
        }

        if (!missingSkills.isEmpty()) {
            builder.append(" Gaps still visible in ")
                    .append(String.join(", ", missingSkills))
                    .append('.');
        }
        return builder.toString();
    }

    private String buildAvailabilityReason(String availability, double availabilityScore) {
        if (availability == null || availability.isBlank()) {
            return "Availability details are missing, so scheduling confidence stays conservative.";
        }
        if (availabilityScore >= 0.75) {
            return "Availability looks well specified for planning: " + availability + '.';
        }
        return "Availability is present but could be more specific: " + availability + '.';
    }

    private String buildExperienceReason(UserProfile user, double experienceScore, List<String> evidenceHits) {
        if (user.getExperience().isBlank() && user.getCvText().isBlank()) {
            return "Experience and CV evidence are sparse, which limits confidence in role readiness.";
        }
        if (!evidenceHits.isEmpty()) {
            return "Profile evidence aligns with the role through " + String.join(", ", evidenceHits)
                    + ", lifting the experience score.";
        }
        if (experienceScore >= 0.7) {
            return "Experience wording is detailed enough to support this role, even without many exact phrase matches.";
        }
        return "Some experience evidence exists, but the profile could mention more role-specific examples.";
    }

    private String buildProfileEvidenceReason(double profileEvidenceScore, List<String> evidenceHits) {
        if (profileEvidenceScore >= 0.75) {
            return !evidenceHits.isEmpty()
                    ? "Overall profile evidence is strong and consistent with the job signals."
                    : "Overall profile evidence is strong, with good completeness across skills, availability and CV text.";
        }
        if (profileEvidenceScore >= 0.5) {
            return "Profile evidence is usable, but more concrete examples would improve explainability.";
        }
        return "Profile evidence is still thin, so the recommendation remains cautious.";
    }

    private String buildWorkloadReason(int currentWorkload, int projectedWorkload, int workloadThreshold) {
        if (projectedWorkload > workloadThreshold) {
            return "Accepting this role would move workload from " + currentWorkload + "h to " + projectedWorkload
                    + "h, above the " + workloadThreshold + "h threshold.";
        }
        return "Current accepted workload is " + currentWorkload + "h; this role would project to " + projectedWorkload
                + "h within the " + workloadThreshold + "h threshold.";
    }

    private String buildCompetitionReason(CompetitionSnapshot competition) {
        if (competition.remainingSlots() <= 0) {
            return "All quota is currently allocated, so competition pressure is extremely high.";
        }
        if (competition.score() >= 0.8) {
            return "Demand pressure is manageable: " + competition.activeApplicants() + " active applicants for "
                    + competition.remainingSlots() + " remaining slot" + (competition.remainingSlots() == 1 ? "" : "s") + '.';
        }
        return "Competition is heavier here: " + competition.activeApplicants() + " active applicants for "
                + competition.remainingSlots() + " remaining slot" + (competition.remainingSlots() == 1 ? "" : "s") + '.';
    }

    private double phraseCoverage(JobPosting job, String profileText) {
        if (profileText.isBlank()) {
            return 0.0;
        }
        LinkedHashSet<String> phrases = new LinkedHashSet<>();
        phrases.add(normalizePhrase(job.getTitle()));
        phrases.add(normalizePhrase(job.getModuleCode()));
        job.getRequiredSkills().stream().map(this::normalizePhrase).forEach(phrases::add);
        job.getPreferredSkills().stream().map(this::normalizePhrase).forEach(phrases::add);

        long relevantCount = phrases.stream().filter(phrase -> !phrase.isBlank()).count();
        if (relevantCount == 0) {
            return 0.0;
        }

        long matches = phrases.stream()
                .filter(phrase -> !phrase.isBlank())
                .filter(profileText::contains)
                .count();
        return clamp(matches / (double) relevantCount);
    }

    private Set<String> jobKeywords(JobPosting job) {
        LinkedHashSet<String> tokens = new LinkedHashSet<>();
        tokens.addAll(tokenize(job.getTitle()));
        tokens.addAll(tokenize(job.getModuleCode()));
        tokens.addAll(tokenize(job.getDescription()));
        job.getRequiredSkills().forEach(skill -> tokens.addAll(tokenize(skill)));
        job.getPreferredSkills().forEach(skill -> tokens.addAll(tokenize(skill)));
        tokens.removeIf(token -> STOP_WORDS.contains(token));
        return tokens;
    }

    private double tokenOverlap(Set<String> referenceTokens, Set<String> profileTokens) {
        if (referenceTokens.isEmpty() || profileTokens.isEmpty()) {
            return 0.0;
        }
        long matched = referenceTokens.stream().filter(profileTokens::contains).count();
        double denominator = Math.max(4, Math.min(referenceTokens.size(), 10));
        return clamp(matched / denominator);
    }

    private Set<String> tokenize(String rawText) {
        LinkedHashSet<String> tokens = new LinkedHashSet<>();
        Matcher matcher = TOKEN_PATTERN.matcher(normalizeText(rawText));
        while (matcher.find()) {
            String token = matcher.group();
            if (!STOP_WORDS.contains(token)) {
                tokens.add(token);
            }
        }
        return tokens;
    }

    private boolean containsAny(String text, String... candidates) {
        for (String candidate : candidates) {
            if (text.contains(candidate)) {
                return true;
            }
        }
        return false;
    }

    private String normalizePhrase(String rawValue) {
        return normalizeText(rawValue).replace('-', ' ').trim();
    }

    private String normalizeText(String rawValue) {
        return rawValue == null ? "" : rawValue.toLowerCase(Locale.ENGLISH).trim();
    }

    private double clamp(double value) {
        return Math.max(0.0, Math.min(value, 1.0));
    }

    private record CompetitionSnapshot(int activeApplicants, int remainingSlots, double score) {
    }
}
