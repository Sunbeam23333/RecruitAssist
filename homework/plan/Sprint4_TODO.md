# Sprint 4 TODO — Security Hardening & Performance Optimization

> **Status**: Planned  
> **Target**: Sprint 4 (post-v3.0.0 release)  
> **Priority**: High — these items should be completed before any production deployment

---

## Phase 1: Security Hardening (Must Have)

### 1.1 Password Encryption (BCrypt)

- **Current state**: Passwords stored as plaintext in `data/users/*.json`, compared with `String.equals()` in `AuthService.java:26`
- **Target**: Use BCrypt (or PBKDF2) to hash passwords at rest
- **Files to modify**:
  - [ ] `pom.xml` — add `jBCrypt` or `spring-security-crypto` dependency
  - [ ] `AuthService.java` — replace `equals()` with `BCryptPasswordEncoder.matches()`
  - [ ] `UserProfile.java` — rename `password` field to `passwordHash`
  - [ ] `UserService.java` — hash password on profile creation/update
  - [ ] `data/users/*.json` — migrate existing plaintext passwords to hashed values
  - [ ] `scripts/migrate_passwords.py` — write a one-time migration script
- **Acceptance criteria**:
  - No plaintext passwords in any JSON file
  - All demo accounts still work with `demo123`
  - New user registration (if added) stores hashed password

### 1.2 CSRF Token Protection

- **Current state**: All POST forms lack CSRF token verification
- **Target**: Generate per-session CSRF token, validate on every state-changing request
- **Files to modify**:
  - [ ] `AppServlet.java` — add `generateCsrfToken()` and `validateCsrfToken()` methods
  - [ ] `LoginServlet.java` — generate token on session creation
  - [ ] All POST Servlets — add `validateCsrfToken()` check at the beginning of `doPost()`
  - [ ] All JSP forms — add `<input type="hidden" name="csrfToken" value="${sessionScope.csrfToken}" />`
- **Affected Servlets** (9 total):
  - `ApplyServlet`, `WithdrawApplicationServlet`, `UpdateApplicationStatusServlet`
  - `CreateJobServlet`, `UpdateJobServlet`, `ChangeJobStatusServlet`
  - `UpdateProfileServlet`, `UploadCvServlet`, `LoginServlet`
- **Acceptance criteria**:
  - POST without valid CSRF token returns 403 Forbidden
  - All forms include hidden CSRF field
  - Token regenerated on login

### 1.3 Logging Framework (SLF4J + Logback)

- **Current state**: No logging framework; errors silently caught or re-thrown
- **Target**: Structured logging for debugging, auditing, and monitoring
- **Files to modify**:
  - [ ] `pom.xml` — add `slf4j-api` + `logback-classic` dependencies
  - [ ] `src/main/resources/logback.xml` — configure console + file appender
  - [ ] All Service classes — replace `catch (Exception ignored)` with `log.warn()`/`log.error()`
  - [ ] All Servlet classes — add request logging in `doGet()`/`doPost()`
  - [ ] `AppBootstrapListener.java` — log startup events
- **Acceptance criteria**:
  - Application logs to `logs/app.log` with timestamps
  - Exceptions are logged with stack traces
  - No `System.out.println` or `catch (Exception ignored)` remains

---

## Phase 2: Performance Optimization (Should Have)

### 2.1 Recommendation Result Caching

- **Current state**: `ApplicationService.refreshRecommendationSnapshots()` recalculates recommendation scores on EVERY read operation (lines 353-384)
- **Impact**: With 500+ applications, each dashboard load triggers hundreds of `recommend()` calls
- **Target**: Cache recommendation scores with time-based expiration
- **Implementation plan**:
  - [ ] Add `ConcurrentHashMap<String, CachedRecommendation>` with TTL (5 minutes)
  - [ ] Cache key: `userId::jobId`
  - [ ] Invalidate cache on: application submit/withdraw, job update, profile update
  - [ ] Add cache hit/miss metrics logging
- **Files to modify**:
  - [ ] `RecommendationService.java` — add caching layer around `recommend()`
  - [ ] `ApplicationService.java` — remove per-read recalculation, use cached values
  - [ ] `UserService.java` — invalidate recommendation cache on profile update
  - [ ] `JobService.java` — invalidate recommendation cache on job update
- **Expected improvement**: 80-90% reduction in redundant computation

### 2.2 Concurrent Lock Optimization

- **Current state**: `JobService.listAllJobs()` uses **write lock** for a mostly-read operation (line 45-51)
- **Impact**: Read operations block each other unnecessarily
- **Target**: Use read lock for queries, upgrade to write lock only when state synchronization is needed
- **Files to modify**:
  - [ ] `JobService.java` — refactor `listAllJobs()` to use read lock with conditional write lock upgrade
  - [ ] `ApplicationService.java` — review lock usage in `findAll()`, `findByApplicantId()`, etc.
- **Implementation notes**:
  - Use "check-then-upgrade" pattern: read lock → check if update needed → release read → acquire write → double-check → update
  - Consider using `StampedLock` for optimistic reads where appropriate
- **Acceptance criteria**:
  - Concurrent reads no longer block each other
  - No data corruption under concurrent access
  - Load test with 50 concurrent users shows improved response time

---

## Phase 3: Additional Improvements (Nice to Have)

### 3.1 Global Error Handler

- [ ] Create `ErrorHandlerServlet.java` mapped to `/error`
- [ ] Create `error.jsp` with user-friendly error page
- [ ] Configure `<error-page>` in `web.xml` for 404, 500

### 3.2 Input Validation Enhancement

- [ ] Add file magic byte validation in `UploadCvServlet` (not just extension check)
- [ ] Add path traversal double-check in file operations
- [ ] Add rate limiting for login attempts

### 3.3 Test Coverage

- [ ] Add unit tests for `RecommendationService` (skill matching, scoring)
- [ ] Add unit tests for `ApplicationService` (state transitions)
- [ ] Add integration tests for Servlet request/response cycle
- [ ] Target: > 70% line coverage

---

## Estimation

| Task | Story Points | Assignee | Sprint |
|------|-------------|----------|--------|
| BCrypt Password Encryption | 5 | TBD | Sprint 4 |
| CSRF Token Protection | 3 | TBD | Sprint 4 |
| Logging Framework | 3 | TBD | Sprint 4 |
| Recommendation Caching | 5 | TBD | Sprint 4 |
| Lock Optimization | 3 | TBD | Sprint 4 |
| Global Error Handler | 2 | TBD | Sprint 4 |
| Input Validation | 2 | TBD | Sprint 4 |
| Test Coverage | 8 | TBD | Sprint 4 |
| **Total** | **31** | | |
