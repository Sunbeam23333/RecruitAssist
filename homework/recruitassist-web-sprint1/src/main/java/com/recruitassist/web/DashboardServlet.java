package com.recruitassist.web;

import com.recruitassist.model.JobPosting;
import com.recruitassist.model.UserProfile;
import com.recruitassist.model.UserRole;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Comparator;
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
        renderAdminDashboard(req, resp, user);
    }

    private void renderTaDashboard(HttpServletRequest req, HttpServletResponse resp, UserProfile user)
            throws ServletException, IOException {
        String query = normalizeText(req.getParameter("q"));
        String sort = normalizeSort(req.getParameter("sort"));
        List<JobPosting> jobs = services(req).jobService().listAllJobs().stream()
                .filter(job -> matchesJobQuery(job, query))
                .sorted(jobComparator(sort))
                .toList();

        req.setAttribute("jobs", jobs);
        req.setAttribute("applicationsByJobId", services(req).applicationService().mapByJobIdForApplicant(user.getUserId()));
        req.setAttribute("applicationCounts", services(req).applicationService().countByJobId());
        req.setAttribute("openJobCount", services(req).jobService().listOpenJobs().size());
        req.setAttribute("profileReady", user.isProfileReady());
        req.setAttribute("jobSearchQuery", req.getParameter("q") == null ? "" : req.getParameter("q").trim());
        req.setAttribute("jobSort", sort);
        req.setAttribute("jobSortLabel", describeSort(sort));
        req.getRequestDispatcher("/WEB-INF/jsp/dashboard-ta.jsp").forward(req, resp);
    }

    private void renderMoDashboard(HttpServletRequest req, HttpServletResponse resp, UserProfile user)
            throws ServletException, IOException {
        List<JobPosting> jobs = services(req).jobService().listJobsForOwner(user.getUserId());
        Map<String, Long> applicationCounts = services(req).applicationService().countByJobId();
        long totalApplicationCount = jobs.stream()
                .mapToLong(job -> applicationCounts.getOrDefault(job.getJobId(), 0L))
                .sum();

        req.setAttribute("jobs", jobs);
        req.setAttribute("applicationCounts", applicationCounts);
        req.setAttribute("openJobCount", jobs.stream().filter(JobPosting::isOpen).count());
        req.setAttribute("totalApplicationCount", totalApplicationCount);
        req.getRequestDispatcher("/WEB-INF/jsp/dashboard-mo.jsp").forward(req, resp);
    }

    private void renderAdminDashboard(HttpServletRequest req, HttpServletResponse resp, UserProfile user)
            throws ServletException, IOException {
        List<JobPosting> allJobs = services(req).jobService().listAllJobs();
        req.setAttribute("user", user);
        req.setAttribute("jobCount", allJobs.size());
        req.setAttribute("openJobTotal", allJobs.stream().filter(JobPosting::isOpen).count());
        req.setAttribute("applicationCount", services(req).applicationService().findAll().size());
        req.setAttribute("taCount", services(req).userService().listUsersByRole(UserRole.TA).size());
        req.setAttribute("moCount", services(req).userService().listUsersByRole(UserRole.MO).size());
        req.getRequestDispatcher("/WEB-INF/jsp/dashboard-admin.jsp").forward(req, resp);
    }

    private Comparator<JobPosting> jobComparator(String sort) {
        return switch (sort) {
            case "module" -> Comparator.comparing(JobPosting::getModuleCode, String.CASE_INSENSITIVE_ORDER)
                    .thenComparing(JobPosting::getTitle, String.CASE_INSENSITIVE_ORDER);
            default -> Comparator.comparing(JobPosting::getDeadline, String.CASE_INSENSITIVE_ORDER)
                    .thenComparing(JobPosting::getModuleCode, String.CASE_INSENSITIVE_ORDER);
        };
    }

    private boolean matchesJobQuery(JobPosting job, String query) {
        if (query.isBlank()) {
            return true;
        }
        String searchable = String.join(" ",
                job.getTitle(),
                job.getModuleCode(),
                job.getDescription(),
                job.getRequiredSkillsSummary()).toLowerCase();
        return searchable.contains(query);
    }

    private String normalizeSort(String rawSort) {
        if (rawSort == null || rawSort.isBlank()) {
            return "deadline";
        }
        return switch (rawSort.trim().toLowerCase()) {
            case "deadline", "module" -> rawSort.trim().toLowerCase();
            default -> "deadline";
        };
    }

    private String describeSort(String sort) {
        return switch (sort) {
            case "module" -> "Module name";
            default -> "Closest deadline first";
        };
    }

    private String normalizeText(String rawValue) {
        return rawValue == null ? "" : rawValue.trim().toLowerCase();
    }
}
