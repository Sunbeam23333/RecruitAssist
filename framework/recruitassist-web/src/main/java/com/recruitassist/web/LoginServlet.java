package com.recruitassist.web;

import com.recruitassist.model.UserProfile;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
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
        req.setAttribute("demoUsers", services(req).userService().listAllUsers());
        req.setAttribute("demoPassword", "demo123");
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
            req.setAttribute("demoUsers", services(req).userService().listAllUsers());
            req.setAttribute("demoPassword", "demo123");
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
}
