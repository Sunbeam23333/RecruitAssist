<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>${appName}</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/app.css" />
</head>
<body>
<div class="page-shell">
    <header class="hero-card home-hero dashboard-hero">
        <div>
            <div class="badge">Course-ready Java Web Prototype</div>
            <h1>${appName}</h1>
            <p class="subtitle">A polished Servlet/JSP prototype for the Teaching Assistant Recruitment System, with explainable recommendations, operational dashboards and demo-ready seeded data.</p>
            <p class="stack">${stack}</p>
            <div class="hero-metrics">
                <div class="hero-metric"><strong>3 roles</strong><span>TA · MO · Admin</span></div>
                <div class="hero-metric"><strong>10+ flows</strong><span>Apply, withdraw, review, publish and balance</span></div>
                <div class="hero-metric"><strong>Text storage</strong><span>No database setup required for demos</span></div>
            </div>
            <div class="hero-actions section-gap">
                <a class="primary-button" href="${pageContext.request.contextPath}/login">Open demo login</a>
                <div class="hero-note">All seeded demo accounts use password <strong>${demoPassword}</strong>.</div>
            </div>
        </div>
        <aside class="spotlight-card">
            <div class="spotlight-kicker">Demo-ready experience</div>
            <div class="spotlight-score">Live<span> prototype</span></div>
            <h3>What you can show right now</h3>
            <p class="muted-copy">The current build already supports explainable TA ranking, MO job lifecycle management, candidate review ordering and workload-aware admin visibility.</p>
            <div class="spotlight-meta">
                <span class="metric-pill">Explainable ranking</span>
                <span class="metric-pill">Unified detail view</span>
                <span class="metric-pill">CloudStudio previewable</span>
            </div>
        </aside>
    </header>

    <section class="showcase-grid">
        <article class="feature-card">
            <div class="spotlight-kicker">TA workflow</div>
            <h3>Find the best-fit jobs faster</h3>
            <p class="muted-copy">Recommendations now combine skill coverage, profile evidence, workload projection and competition pressure into a single explainable score.</p>
        </article>
        <article class="feature-card">
            <div class="spotlight-kicker">MO workflow</div>
            <h3>Manage roles without context switching</h3>
            <p class="muted-copy">Publish, edit, close, reopen and review candidates from the same role-aware dashboard with cleaner summaries.</p>
        </article>
        <article class="feature-card">
            <div class="spotlight-kicker">Admin workflow</div>
            <h3>Monitor workload risk clearly</h3>
            <p class="muted-copy">Workload thresholds and recent application signals remain visible for operational checks and demo explanation.</p>
        </article>
    </section>

    <main class="content-grid dashboard-grid single-column-grid">
        <section class="panel">
            <div class="section-head">
                <h2>Project layout</h2>
                <span class="status-pill status-open">Ready</span>
            </div>
            <ul class="info-list">
                <li><strong>Framework</strong><span>${frameworkDir}</span></li>
                <li><strong>Data</strong><span>${dataDir}</span></li>
                <li><strong>Logs</strong><span>${logsDir}</span></li>
            </ul>
        </section>

        <section class="panel">
            <div class="section-head">
                <h2>What is already implemented</h2>
                <span class="metric-pill">Current demo scope</span>
            </div>
            <div class="chip-row">
                <span class="chip">Seeded demo accounts</span>
                <span class="chip">Role-aware login</span>
                <span class="chip">Explainable TA recommendations</span>
                <span class="chip">Application submission</span>
                <span class="chip">TA application withdrawal</span>
                <span class="chip">MO job publishing</span>
                <span class="chip">MO job editing &amp; closure</span>
                <span class="chip">Unified job detail page</span>
                <span class="chip">Candidate sorting &amp; review</span>
                <span class="chip">Admin workload view</span>
            </div>
        </section>

        <section class="panel">
            <div class="section-head">
                <h2>Suggested demo path</h2>
                <span class="metric-pill">3 quick steps</span>
            </div>
            <div class="journey-grid">
                <article class="journey-step">
                    <div class="spotlight-kicker">Step 1</div>
                    <h3>Login as a TA</h3>
                    <p class="muted-copy">Open the recommendation dashboard, inspect the new multi-signal fit score and apply to a role.</p>
                </article>
                <article class="journey-step">
                    <div class="spotlight-kicker">Step 2</div>
                    <h3>Switch to an MO</h3>
                    <p class="muted-copy">Show job publishing, candidate ranking and the ability to update or close a posting from the same workspace.</p>
                </article>
                <article class="journey-step">
                    <div class="spotlight-kicker">Step 3</div>
                    <h3>Finish as Admin</h3>
                    <p class="muted-copy">Explain how workload thresholds and recent applications support fairer allocation decisions.</p>
                </article>
            </div>
        </section>
    </main>
</div>
</body>
</html>
