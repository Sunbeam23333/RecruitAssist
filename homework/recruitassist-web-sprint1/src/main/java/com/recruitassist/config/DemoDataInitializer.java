package com.recruitassist.config;

import com.google.gson.reflect.TypeToken;
import com.recruitassist.model.JobPosting;
import com.recruitassist.model.JobStatus;
import com.recruitassist.model.SystemConfig;
import com.recruitassist.model.UserProfile;
import com.recruitassist.model.UserRole;
import com.recruitassist.util.JsonFileStore;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public final class DemoDataInitializer {
    private static final Type COUNTER_TYPE = new TypeToken<Map<String, Long>>() { }.getType();

    private DemoDataInitializer() {
    }

    public static void ensureSeedData() throws IOException {
        JsonFileStore store = new JsonFileStore();
        seedConfig(store);
        seedUsers(store);
        seedJobs(store);
        seedCounters(store);
    }

    private static void seedConfig(JsonFileStore store) {
        if (store.read(AppPaths.configFile(), SystemConfig.class) == null) {
            store.write(AppPaths.configFile(), new SystemConfig());
        }
    }

    private static void seedUsers(JsonFileStore store) throws IOException {
        if (hasJsonFiles(AppPaths.usersDir())) {
            return;
        }

        store.write(AppPaths.usersDir().resolve("U1001.json"), taUser(
                "U1001", "ta_alice", "Alice Chen", "2401001", "alice.chen@example.com",
                "MSc Computer Science", List.of("Java", "Communication", "Lab Support"), "Weekdays afternoon"));
        store.write(AppPaths.usersDir().resolve("U1002.json"), taUser(
                "U1002", "ta_ben", "Ben Wong", "2401002", "",
                "BSc Software Engineering", List.of(), ""));
        store.write(AppPaths.usersDir().resolve("U1101.json"), staffUser(
                "U1101", "mo_smith", "Dr Smith", UserRole.MO));
        store.write(AppPaths.usersDir().resolve("U1201.json"), staffUser(
                "U1201", "admin_demo", "System Admin", UserRole.ADMIN));
    }

    private static void seedJobs(JsonFileStore store) throws IOException {
        if (hasJsonFiles(AppPaths.jobsDir())) {
            return;
        }

        store.write(AppPaths.jobsDir().resolve("J2001.json"), job(
                "J2001",
                "U1101",
                "Programming Fundamentals TA",
                "CSC101",
                "Support weekly labs and answer student questions.",
                List.of("Java", "Communication"),
                LocalDate.now().plusDays(10),
                2,
                6,
                JobStatus.OPEN));
        store.write(AppPaths.jobsDir().resolve("J2002.json"), job(
                "J2002",
                "U1101",
                "Database Systems TA",
                "CSC202",
                "Help with practical sessions and simple marking preparation.",
                List.of("SQL", "Database Design"),
                LocalDate.now().plusDays(14),
                1,
                4,
                JobStatus.OPEN));
        store.write(AppPaths.jobsDir().resolve("J2003.json"), job(
                "J2003",
                "U1101",
                "Archived Web Development TA",
                "CSC099",
                "Past posting kept to demonstrate closed jobs in the listing.",
                List.of("HTML", "CSS"),
                LocalDate.now().minusDays(2),
                1,
                4,
                JobStatus.CLOSED));
    }

    private static void seedCounters(JsonFileStore store) {
        if (store.read(AppPaths.idCountersFile(), COUNTER_TYPE) != null) {
            return;
        }

        Map<String, Long> counters = new LinkedHashMap<>();
        counters.put("job", 2003L);
        counters.put("application", 3000L);
        store.write(AppPaths.idCountersFile(), counters);
    }

    private static boolean hasJsonFiles(Path directory) throws IOException {
        try (Stream<Path> paths = Files.list(directory)) {
            return paths.anyMatch(path -> Files.isRegularFile(path) && path.getFileName().toString().endsWith(".json"));
        }
    }

    private static UserProfile taUser(
            String userId,
            String username,
            String name,
            String studentId,
            String email,
            String programme,
            List<String> skills,
            String availability) {
        UserProfile user = new UserProfile();
        user.setUserId(userId);
        user.setUsername(username);
        user.setPassword("demo123");
        user.setRole(UserRole.TA);
        user.setName(name);
        user.setStudentId(studentId);
        user.setEmail(email);
        user.setProgramme(programme);
        user.setSkills(skills);
        user.setAvailability(availability);
        return user;
    }

    private static UserProfile staffUser(String userId, String username, String name, UserRole role) {
        UserProfile user = new UserProfile();
        user.setUserId(userId);
        user.setUsername(username);
        user.setPassword("demo123");
        user.setRole(role);
        user.setName(name);
        user.setProgramme(role == UserRole.ADMIN ? "Administration" : "School of Electronic Engineering and Computer Science");
        return user;
    }

    private static JobPosting job(
            String jobId,
            String ownerId,
            String title,
            String moduleCode,
            String description,
            List<String> requiredSkills,
            LocalDate deadline,
            int quota,
            int workloadHours,
            JobStatus status) {
        JobPosting job = new JobPosting();
        job.setJobId(jobId);
        job.setOwnerId(ownerId);
        job.setTitle(title);
        job.setModuleCode(moduleCode);
        job.setDescription(description);
        job.setRequiredSkills(requiredSkills);
        job.setPreferredSkills(List.of());
        job.setDeadline(deadline.toString());
        job.setQuota(quota);
        job.setWorkloadHours(workloadHours);
        job.setStatus(status);
        return job;
    }
}
