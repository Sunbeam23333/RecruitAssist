package com.recruitassist.web;

import com.recruitassist.model.ApplicationRecord;
import com.recruitassist.model.ApplicationStatus;
import com.recruitassist.model.JobPosting;
import com.recruitassist.model.UserProfile;
import com.recruitassist.model.UserRole;
import com.recruitassist.model.view.JobRecommendation;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet("/dashboard")
public class DashboardServlet extends AppServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserProfile user = requireAuthenticatedUser(req, resp);
        if (user == null) {
            return;
        }

        moveFlashToRequest(req);
        req.setAttribute("user", user);
        req.setAttribute("appName", services(req).systemConfig().getAppName());

        if (user.getRole() == UserRole.TA) {
            renderTaDashboard(req, resp, user);
            return;
        }
        if (user.getRole() == UserRole.MO) {
            renderMoDashboard(req, resp, user);
            return;
        }
        renderAdminDashboard(req, resp);
    }

    private void renderTaDashboard(HttpServletRequest req, HttpServletResponse resp, UserProfile user)
            throws ServletException, IOException {
        List<JobRecommendation> recommendedJobs = services(req).recommendationService().recommendJobsFor(user);
        List<ApplicationRecord> applications = services(req).applicationService().findByApplicantId(user.getUserId());
        long acceptedApplications = applications.stream()
                .filter(application -> application.getStatus() == ApplicationStatus.ACCEPTED)
                .count();

        req.setAttribute("recommendedJobs", recommendedJobs);
        req.setAttribute("topRecommendation", recommendedJobs.isEmpty() ? null : recommendedJobs.get(0));
        req.setAttribute("applications", applications);
        req.setAttribute("applicationsByJobId", services(req).applicationService().mapByJobIdForApplicant(user.getUserId()));
        req.setAttribute("jobsById", services(req).jobService().indexById());
        req.setAttribute("currentWorkload", services(req).workloadService().workloadForUser(user.getUserId()));
        req.setAttribute("activeApplicationCount", services(req).workloadService().activeApplicationsForUser(user.getUserId()));
        req.setAttribute("acceptedApplicationCount", acceptedApplications);
        req.setAttribute("profileSignalCount", countProfileSignals(user));
        req.setAttribute("profileSignalPercent", countProfileSignals(user) * 25);
        req.setAttribute("workloadThreshold", services(req).workloadService().getThreshold());
        req.getRequestDispatcher("/WEB-INF/jsp/dashboard-ta.jsp").forward(req, resp);
    }

    private void renderMoDashboard(HttpServletRequest req, HttpServletResponse resp, UserProfile user)
            throws ServletException, IOException {
        List<JobPosting> jobs = services(req).jobService().listJobsForOwner(user.getUserId());
        Map<String, Integer> workloadByUserId = services(req).workloadService().workloadByUserId();
        Map<String, List<ApplicationRecord>> applicationsByJobId = services(req).applicationService().groupByJobIdsForReview(
                jobs.stream().map(JobPosting::getJobId).toList(),
                workloadByUserId);
        long openJobCount = jobs.stream().filter(JobPosting::isOpen).count();
        int totalApplicationCount = applicationsByJobId.values().stream().mapToInt(List::size).sum();
        long acceptedCandidateCount = applicationsByJobId.values().stream()
                .flatMap(List::stream)
                .filter(application -> application.getStatus() == ApplicationStatus.ACCEPTED)
                .count();
        long shortlistedCount = applicationsByJobId.values().stream()
                .flatMap(List::stream)
                .filter(application -> application.getStatus() == ApplicationStatus.SHORTLISTED)
                .count();

        req.setAttribute("jobs", jobs);
        req.setAttribute("applicationsByJobId", applicationsByJobId);
        req.setAttribute("usersById", services(req).userService().indexById());
        req.setAttribute("workloadByUserId", workloadByUserId);
        req.setAttribute("workloadThreshold", services(req).workloadService().getThreshold());
        req.setAttribute("openJobCount", openJobCount);
        req.setAttribute("totalApplicationCount", totalApplicationCount);
        req.setAttribute("acceptedCandidateCount", acceptedCandidateCount);
        req.setAttribute("shortlistedCandidateCount", shortlistedCount);
        req.getRequestDispatcher("/WEB-INF/jsp/dashboard-mo.jsp").forward(req, resp);
    }

    private void renderAdminDashboard(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setAttribute("workloadEntries", services(req).workloadService().buildEntries());
        req.setAttribute("latestApplications", services(req).applicationService().findRecentApplications(10));
        req.setAttribute("usersById", services(req).userService().indexById());
        req.setAttribute("jobsById", services(req).jobService().indexById());
        req.setAttribute("workloadThreshold", services(req).workloadService().getThreshold());
        req.getRequestDispatcher("/WEB-INF/jsp/dashboard-admin.jsp").forward(req, resp);
    }

    private int countProfileSignals(UserProfile user) {
        int signals = 0;
        if (!user.getSkills().isEmpty()) {
            signals++;
        }
        if (!user.getAvailability().isBlank()) {
            signals++;
        }
        if (!user.getExperience().isBlank()) {
            signals++;
        }
        if (!user.getCvText().isBlank()) {
            signals++;
        }
        return signals;
    }
}
