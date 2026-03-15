<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Login · RecruitAssist</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/app.css" />
</head>
<body>
<div class="page-shell narrow-shell">
    <header class="hero-card compact-hero">
        <div class="badge">Demo access</div>
        <h1>Sign in to RecruitAssist</h1>
        <p class="subtitle">Use a seeded account below to test TA, MO and Admin workflows.</p>
    </header>

    <c:if test="${not empty flashMessage}">
        <div class="alert ${flashTone}">${flashMessage}</div>
    </c:if>
    <c:if test="${not empty error}">
        <div class="alert error">${error}</div>
    </c:if>

    <main class="content-grid login-layout">
        <section class="panel">
            <div class="section-head">
                <h2>Login form</h2>
            </div>
            <form class="form-grid" method="post" action="${pageContext.request.contextPath}/login">
                <label class="field-group">
                    <span>Username</span>
                    <input class="input" type="text" name="username" value="${username}" placeholder="e.g. alice.ta" required />
                </label>
                <label class="field-group">
                    <span>Password</span>
                    <input class="input" type="password" name="password" placeholder="${demoPassword}" required />
                </label>
                <button class="primary-button" type="submit">Sign in</button>
                <a class="secondary-button" href="${pageContext.request.contextPath}/home">Back to home</a>
            </form>
        </section>

        <section class="panel">
            <div class="section-head">
                <h2>Seeded demo accounts</h2>
                <span class="metric-pill">Password: ${demoPassword}</span>
            </div>
            <div class="table-wrapper">
                <table class="data-table compact-table">
                    <thead>
                    <tr>
                        <th>Name</th>
                        <th>Role</th>
                        <th>Username</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="demoUser" items="${demoUsers}">
                        <tr>
                            <td>
                                <strong>${demoUser.name}</strong>
                                <div class="muted-copy">${demoUser.programme}</div>
                            </td>
                            <td><span class="status-pill status-${demoUser.role.cssClass}">${demoUser.role.label}</span></td>
                            <td><code>${demoUser.username}</code></td>
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
