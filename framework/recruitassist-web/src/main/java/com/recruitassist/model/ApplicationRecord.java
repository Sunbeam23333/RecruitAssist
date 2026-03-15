package com.recruitassist.model;

import java.util.ArrayList;
import java.util.List;

public class ApplicationRecord {
    private String applicationId;
    private String jobId;
    private String applicantId;
    private String applyTime;
    private ApplicationStatus status;
    private double recommendationScore;
    private List<String> explanation = new ArrayList<>();

    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public String getApplicantId() {
        return applicantId;
    }

    public void setApplicantId(String applicantId) {
        this.applicantId = applicantId;
    }

    public String getApplyTime() {
        return applyTime == null ? "" : applyTime;
    }

    public void setApplyTime(String applyTime) {
        this.applyTime = applyTime;
    }

    public ApplicationStatus getStatus() {
        return status == null ? ApplicationStatus.SUBMITTED : status;
    }

    public void setStatus(ApplicationStatus status) {
        this.status = status;
    }

    public double getRecommendationScore() {
        return recommendationScore;
    }

    public void setRecommendationScore(double recommendationScore) {
        this.recommendationScore = recommendationScore;
    }

    public List<String> getExplanation() {
        return explanation == null ? List.of() : explanation;
    }

    public void setExplanation(List<String> explanation) {
        this.explanation = explanation;
    }

    public int getRecommendationPercent() {
        return (int) Math.round(recommendationScore * 100);
    }

    public String getExplanationSummary() {
        return getExplanation().isEmpty() ? "No explanation available" : String.join(" • ", getExplanation());
    }
}
