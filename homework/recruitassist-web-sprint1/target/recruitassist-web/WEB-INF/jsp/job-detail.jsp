<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>${job.title} · RecruitAssist Sprint 1</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/app.css" />
    <script defer src="${pageContext.request.contextPath}/assets/js/app.js"></script>
</head>
<body class="page-detail">
<div class="page-shell">
    <header class="hero-card home-hero dashboard-hero">
        <div>
            <div class="badge">Job Detail</div>
            <h1>${job.title}</h1>
            <div class="hero-metrics">
                <div class="hero-metric"><strong>${applicationCount}</strong><span>Applicants</span></div>
                <div class="hero-metric"><strong>${job.quota}</strong><span>Positions</span></div>
                <div class="hero-metric"><strong>${job.workloadHours} h</strong><span>Hours per week</span></div>
            </div>
        </div>
        <aside class="spotlight-card">
            <div class="spotlight-kicker">Current status</div>
            <div class="spotlight-score">${job.status.label}<span> job</span></div>
            <h3>${job.deadlineStatusLabel}</h3>
            <div class="action-row">
                <a class="secondary-button small-button" href="${pageContext.request.contextPath}/dashboard">Back to dashboard</a>
            </div>
        </aside>
    </header>

    <c:if test="${not empty flashMessage}">
        <div class="alert ${flashTone}">${flashMessage}</div>
    </c:if>

    <main class="dashboard-grid">
        <section class="panel">
            <div class="section-head">
                <h2>Job information</h2>
            </div>
            <div class="detail-pairs">
                <div class="detail-pair"><span>Module code</span><strong>${job.moduleCode}</strong></div>
                <div class="detail-pair"><span>Status</span><strong>${job.status.label}</strong></div>
                <div class="detail-pair"><span>Deadline</span><strong>${job.deadlineLabel}</strong></div>
                <div class="detail-pair"><span>Quota</span><strong>${job.quota}</strong></div>
                <div class="detail-pair"><span>Required skills</span><strong>${job.requiredSkillsSummary}</strong></div>
                <div class="detail-pair"><span>Weekly hours</span><strong>${job.workloadHours}</strong></div>
                <div class="detail-pair full-width"><span>Description</span><strong>${job.description}</strong></div>
            </div>
        </section>

        <section class="panel">
            <div class="section-head">
                <h2>Action</h2>
            </div>
            <c:choose>
                <c:when test="${taView}">
                    <c:choose>
                        <c:when test="${existingApplication != null}">
                            <p class="muted-copy">You have already applied for this job.</p>
                            <span class="status-pill status-${existingApplication.status.cssClass}">${existingApplication.status.label}</span>
                        </c:when>
                        <c:when test="${canApplyToJob}">
                            <p class="muted-copy">Your profile is ready and this role is open for applications.</p>
                            <form class="inline-form inline-form-tight section-gap" method="post" action="${pageContext.request.contextPath}/apply">
                                <input type="hidden" name="jobId" value="${job.jobId}" />
                                <input type="hidden" name="returnTo" value="detail" />
                                <button class="primary-button" type="submit" data-loading-text="Submitting...">Apply for this job</button>
                            </form>
                        </c:when>
                        <c:otherwise>
                            <p class="muted-copy">This role is closed or no longer accepting applications.</p>
                            <span class="status-pill status-${job.status.cssClass}">${job.status.label}</span>
                        </c:otherwise>
                    </c:choose>
                </c:when>
                <c:when test="${ownerView}">
                    <span class="status-pill status-open">Read only</span>
                </c:when>
                <c:otherwise>
                    <span class="status-pill status-open">Read only</span>
                </c:otherwise>
            </c:choose>
        </section>
    </main>
</div>
</body>
</html>
