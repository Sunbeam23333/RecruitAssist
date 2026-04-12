<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>${appName} Sprint 1</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/app.css" />
    <script defer src="${pageContext.request.contextPath}/assets/js/app.js"></script>
</head>
<body class="page-home">
<div class="page-shell">
    <header class="hero-card home-hero dashboard-hero">
        <div>
            <div class="badge">Admin Portal</div>
            <h1>${user.name}</h1>
            <div class="hero-metrics">
                <div class="hero-metric"><strong>${taCount}</strong><span>TA accounts</span></div>
                <div class="hero-metric"><strong>${recruiterCount}</strong><span>MO accounts</span></div>
                <div class="hero-metric"><strong>${adminCount}</strong><span>Admin accounts</span></div>
                <div class="hero-metric"><strong>${jobCount}</strong><span>Total jobs</span></div>
            </div>
            <div class="hero-actions section-gap">
                <a class="primary-button" href="${pageContext.request.contextPath}/dashboard">Open dashboard</a>
                <a class="secondary-button" href="${pageContext.request.contextPath}/logout">Log out</a>
            </div>
        </div>
        <aside class="spotlight-card">
            <div class="spotlight-kicker">Recruitment summary</div>
            <div class="spotlight-score">${applicationCount}<span> applications</span></div>
            <h3>${openJobTotal} open jobs</h3>
            <div class="spotlight-meta">
                <span class="metric-pill">Admin only</span>
                <span class="metric-pill">${jobCount} jobs</span>
                <span class="metric-pill">${recruiterCount} MOs</span>
            </div>
        </aside>
    </header>

    <main class="content-grid dashboard-grid single-column-grid">
        <section class="panel">
            <div class="section-head">
                <h2>Admin Access</h2>
                <span class="status-pill status-open">Ready</span>
            </div>
            <div class="journey-grid">
                <article class="journey-step">
                    <div class="spotlight-kicker">Users</div>
                    <h3>Track account coverage</h3>
                </article>
                <article class="journey-step">
                    <div class="spotlight-kicker">Jobs</div>
                    <h3>Monitor job activity</h3>
                </article>
                <article class="journey-step">
                    <div class="spotlight-kicker">Flow</div>
                    <h3>Open the admin dashboard</h3>
                </article>
            </div>
        </section>

        <section class="panel">
            <div class="section-head">
                <h2>Available Overview</h2>
                <span class="metric-pill">Admin</span>
            </div>
            <div class="chip-row">
                <span class="chip">${taCount} TA accounts</span>
                <span class="chip">${recruiterCount} MO accounts</span>
                <span class="chip">${adminCount} admin accounts</span>
                <span class="chip">${jobCount} total jobs</span>
                <span class="chip">${openJobTotal} open jobs</span>
                <span class="chip">${applicationCount} applications</span>
            </div>
        </section>
    </main>
</div>
</body>
</html>
