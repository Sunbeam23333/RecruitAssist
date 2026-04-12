# Group38 Project Ownership and GitHub Upload Split Plan (English)

## 1. Purpose

This document is based on the latest `RecruitAssist` project snapshot. The team member list is taken from `homework/Group38-TA_Recruitment/README.md`.

The goal of this plan is to:

- make sure **all 6 members can upload a meaningful part of the project**;
- split the current codebase by **role / feature boundary** as much as possible;
- **avoid modifying the existing `framework/` code right now** and only define upload ownership;
- keep the record under `homework/plan/github-upload-split/` so it does not interfere with the final repository.

> This is not a redevelopment plan. It is a **GitHub contribution / upload ownership plan**.

---

## 2. Team Members

| No. | Name | GitHub Username | Suggested Ownership |
| --- | --- | --- | --- |
| 1 | Yi Qi | `yi-Q945` | Home / Login / README / visual materials |
| 2 | Tianyu Zhao | `DayDreemurr0615` | TA flow and frontend interactions |
| 3 | Jie Ren | `JieJieSAM` | Recruiter (MO) job management |
| 4 | Haopeng Jin | `Sunbeam23333` | Recommendation engine / job detail / candidate ranking |
| 5 | Zhuang Hou | `qiye-cv` | Admin monitoring / workload governance |
| 6 | Zexuan Dong | `GuMiShDo666` | Platform core / storage / bootstrap / seed data |

---

## 3. Splitting Principles

### 3.1 General Rules

- Each member should own **one relatively independent file group**.
- Shared files should have **one clear owner only**.
- If a file belongs to the platform core or shared layer, it must not be uploaded by multiple people casually.
- During the real upload stage, each member should only `git add` and `git commit` the files assigned in this plan.

### 3.2 Shared File Ownership

The following files are conflict-prone and should have a single owner:

- `DashboardServlet.java` → **Zhuang Hou**
- `ApplicationService.java` → **Haopeng Jin**
- `app.css` / `app.js` → **Tianyu Zhao**
- `README.md` / `README_zh.md` / `figure/` → **Yi Qi**
- `data/system/config.json` → **Zhuang Hou**
- `data/system/id-counters.json` / `scripts/generate_demo_load.py` → **Zexuan Dong**

---

## 4. Six-Part Ownership Split

## 4.1 Yi Qi — Home, Login, README, Visual Materials

### Scope

- public landing page and login entry
- outward-facing project documentation
- README figures and presentation assets

### Suggested Files to Upload

- `README.md`
- `README_zh.md`
- `figure/recommendation-engine.png`
- `figure/software-overview.png`
- `figure/system-architecture.png`
- `framework/recruitassist-web/src/main/webapp/WEB-INF/jsp/home.jsp`
- `framework/recruitassist-web/src/main/webapp/WEB-INF/jsp/login.jsp`
- `framework/recruitassist-web/src/main/java/com/recruitassist/web/HomeServlet.java`
- `framework/recruitassist-web/src/main/java/com/recruitassist/web/LoginServlet.java`
- `framework/recruitassist-web/src/main/java/com/recruitassist/web/LogoutServlet.java`

### Covered Features

- landing page presentation
- quick demo sign-in section
- login and logout flow
- project description, running guide, and demo figures

### Suggested Commit Theme

- `docs: polish project readme and landing/login experience`

---

## 4.2 Tianyu Zhao — TA Flow and Frontend Interactions

### Scope

- TA dashboard
- TA profile update
- CV upload / download
- TA-side UI and interaction layer

### Suggested Files to Upload

- `framework/recruitassist-web/src/main/webapp/WEB-INF/jsp/dashboard-ta.jsp`
- `framework/recruitassist-web/src/main/webapp/assets/css/app.css`
- `framework/recruitassist-web/src/main/webapp/assets/js/app.js`
- `framework/recruitassist-web/src/main/java/com/recruitassist/web/UpdateProfileServlet.java`
- `framework/recruitassist-web/src/main/java/com/recruitassist/web/UploadCvServlet.java`
- `framework/recruitassist-web/src/main/java/com/recruitassist/web/DownloadCvServlet.java`
- `framework/recruitassist-web/src/main/java/com/recruitassist/web/ApplyServlet.java`
- `framework/recruitassist-web/src/main/java/com/recruitassist/web/WithdrawApplicationServlet.java`
- `framework/recruitassist-web/src/main/java/com/recruitassist/service/UserService.java`

### Covered Features

- TA profile editing
- CV/PDF upload and download
- TA page interactions, buttons, upload widget, and UI polish
- TA-side entry points for apply / withdraw

### Suggested Commit Theme

- `feat: improve TA dashboard profile and CV interactions`

---

## 4.3 Jie Ren — Recruiter / MO Job Management

### Scope

- Recruiter (MO) dashboard
- job creation, editing, closing, reopening
- recruiter-owned jobs view

### Suggested Files to Upload

- `framework/recruitassist-web/src/main/webapp/WEB-INF/jsp/dashboard-mo.jsp`
- `framework/recruitassist-web/src/main/java/com/recruitassist/web/CreateJobServlet.java`
- `framework/recruitassist-web/src/main/java/com/recruitassist/web/UpdateJobServlet.java`
- `framework/recruitassist-web/src/main/java/com/recruitassist/web/ChangeJobStatusServlet.java`
- `framework/recruitassist-web/src/main/java/com/recruitassist/service/JobService.java`
- related files under `data/jobs/`

### Covered Features

- creating new jobs
- editing jobs
- closing / reopening jobs
- recruiter dashboard owned-jobs presentation

### Suggested Commit Theme

- `feat: add recruiter dashboard and job lifecycle management`

---

