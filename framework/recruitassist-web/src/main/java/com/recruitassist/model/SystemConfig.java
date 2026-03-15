package com.recruitassist.model;

public class SystemConfig {
    private String appName = "RecruitAssist";
    private StorageConfig storage = new StorageConfig();
    private WorkloadConfig workload = new WorkloadConfig();
    private RecommendationConfig recommendation = new RecommendationConfig();

    public String getAppName() {
        return appName;
    }

    public StorageConfig getStorage() {
        return storage == null ? new StorageConfig() : storage;
    }

    public WorkloadConfig getWorkload() {
        return workload == null ? new WorkloadConfig() : workload;
    }

    public RecommendationConfig getRecommendation() {
        return recommendation == null ? new RecommendationConfig() : recommendation;
    }

    public static class StorageConfig {
        private String mode = "text-files-only";

        public String getMode() {
            return mode;
        }
    }

    public static class WorkloadConfig {
        private int defaultMaxHours = 12;

        public int getDefaultMaxHours() {
            return defaultMaxHours <= 0 ? 12 : defaultMaxHours;
        }
    }

    public static class RecommendationConfig {
        private double skillMatchWeight = 0.5;
        private double availabilityWeight = 0.2;
        private double experienceWeight = 0.15;
        private double workloadBalanceWeight = 0.15;

        public double getSkillMatchWeight() {
            return skillMatchWeight;
        }

        public double getAvailabilityWeight() {
            return availabilityWeight;
        }

        public double getExperienceWeight() {
            return experienceWeight;
        }

        public double getWorkloadBalanceWeight() {
            return workloadBalanceWeight;
        }
    }
}
