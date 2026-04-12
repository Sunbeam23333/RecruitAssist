<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>TA Dashboard · RecruitAssist Sprint 1</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/app.css" />
    <script defer src="${pageContext.request.contextPath}/assets/js/app.js"></script>
</head>
<body class="page-dashboard role-ta">
<div class="page-shell">
    <header class="hero-card home-hero dashboard-hero">
        <div>
            <div class="badge">TA Dashboard</div>
            <h1>Welcome, ${user.name}</h1>
            <div class="hero-metrics">
                <div class="hero-metric"><strong>${openJobCount}</strong><span>Open jobs</span></div>
                <div class="hero-metric"><strong>${user.skills.size()}</strong><span>Listed skills</span></div>
                <div class="hero-metric"><strong><c:choose><c:when test="${profileReady}">Ready</c:when><c:otherwise>Incomplete</c:otherwise></c:choose></strong><span>Profile status</span></div>
            </div>
        </div>
        <aside class="spotlight-card">
            <div class="spotlight-kicker">Sprint 1 focus</div>
            <div class="spotlight-score">TA<span> flow</span></div>
            <h3>Profile then apply</h3>
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
                <h2>TA profile</h2>
                <span class="status-pill status-ta"><c:choose><c:when test="${profileReady}">Profile ready</c:when><c:otherwise>Needs update</c:otherwise></c:choose></span>
            </div>

            <form class="form-grid two-column-form section-gap" method="post" action="${pageContext.request.contextPath}/profile/update">
                <label class="field-group">
                    <span>Full name</span>
                    <input class="input" type="text" name="name" value="${user.name}" required />
                </label>
                <label class="field-group">
                    <span>Student ID</span>
                    <input class="input" type="text" name="studentId" value="${user.studentId}" required />
                </label>
                <label class="field-group">
                    <span>Email</span>
                    <input class="input" type="email" name="email" value="${user.email}" />
                </label>
                <label class="field-group">
                    <span>Programme</span>
                    <input class="input" type="text" name="programme" value="${user.programme}" required />
                </label>
                <div class="field-group full-width">
                    <span>Skills</span>
                    <textarea class="textarea" name="skills" rows="3" required>${user.skillsSummary}</textarea>
                </div>
                <div class="field-group full-width">
                    <span>Availability</span>
                    <textarea class="textarea" name="availability" rows="2" required>${user.availability}</textarea>
                </div>
                <div class="form-actions full-width">
                    <button class="primary-button" type="submit" data-loading-text="Saving profile...">Save profile</button>
                </div>
            </form>

            <div class="detail-pairs compact-pairs section-gap">
                <div class="detail-pair">
                    <span>Current CV file</span>
                    <strong>${user.cvFileLabel}</strong>
                </div>
                <div class="detail-pair">
                    <span>Last uploaded</span>
                    <strong>${user.cvUploadedAtLabel}</strong>
                </div>
            </div>

            <form class="form-grid section-gap" method="post" action="${pageContext.request.contextPath}/profile/cv/upload" enctype="multipart/form-data">
                <label class="upload-zone" data-upload-zone>
                    <span class="upload-zone-title">Upload CV</span>
                    <span class="upload-zone-hint" data-upload-filename>Drop your file here or choose a file</span>
                    <span class="upload-zone-meta" data-upload-hint>Accepted: PDF, DOC, DOCX, TXT · max 5MB.</span>
                    <span class="secondary-button small-button upload-trigger">Choose file</span>
                    <input class="upload-input" type="file" name="cvFile" accept=".pdf,.doc,.docx,.txt" data-max-mb="5" required />
                </label>
                <div class="action-row">
                    <button class="secondary-button" type="submit" data-loading-text="Uploading CV...">Upload CV</button>
                </div>
            </form>
        </section>

        <section class="panel">
            <div class="section-head">
                <h2>Browse TA positions</h2>
                <span class="metric-pill">${jobSortLabel}</span>
            </div>

            <form class="filter-bar section-gap" method="get" action="${pageContext.request.contextPath}/dashboard">
                <label class="field-group compact-field">
                    <span>Search jobs</span>
                    <input class="input" type="text" name="q" value="${jobSearchQuery}" placeholder="Search by module, title, or skill" />
                </label>
                <label class="field-group compact-field">
                    <span>Sort by</span>
                    <select class="select" name="sort">
                        <option value="${jobSort}">${jobSortLabel} (current)</option>
                        <c:if test="${jobSort != 'deadline'}"><option value="deadline">Closest deadline first</option></c:if>
                        <c:if test="${jobSort != 'module'}"><option value="module">Module name</option></c:if>
                    </select>
                </label>
                <div class="form-actions compact-actions">
                    <button class="secondary-button small-button" type="submit">Apply</button>
                    <a class="secondary-button small-button" href="${pageContext.request.contextPath}/dashboard">Reset</a>
                </div>
            </form>

            <div class="card-grid section-gap">
                <c:forEach var="job" items="${jobs}">
                    <c:set var="existingApplication" value="${applicationsByJobId[job.jobId]}" />
                    <article class="job-card" style="${job.open ? '' : 'opacity:0.65;'}">
                        <div class="section-head">
                            <div>
                                <div class="spotlight-kicker">${job.moduleCode}</div>
                                <h3>${job.title}</h3>
                            </div>
                            <span class="status-pill status-${job.status.cssClass}">${job.status.label}</span>
                        </div>
                        <p class="muted-copy">${job.description}</p>
                        <div class="job-meta-row section-gap">
                            <span class="meta-chip">Required: ${job.requiredSkillsSummary}</span>
                            <span class="meta-chip">Hours/week: ${job.workloadHours}</span>
                            <span class="meta-chip">Deadline: ${job.deadlineLabel}</span>
                            <span class="meta-chip">Positions: ${job.quota}</span>
                            <span class="meta-chip">Applicants: ${applicationCounts[job.jobId] == null ? 0 : applicationCounts[job.jobId]}</span>
                        </div>
                        <div class="action-row section-gap">
                            <a class="secondary-button small-button" href="${pageContext.request.contextPath}/jobs/detail?jobId=${job.jobId}">View details</a>
                            <c:choose>
                                <c:when test="${existingApplication != null}">
                                    <span class="status-pill status-${existingApplication.status.cssClass}">Applied · ${existingApplication.status.label}</span>
                                </c:when>
                                <c:when test="${job.open}">
                                    <form class="inline-form inline-form-tight" method="post" action="${pageContext.request.contextPath}/apply">
                                        <input type="hidden" name="jobId" value="${job.jobId}" />
                                        <button class="primary-button small-button" type="submit" data-loading-text="Applying...">Apply</button>
                                    </form>
                                </c:when>
                                <c:otherwise>
                                    <span class="muted-copy">Closed</span>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </article>
                </c:forEach>
                <c:if test="${empty jobs}">
                    <div class="empty-state large-empty">No jobs match your current search.</div>
                </c:if>
            </div>
        </section>
    </main>
</div>
</body>
</html>