## 4.4 Haopeng Jin — Recommendation Engine, Job Detail, Candidate Ranking

### Scope

- recommendation scoring and explanation logic
- job detail page
- candidate review / status update core logic
- application core service layer

### Suggested Files to Upload

- `framework/recruitassist-web/src/main/webapp/WEB-INF/jsp/job-detail.jsp`
- `framework/recruitassist-web/src/main/java/com/recruitassist/web/JobDetailServlet.java`
- `framework/recruitassist-web/src/main/java/com/recruitassist/web/UpdateApplicationStatusServlet.java`
- `framework/recruitassist-web/src/main/java/com/recruitassist/service/ApplicationService.java`
- `framework/recruitassist-web/src/main/java/com/recruitassist/service/RecommendationService.java`
- `framework/recruitassist-web/src/main/java/com/recruitassist/model/view/JobRecommendation.java`
- related files under `data/applications/`

### Covered Features

- recommendation engine
- matching explanations
- multi-role job detail view
- recruiter candidate review and status update
- application snapshot and ranking logic

### Suggested Commit Theme

- `feat: refine recommendation engine and job detail review flow`

---

## 4.5 Zhuang Hou — Admin Monitoring and Workload Governance

### Scope

- admin dashboard
- workload threshold and workload monitoring
- admin filtering view and operational overview

### Suggested Files to Upload

- `framework/recruitassist-web/src/main/webapp/WEB-INF/jsp/dashboard-admin.jsp`
- `framework/recruitassist-web/src/main/java/com/recruitassist/web/DashboardServlet.java`
- `framework/recruitassist-web/src/main/java/com/recruitassist/service/WorkloadService.java`
- `framework/recruitassist-web/src/main/java/com/recruitassist/model/view/WorkloadEntry.java`
- `framework/recruitassist-web/src/main/java/com/recruitassist/model/SystemConfig.java`
- `data/system/config.json`

### Covered Features

- admin overview dashboard
- workload monitoring and threshold logic
- open / closed job filtering
- recent application activity and operational view

### Suggested Commit Theme

- `feat: build admin monitoring and workload governance view`

---

## 4.6 Zexuan Dong — Platform Core, Storage, Bootstrap, Seed Data

### Scope

- application bootstrap and service wiring
- file storage and repository layer
- core data models
- seed data and demo data generation scripts

### Suggested Files to Upload

- `framework/recruitassist-web/pom.xml`
- `framework/recruitassist-web/src/main/webapp/WEB-INF/web.xml`
- `framework/recruitassist-web/src/main/java/com/recruitassist/config/`
- `framework/recruitassist-web/src/main/java/com/recruitassist/repository/`
- `framework/recruitassist-web/src/main/java/com/recruitassist/util/JsonFileStore.java`
- `framework/recruitassist-web/src/main/java/com/recruitassist/web/AppServlet.java`
- `framework/recruitassist-web/src/main/java/com/recruitassist/model/ActionResult.java`
- `framework/recruitassist-web/src/main/java/com/recruitassist/model/ApplicationRecord.java`
- `framework/recruitassist-web/src/main/java/com/recruitassist/model/ApplicationStatus.java`
- `framework/recruitassist-web/src/main/java/com/recruitassist/model/JobPosting.java`
- `framework/recruitassist-web/src/main/java/com/recruitassist/model/JobStatus.java`
- `framework/recruitassist-web/src/main/java/com/recruitassist/model/UserProfile.java`
- `framework/recruitassist-web/src/main/java/com/recruitassist/model/UserRole.java`
- `data/users/`
- `data/system/id-counters.json`
- `scripts/generate_demo_load.py`
- `.gitignore`

### Covered Features

- project startup and service initialization
- JSON file storage
- user / job / application / config repositories
- core data structures
- demo data generation and foundational support

### Suggested Commit Theme

- `chore: maintain core platform storage bootstrap and demo seeds`

---

## 5. Recommended Upload Order

To reduce conflicts, use the following upload order:

1. **Zexuan Dong** uploads the platform core, models, repositories, and seed data first.
2. **Yi Qi** uploads README, home page, login, and figures.
3. **Tianyu Zhao** uploads TA dashboard and frontend interaction files.
4. **Jie Ren** uploads the Recruiter / MO job-management part.
5. **Haopeng Jin** uploads the recommendation engine, job detail, and candidate review logic.
6. **Zhuang Hou** uploads the admin monitoring and workload governance part at the end.

Why this order works:

- foundational dependencies become stable first;
- documentation and entry pages are ready early;
- TA / MO / recommendation / admin modules can then be merged with less overlap;
- the admin layer comes last as the overall monitoring view.

---

## 6. Execution Advice During Upload

### 6.1 Each member should only add their own files

Avoid using:

- `git add .`

Instead use:

- `git add <only the files or directories you own>`

### 6.2 If there is a shared-file conflict

Follow the ownership in this document:

- if you are **not** the owner, do **not** upload the shared file directly;
- if a shared file really must be changed, discuss it in the team first and let the owner submit it.

### 6.3 If the main purpose is to show that everyone contributed

The safest strategy is **not** to artificially split one file into many pieces. Instead:

- each member uploads their own feature package;
- each member makes 1-2 meaningful commits;
- the team can clearly explain who owned which module and who uploaded which part.

---

## 7. Final Recommendation

If you later sync the current project into the `Group38-TA_Recruitment` repository, you can directly follow this split plan. The key points are:

- **do not let six people change the same shared file at the same time**;
- **each member uploads only their assigned package**;
- **upload the platform/core files first, then pages and business features**;
- **treat this document as the official GitHub ownership and upload guide**.

If needed, the next step I can help with is either:

1. a **member-by-member `git add` command list**, or
2. a **separate checklist page for each of the 6 members**.
