# RecruitAssist

> Chinese version: [README_zh.md](./README_zh.md)

RecruitAssist is a lightweight **Java Servlet/JSP prototype** for a Teaching Assistant recruitment workflow. It is designed for coursework demos and rapid local preview, with **role-aware dashboards**, **explainable job recommendations**, and **file-based storage** so the system can run without setting up a database.

## Visual Overview

### Software overview

![RecruitAssist software overview](./figure/software-overview.svg)

### Recommendation engine

![RecruitAssist recommendation engine](./figure/recommendation-engine.svg)

## Highlights

- **3 demo roles**: TA, MO (Module Organiser), and Admin
- **Explainable recommendations** for TAs based on skills, availability, experience, workload balance, and competition pressure
- **Role-aware dashboards** with different views and permissions after login
- **Unified job detail page** that adapts to TA, MO, and Admin workflows
- **Local JSON / CSV / TXT storage** with seeded demo data and no database dependency
- **Demo-friendly flow** for showcasing apply, review, publish, close, reopen, and workload monitoring scenarios

## Demo Roles

| Role | Example account | What you can demonstrate |
| --- | --- | --- |
| TA | `alice.ta`, `ben.ta` | Browse recommended jobs, inspect fit explanations, submit applications, withdraw applications, review personal workload |
| MO | `mo.chen` | Create jobs, edit jobs, close/reopen postings, inspect ranked candidates, update application status |
| Admin | `admin.sarah` | View workload balance, monitor recent application activity, inspect operational status |

> Current seeded demo password: `demo123`  
> This is intentionally simple for coursework demonstration and **must not** be reused in production.

## What is implemented

- Role-aware login and dashboard routing
- TA recommendation list with explainable scoring
- Job application submission and withdrawal
- MO job publishing, editing, closing, and reopening
- Candidate queue review with sorting and status updates
- Admin workload overview and recent application monitoring
- File-based persistence for users, jobs, applications, and audit records

## Tech Stack

- **Java 17**
- **Maven**
- **Jakarta Servlet 6**
- **JSP + JSTL**
- **Gson**
- **WAR packaging**
- **JSON / CSV / TXT** storage

## Project Structure

```text
RecruitAssist/
├── data/                       # Seeded users, jobs, applications, and system config
├── figure/                     # README diagrams and showcase assets
├── framework/
│   └── recruitassist-web/      # Java web application (Servlet/JSP)
├── logs/                       # Runtime and audit logs
├── scripts/                    # Java / Maven helper scripts
└── README.md / README_zh.md
```

## Quick Start

### Prerequisites

- Java 17
- Maven 3.9+
- macOS users can use the helper scripts already included in `scripts/`

### Run locally with the helper script

From the project root:

```bash
RECRUITASSIST_BASE_DIR=$(pwd) zsh scripts/mvn17.sh -f framework/recruitassist-web/pom.xml org.eclipse.jetty.ee10:jetty-ee10-maven-plugin:12.0.15:run -Djetty.http.port=8081 -Djetty.contextPath=/
```

Then open:

```text
http://127.0.0.1:8081/
```

### Alternative: run with your own Java / Maven setup

```bash
cd framework/recruitassist-web
RECRUITASSIST_BASE_DIR=$(cd ../.. && pwd) mvn org.eclipse.jetty.ee10:jetty-ee10-maven-plugin:12.0.15:run -Djetty.http.port=8081 -Djetty.contextPath=/
```

## Demo Walkthrough Suggestion

1. **Login as TA** and show explainable job recommendations.
2. Open a **job detail page** and submit an application.
3. Switch to **MO** and review candidates for a posting.
4. Change an application status or close / reopen a job.
5. Finish with **Admin** to explain workload visibility and fairness checks.

## Data & Configuration

The project intentionally uses **text-file storage only**:

- `data/users/` – user profiles and demo accounts
- `data/jobs/` – job postings
- `data/applications/` – submitted applications
- `data/system/config.json` – recommendation and workload configuration
- `logs/access/audit.csv` – audit trail

Current configuration includes workload and recommendation weights such as:

- skill match: `0.5`
- availability: `0.2`
- experience: `0.15`
- workload balance: `0.15`

These values can be adjusted in `data/system/config.json`.

## Recommendation Logic

A core feature of RecruitAssist is the **explainable recommendation engine**. Instead of listing open jobs only, the system computes a fit score by combining:

- skill overlap
- availability alignment
- profile / CV evidence
- projected workload impact
- competition pressure

This makes the prototype more useful for both demonstration and discussion around fairness and allocation quality.

## Notes

- This project is best understood as a **coursework prototype**, not a production-ready hiring system.
- Authentication is intentionally simplified for demonstration.
- The system is optimized for easy preview, seeded data, and clear explanation of user flows.
