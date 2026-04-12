<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Admin Dashboard · RecruitAssist Sprint 1</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/app.css" />
    <script defer src="${pageContext.request.contextPath}/assets/js/app.js"></script>
</head>
<body class="page-dashboard role-admin">
<div class="page-shell">
    <header class="hero-card home-hero dashboard-hero">
        <div>
            <div class="badge">Admin Dashboard</div>
            <h1>${user.name}</h1>
            <div class="hero-metrics">
                <div class="hero-metric"><strong>${jobCount}</strong><span>Total jobs</span></div>
                <div class="hero-metric"><strong>${openJobTotal}</strong><span>Open jobs</span></div>
                <div class="hero-metric"><strong>${applicationCount}</strong><span>Total applications</span></div>
            </div>
        </div>
        <aside class="spotlight-card">
            <div class="spotlight-kicker">Sprint 1 boundary</div>
            <div class="spotlight-score">Admin<span> landing</span></div>
            <h3>No operational review yet</h3>
            <div class="action-row">
                <a class="secondary-button small-button" href="${pageContext.request.contextPath}/logout">Log out</a>
            </div>
        </aside>
    </header>

    <c:if test="${not empty flashMessage}">
        <div class="alert ${flashTone}">${flashMessage}</div>
    </c:if>

    <section class="kpi-grid">
        <article class="kpi-card">
            <span class="kpi-label">TAs</span>
            <strong>${taCount}</strong>
        </article>
        <article class="kpi-card">
            <span class="kpi-label">MOs</span>
            <strong>${moCount}</strong>
        </article>
        <article class="kpi-card">
            <span class="kpi-label">Jobs</span>
            <strong>${jobCount}</strong>
        </article>
        <article class="kpi-card">
            <span class="kpi-label">Applications</span>
            <strong>${applicationCount}</strong>
        </article>
    </section>
</div>
</body>
</html>
