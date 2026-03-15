package com.recruitassist.web;

import com.recruitassist.config.AppPaths;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/home")
public class HomeServlet extends AppServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (currentUser(req) != null) {
            redirect(req, resp, "/dashboard");
            return;
        }

        req.setAttribute("appName", services(req).systemConfig().getAppName());
        req.setAttribute("stack", "Java 17 + Maven + Servlet/JSP + JSON/CSV/TXT");
        req.setAttribute("frameworkDir", AppPaths.frameworkDir().toString());
        req.setAttribute("dataDir", AppPaths.dataDir().toString());
        req.setAttribute("logsDir", AppPaths.logsDir().toString());
        req.setAttribute("demoPassword", "demo123");
        req.getRequestDispatcher("/WEB-INF/jsp/home.jsp").forward(req, resp);
    }
}
