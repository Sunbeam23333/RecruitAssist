<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>MO Dashboard · RecruitAssist Sprint 1</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/app.css" />
    <script defer src="${pageContext.request.contextPath}/assets/js/app.js"></script>
</head>
<body class="page-dashboard role-mo">
<div class="page-shell">
    <header class="hero-card home-hero dashboard-hero">
        <div>
            <div class="badge">MO Dashboard</div>
            <h1>${user.name}</h1>
            <div class="hero-metrics">
                <div class="hero-metric"><strong>${jobs.size()}</strong><span>Your jobs</span></div>
                <div class="hero-metric"><strong>${openJobCount}</strong><span>Open jobs</span></div>
                <div class="hero-metric"><strong>${totalApplicationCount}</strong><span>Total applications</span></div>
            </div>
        </div>
        <aside class="spotlight-card">
            <div class="spotlight-kicker">Sprint 1 focus</div>
            <div class="spotlight-score">MO<span> flow</span></div>
            <h3>Post roles clearly</h3>
            <div class="action-row">
                <a class="secondary-button small-button" href="${pageContext.request.contextPath}/logout">Log out</a>
            </div>
        </aside>
    </header>

    <c:if test="${not empty flashMessage}">
        <div class="alert ${flashTone}">${flashMessage}</div>
    </c:if>

    <main class="dashboard-grid">
        <section class="panel">
            <div class="section-head">
                <h2>Create a new TA position</h2>
                <span class="metric-pill">Post immediately</span>
            </div>
            <form class="form-grid two-column-form" method="post" action="${pageContext.request.contextPath}/jobs/create">
                <label class="field-group">
                    <span>Job title</span>
                    <input class="input" type="text" name="title" required />
                </label>
                <label class="field-group">
                    <span>Module code</span>
                    <input class="input" type="text" name="moduleCode" required />
                </label>
                <label class="field-group">
                    <span>Deadline</span>
                    <input class="input" type="date" name="deadline" required />
                </label>
                <label class="field-group">
                    <span>TA count</span>
                    <input class="input" type="number" name="quota" min="1" step="1" required />
                </label>
                <label class="field-group">
                    <span>Hours per week</span>
                    <input class="input" type="number" name="workloadHours" min="1" step="1" required />
                </label>
                <div class="field-group full-width">
                    <span>Required skills</span>
                    <textarea class="textarea" name="requiredSkills" rows="3" required></textarea>
                </div>
                <div class="field-group full-width">
                    <span>Description</span>
                    <textarea class="textarea" name="description" rows="4" required></textarea>
                </div>
                <div class="form-actions full-width">
                    <button class="primary-button" type="submit" data-loading-text="Publishing...">Publish job</button>
                </div>
            </form>
        </section>

        <section class="panel">
            <div class="section-head">
                <h2>Your posted jobs</h2>
                <span class="metric-pill">${jobs.size()} jobs</span>
            </div>
            <div class="table-wrapper">
                <table class="data-table">
                    <thead>
                    <tr>
                        <th>Job</th>
                        <th>Deadline</th>
                        <th>Quota</th>
                        <th>Applicants</th>
                        <th>Status</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="job" items="${jobs}">
                        <tr>
                            <td>
                                <strong>
                                    <a class="inline-link" href="${pageContext.request.contextPath}/jobs/detail?jobId=${job.jobId}">
                                        ${job.title}
                                    </a>
                                </strong>
                                <div class="muted-copy">${job.moduleCode}</div>
                            </td>
                            <td>
                                <strong>${job.deadlineLabel}</strong>
                                <div class="muted-copy">${job.deadlineStatusLabel}</div>
                            </td>
                            <td>${job.quota}</td>
                            <td>${applicationCounts[job.jobId] == null ? 0 : applicationCounts[job.jobId]}</td>
                            <td><span class="status-pill status-${job.status.cssClass}">${job.status.label}</span></td>
                        </tr>
                    </c:forEach>
                    <c:if test="${empty jobs}">
                        <tr><td colspan="5" class="empty-state">You have not posted any jobs yet.</td></tr>
                    </c:if>
                    </tbody>
                </table>
            </div>
        </section>
    </main>
</div>
</body>
</html>
