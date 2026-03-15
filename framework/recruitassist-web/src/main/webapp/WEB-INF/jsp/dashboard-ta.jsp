<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>TA Dashboard · RecruitAssist</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/app.css" />
</head>
<body>
<div class="page-shell">
    <header class="hero-card home-hero dashboard-hero">
        <div>
            <div class="badge">TA Dashboard</div>
            <h1>Welcome back, ${user.name}</h1>
            <p class="subtitle">Track your workload, monitor application progress and focus on the most relevant opportunities with richer recommendation signals.</p>
            <div class="hero-metrics">
                <div class="hero-metric"><strong>${currentWorkload}/${workloadThreshold} h</strong><span>Current accepted workload</span></div>
                <div class="hero-metric"><strong>${activeApplicationCount}</strong><span>Active applications</span></div>
                <div class="hero-metric"><strong>${profileSignalPercent}%</strong><span>Profile evidence readiness</span></div>
            </div>
        </div>
        <c:choose>
            <c:when test="${topRecommendation != null}">
                <aside class="spotlight-card">
                    <div class="spotlight-kicker">Top recommendation</div>
                    <div class="spotlight-score">${topRecommendation.scorePercent}<span>% match</span></div>
                    <h3>${topRecommendation.job.title}</h3>
                    <p class="muted-copy">${topRecommendation.job.moduleCode} · ${topRecommendation.fitLabel} · ${topRecommendation.actionLabel}</p>
                    <div class="spotlight-meta">
                        <span class="metric-pill">${topRecommendation.evidenceLabel}</span>
                        <span class="metric-pill">${topRecommendation.competitionSummary}</span>
                    </div>
                    <div class="action-row">
                        <a class="secondary-button small-button" href="${pageContext.request.contextPath}/jobs/detail?jobId=${topRecommendation.job.jobId}">Open top job</a>
                        <a class="secondary-button small-button" href="${pageContext.request.contextPath}/logout">Log out</a>
                    </div>
                </aside>
            </c:when>
            <c:otherwise>
                <aside class="spotlight-card">
                    <div class="spotlight-kicker">No open roles</div>
                    <div class="spotlight-score">0<span> live matches</span></div>
                    <h3>Nothing to apply for right now</h3>
                    <p class="muted-copy">Once more roles are published, this panel will highlight the best opportunity based on your profile evidence and workload balance.</p>
                    <div class="action-row">
                        <a class="secondary-button small-button" href="${pageContext.request.contextPath}/logout">Log out</a>
                    </div>
                </aside>
            </c:otherwise>
        </c:choose>
    </header>

    <c:if test="${not empty flashMessage}">
        <div class="alert ${flashTone}">${flashMessage}</div>
    </c:if>

    <section class="kpi-grid">
        <article class="kpi-card">
            <span class="kpi-label">Role</span>
            <strong>${user.role.label}</strong>
            <p>${user.programme}</p>
        </article>
        <article class="kpi-card">
            <span class="kpi-label">Skills</span>
            <strong>${user.skills.size()}</strong>
            <p>${user.skillsSummary}</p>
        </article>
        <article class="kpi-card">
            <span class="kpi-label">Accepted offers</span>
            <strong>${acceptedApplicationCount}</strong>
            <p>Accepted applications already contributing to your committed workload.</p>
        </article>
        <article class="kpi-card">
            <span class="kpi-label">Profile signals</span>
            <strong>${profileSignalCount}/4</strong>
            <p>Skills, availability, experience and CV text all strengthen explainable recommendations.</p>
        </article>
    </section>

    <main class="dashboard-grid">
        <section class="panel">
            <div class="section-head">
                <h2>Profile snapshot</h2>
                <span class="status-pill status-ta">${user.role.label}</span>
            </div>
            <div class="detail-pairs">
                <div class="detail-pair"><span>Student ID</span><strong>${user.studentId}</strong></div>
                <div class="detail-pair"><span>Availability</span><strong>${user.availability}</strong></div>
                <div class="detail-pair full-width"><span>Skills</span><strong>${user.skillsSummary}</strong></div>
                <div class="detail-pair full-width"><span>Experience</span><strong>${user.experience}</strong></div>
                <div class="detail-pair full-width"><span>CV extract</span><strong>${user.cvText}</strong></div>
            </div>
        </section>

        <section class="panel">
            <div class="section-head">
                <h2>Application history</h2>
                <span class="metric-pill">Live recommendation snapshot</span>
            </div>
            <div class="table-wrapper">
                <table class="data-table compact-table">
                    <thead>
                    <tr>
                        <th>Job</th>
                        <th>Status</th>
                        <th>Score</th>
                        <th>Submitted</th>
                        <th>Action</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="application" items="${applications}">
                        <c:set var="applicationJob" value="${jobsById[application.jobId]}" />
                        <tr>
                            <td>
                                <strong>
                                    <a class="inline-link" href="${pageContext.request.contextPath}/jobs/detail?jobId=${application.jobId}">
                                        ${applicationJob.title}
                                    </a>
                                </strong>
                                <div class="muted-copy">${applicationJob.moduleCode}</div>
                            </td>
                            <td><span class="status-pill status-${application.status.cssClass}">${application.status.label}</span></td>
                            <td>${application.recommendationPercent}%</td>
                            <td>${application.applyTime}</td>
                            <td>
                                <c:choose>
                                    <c:when test="${application.status.code != 'WITHDRAWN' and application.status.code != 'REJECTED'}">
                                        <form class="inline-form inline-form-tight" method="post" action="${pageContext.request.contextPath}/applications/withdraw">
                                            <input type="hidden" name="applicationId" value="${application.applicationId}" />
                                            <input type="hidden" name="jobId" value="${application.jobId}" />
                                            <button class="secondary-button small-button" type="submit">Withdraw</button>
                                        </form>
                                    </c:when>
                                    <c:when test="${application.status.code == 'WITHDRAWN' and applicationJob.open}">
                                        <span class="muted-copy">You can apply again while this job stays open.</span>
                                    </c:when>
                                    <c:otherwise>
                                        <span class="muted-copy">No action</span>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                        </tr>
                    </c:forEach>
                    <c:if test="${empty applications}">
                        <tr><td colspan="5" class="empty-state">You have not applied for any job yet.</td></tr>
                    </c:if>
                    </tbody>
                </table>
            </div>
        </section>
    </main>

    <section class="panel section-gap">
        <div class="section-head">
            <div>
                <h2>Recommended open jobs</h2>
                <p class="muted-copy">Each ranking now blends skill coverage, availability quality, experience evidence, post-acceptance workload and current competition.</p>
            </div>
            <span class="metric-pill">Explainable ranking</span>
        </div>
        <div class="card-grid">
            <c:forEach var="recommendation" items="${recommendedJobs}">
                <c:set var="existingApplication" value="${applicationsByJobId[recommendation.job.jobId]}" />
                <article class="job-card">
                    <div class="job-card-header">
                        <div>
                            <h3>
                                <a class="inline-link" href="${pageContext.request.contextPath}/jobs/detail?jobId=${recommendation.job.jobId}">
                                    ${recommendation.job.title}
                                </a>
                            </h3>
                            <p class="muted-copy">${recommendation.job.moduleCode} · Deadline ${recommendation.job.deadlineLabel}</p>
                        </div>
                        <div class="top-actions">
                            <span class="metric-pill">${recommendation.fitLabel}</span>
                            <span class="score-badge">${recommendation.scorePercent}%</span>
                        </div>
                    </div>

                    <div class="job-meta-row">
                        <span class="meta-chip"><strong>${recommendation.actionLabel}</strong></span>
                        <span class="meta-chip">${recommendation.job.workloadHours}h role load</span>
                        <span class="meta-chip">${recommendation.projectedWorkloadLabel}</span>
                        <span class="meta-chip">${recommendation.competitionSummary}</span>
                    </div>

                    <div class="detail-pairs compact-pairs">
                        <div class="detail-pair"><span>Required</span><strong>${recommendation.job.requiredSkillsSummary}</strong></div>
                        <div class="detail-pair"><span>Preferred</span><strong>${recommendation.job.preferredSkillsSummary}</strong></div>
                        <div class="detail-pair"><span>Matched</span><strong>${recommendation.matchedSkillsSummary}</strong></div>
                        <div class="detail-pair"><span>Preferred matched</span><strong>${recommendation.preferredMatchedSkillsSummary}</strong></div>
                        <div class="detail-pair full-width"><span>Missing</span><strong>${recommendation.missingSkillsSummary}</strong></div>
                    </div>

                    <div class="signal-grid section-gap">
                        <div class="signal-card">
                            <div class="signal-head"><span class="signal-label">Skill match</span><strong class="signal-value">${recommendation.skillScorePercent}%</strong></div>
                            <div class="progress-track"><div class="progress-fill progress-fill-brand" style="width:${recommendation.skillScorePercent}%"></div></div>
                            <div class="signal-note">Required and preferred skill coverage.</div>
                        </div>
                        <div class="signal-card">
                            <div class="signal-head"><span class="signal-label">Availability</span><strong class="signal-value">${recommendation.availabilityScorePercent}%</strong></div>
                            <div class="progress-track"><div class="progress-fill progress-fill-success" style="width:${recommendation.availabilityScorePercent}%"></div></div>
                            <div class="signal-note">How usable your stated availability is for planning.</div>
                        </div>
                        <div class="signal-card">
                            <div class="signal-head"><span class="signal-label">Experience</span><strong class="signal-value">${recommendation.experienceScorePercent}%</strong></div>
                            <div class="progress-track"><div class="progress-fill progress-fill-brand" style="width:${recommendation.experienceScorePercent}%"></div></div>
                            <div class="signal-note">Role-relevant evidence found in experience and CV text.</div>
                        </div>
                        <div class="signal-card">
                            <div class="signal-head"><span class="signal-label">Workload balance</span><strong class="signal-value">${recommendation.workloadBalanceScorePercent}%</strong></div>
                            <div class="progress-track"><div class="progress-fill progress-fill-warning" style="width:${recommendation.workloadBalanceScorePercent}%"></div></div>
                            <div class="signal-note">Projected load if this application succeeds.</div>
                        </div>
                        <div class="signal-card">
                            <div class="signal-head"><span class="signal-label">Profile evidence</span><strong class="signal-value">${recommendation.profileEvidenceScorePercent}%</strong></div>
                            <div class="progress-track"><div class="progress-fill progress-fill-success" style="width:${recommendation.profileEvidenceScorePercent}%"></div></div>
                            <div class="signal-note">Completeness and consistency of your profile.</div>
                        </div>
                        <div class="signal-card">
                            <div class="signal-head"><span class="signal-label">Competition</span><strong class="signal-value">${recommendation.competitionScorePercent}%</strong></div>
                            <div class="progress-track"><div class="progress-fill progress-fill-danger" style="width:${recommendation.competitionScorePercent}%"></div></div>
                            <div class="signal-note">A higher score means the current demand pressure is more manageable.</div>
                        </div>
                    </div>

                    <ul class="reason-list section-gap">
                        <c:forEach var="reason" items="${recommendation.reasons}">
                            <li>${reason}</li>
                        </c:forEach>
                    </ul>

                    <div class="action-row">
                        <a class="secondary-button small-button" href="${pageContext.request.contextPath}/jobs/detail?jobId=${recommendation.job.jobId}">View details</a>
                        <c:choose>
                            <c:when test="${existingApplication != null}">
                                <span class="status-pill status-${existingApplication.status.cssClass}">Applied · ${existingApplication.status.label}</span>
                            </c:when>
                            <c:otherwise>
                                <form class="inline-form inline-form-tight" method="post" action="${pageContext.request.contextPath}/apply">
                                    <input type="hidden" name="jobId" value="${recommendation.job.jobId}" />
                                    <button class="primary-button small-button" type="submit">Apply now</button>
                                </form>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </article>
            </c:forEach>
        </div>
        <c:if test="${empty recommendedJobs}">
            <div class="empty-state large-empty">No open jobs are currently available for recommendation.</div>
        </c:if>
    </section>
</div>
</body>
</html>
