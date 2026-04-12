package com.recruitassist.web;

import com.recruitassist.model.UserProfile;
import com.recruitassist.model.UserRole;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@WebServlet("/login")
public class LoginServlet extends AppServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (currentUser(req) != null) {
            redirect(req, resp, "/dashboard");
            return;
        }

        moveFlashToRequest(req);
        populateLoginView(req);
        req.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        Optional<UserProfile> authenticatedUser = services(req).authService().authenticate(username, password);
        if (authenticatedUser.isEmpty()) {
            req.setAttribute("error", "Invalid username or password.");
            req.setAttribute("username", username);
            populateLoginView(req);
            req.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(req, resp);
            return;
        }

        HttpSession existingSession = req.getSession(false);
        if (existingSession != null) {
            existingSession.invalidate();
        }

        HttpSession session = req.getSession(true);
        session.setAttribute(SESSION_USER_ID, authenticatedUser.get().getUserId());
        setFlash(req, "success", "Signed in as " + authenticatedUser.get().getName() + ".");
        redirect(req, resp, "/dashboard");
    }

    private void populateLoginView(HttpServletRequest req) {
        List<UserProfile> featuredUsers = new ArrayList<>();
        // Sprint 1 keeps the login page simple, even though it means repeated role scans and file reads.
        featuredUsers.addAll(services(req).userService().listUsersByRole(UserRole.TA).stream().limit(3).toList());
        featuredUsers.addAll(services(req).userService().listUsersByRole(UserRole.MO).stream().limit(2).toList());
        featuredUsers.addAll(services(req).userService().listUsersByRole(UserRole.ADMIN).stream().limit(1).toList());

        req.setAttribute("featuredDemoUsers", featuredUsers);
        req.setAttribute("demoPassword", "demo123");
        req.setAttribute("totalUserCount", services(req).userService().listAllUsers().size());
        req.setAttribute("taCount", services(req).userService().listUsersByRole(UserRole.TA).size());
        req.setAttribute("moCount", services(req).userService().listUsersByRole(UserRole.MO).size());
        req.setAttribute("adminCount", services(req).userService().listUsersByRole(UserRole.ADMIN).size());
    }
}
