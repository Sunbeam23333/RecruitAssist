package com.recruitassist.web;

import com.recruitassist.model.ApplicationRecord;
import com.recruitassist.model.JobPosting;
import com.recruitassist.model.UserProfile;
import com.recruitassist.model.UserRole;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/jobs/detail")
public class JobDetailServlet extends AppServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserProfile user = requireAuthenticatedUser(req, resp);
        if (user == null) {
            return;
        }

        moveFlashToRequest(req);
        String jobId = req.getParameter("jobId");
        if (jobId == null || jobId.isBlank()) {
            setFlash(req, "error", "Please choose a valid job.");
            redirect(req, resp, "/dashboard");
            return;
        }

        JobPosting job = services(req).jobService().findById(jobId).orElse(null);
        if (job == null) {
            setFlash(req, "error", "The requested job could not be found.");
            redirect(req, resp, "/dashboard");
            return;
        }

        List<ApplicationRecord> allApplications = services(req).applicationService().findByJobId(job.getJobId());
        UserProfile owner = services(req).userService().findById(job.getOwnerId()).orElse(null);
        boolean taView = user.getRole() == UserRole.TA;
        boolean ownerView = user.getRole() == UserRole.MO && job.getOwnerId().equalsIgnoreCase(user.getUserId());

        req.setAttribute("user", user);
        req.setAttribute("job", job);
        req.setAttribute("ownerName", owner == null ? job.getOwnerId() : owner.getName());
        req.setAttribute("applicationCount", allApplications.size());
        req.setAttribute("taView", taView);
        req.setAttribute("ownerView", ownerView);

        if (taView) {
            ApplicationRecord existingApplication = services(req).applicationService()
                    .findExistingApplication(user.getUserId(), job.getJobId())
                    .orElse(null);
            req.setAttribute("existingApplication", existingApplication);
            req.setAttribute("canApplyToJob", job.isOpen() && !job.isExpired() && existingApplication == null);
        }

        req.getRequestDispatcher("/WEB-INF/jsp/job-detail.jsp").forward(req, resp);
    }
}
