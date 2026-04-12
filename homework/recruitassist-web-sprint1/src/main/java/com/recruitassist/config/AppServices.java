package com.recruitassist.config;

import com.recruitassist.model.SystemConfig;
import com.recruitassist.repository.ApplicationRepository;
import com.recruitassist.repository.IdCounterRepository;
import com.recruitassist.repository.JobRepository;
import com.recruitassist.repository.SystemConfigRepository;
import com.recruitassist.repository.UserRepository;
import com.recruitassist.service.ApplicationService;
import com.recruitassist.service.AuthService;
import com.recruitassist.service.JobService;
import com.recruitassist.service.UserService;
import com.recruitassist.util.JsonFileStore;

public class AppServices {
    private final SystemConfig systemConfig;
    private final UserService userService;
    private final AuthService authService;
    private final JobService jobService;
    private final ApplicationService applicationService;

    public AppServices() {
        JsonFileStore jsonFileStore = new JsonFileStore();
        SystemConfigRepository systemConfigRepository = new SystemConfigRepository(jsonFileStore);
        UserRepository userRepository = new UserRepository(jsonFileStore);
        JobRepository jobRepository = new JobRepository(jsonFileStore);
        ApplicationRepository applicationRepository = new ApplicationRepository(jsonFileStore);
        IdCounterRepository idCounterRepository = new IdCounterRepository(jsonFileStore);

        this.systemConfig = systemConfigRepository.load();
        this.userService = new UserService(userRepository);
        this.authService = new AuthService(userService);
        this.jobService = new JobService(jobRepository, idCounterRepository);
        this.applicationService = new ApplicationService(applicationRepository, jobRepository, idCounterRepository);
    }

    public SystemConfig systemConfig() {
        return systemConfig;
    }

    public UserService userService() {
        return userService;
    }

    public AuthService authService() {
        return authService;
    }

    public JobService jobService() {
        return jobService;
    }

    public ApplicationService applicationService() {
        return applicationService;
    }
}
