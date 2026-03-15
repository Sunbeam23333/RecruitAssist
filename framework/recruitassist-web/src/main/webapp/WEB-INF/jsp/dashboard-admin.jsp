<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Admin Dashboard · RecruitAssist</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/app.css" />
</head>
<body>
<div class="page-shell">
    <header class="app-bar">
        <div>
            <div class="badge">Admin Dashboard</div>
            <h1>${user.name}</h1>
            <p class="subtitle">Monitor workload balance and review recent system activity.</p>
        </div>
        <div class="top-actions">
            <span class="metric-pill">Threshold ${workloadThreshold} h</span>
            <a class="secondary-button" href="${pageContext.request.contextPath}/logout">Log out</a>
        </div>
    </header>

    <c:if test="${not empty flashMessage}">
        <div class="alert ${flashTone}">${flashMessage}</div>
    </c:if>

    <section class="kpi-grid">
        <article class="kpi-card">
            <span class="kpi-label">Tracked TAs</span>
            <strong>${workloadEntries.size()}</strong>
            <p>All seeded teaching assistants are included in the workload balance view.</p>
        </article>
        <article class="kpi-card">
            <span class="kpi-label">Recent applications</span>
            <strong>${latestApplications.size()}</strong>
            <p>Latest application activity is pulled directly from text-based records.</p>
        </article>
        <article class="kpi-card">
            <span class="kpi-label">Policy threshold</span>
            <strong>${workloadThreshold} h</strong>
            <p>Accepted hours above this line should be treated as overload risk.</p>
        </article>
    </section>

    <main class="dashboard-grid">
        <section class="panel">
            <div class="section-head">
                <h2>TA workload overview</h2>
            </div>
            <div class="table-wrapper">
                <table class="data-table">
                    <thead>
                    <tr>
                        <th>TA</th>
                        <th>Accepted hours</th>
                        <th>Active applications</th>
                        <th>Status</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="entry" items="${workloadEntries}">
                        <tr>
                            <td>
                                <strong>${entry.user.name}</strong>
                                <div class="muted-copy">${entry.user.programme}</div>
                            </td>
                            <td>${entry.acceptedHours} / ${entry.threshold} h</td>
                            <td>${entry.activeApplications}</td>
                            <td>
                                <c:choose>
                                    <c:when test="${entry.overloaded}">
                                        <span class="status-pill status-rejected">Over threshold</span>
                                    </c:when>
                                    <c:otherwise>
                                        <span class="status-pill status-accepted">Balanced</span>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </section>

        <section class="panel">
            <div class="section-head">
                <h2>Latest applications</h2>
            </div>
            <div class="table-wrapper">
                <table class="data-table compact-table">
                    <thead>
                    <tr>
                        <th>Applicant</th>
                        <th>Job</th>
                        <th>Status</th>
                        <th>Score</th>
                        <th>Submitted</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="application" items="${latestApplications}">
                        <tr>
                            <td>${usersById[application.applicantId].name}</td>
                            <td>
                                <a class="inline-link" href="${pageContext.request.contextPath}/jobs/detail?jobId=${application.jobId}">
                                    ${jobsById[application.jobId].title}
                                </a>
                            </td>
                            <td><span class="status-pill status-${application.status.cssClass}">${application.status.label}</span></td>
                            <td>${application.recommendationPercent}%</td>
                            <td>${application.applyTime}</td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </section>
    </main>
</div>
</body>
</html>
