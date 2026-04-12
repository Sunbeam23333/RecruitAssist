# 项目_Jie_Ren 上传说明

## 1. 本包负责人

- **姓名**：Jie Ren
- **负责内容**：Recruiter/MO 岗位管理与 recruiter dashboard
- **本次上传策略**：按 `JieRen/Sprint1`、`JieRen/Sprint2`、`JieRen/Sprint3` 三个分支分三次上传
- **建议节奏**：每次只同步本 Sprint 对应文件，不再整包一次性 `git add`

## 2. 本包全部文件总览

- `framework/recruitassist-web/src/main/webapp/WEB-INF/jsp/dashboard-mo.jsp`
- `framework/recruitassist-web/src/main/java/com/recruitassist/web/CreateJobServlet.java`
- `framework/recruitassist-web/src/main/java/com/recruitassist/web/UpdateJobServlet.java`
- `framework/recruitassist-web/src/main/java/com/recruitassist/service/JobService.java`
- `framework/recruitassist-web/src/main/java/com/recruitassist/web/ChangeJobStatusServlet.java`
- `data/jobs`

## 3. 分三次上传方案

> 建议每次都从远程最新 `main` 新开分支；如果上一批刚被 revert，也不要复用旧分支，直接按下面分支名重新开新分支即可。

### 3.1 Sprint 1 - 岗位发布入口与 recruiter dashboard

- **分支名**：`JieRen/Sprint1`
- **建议 commit message**：`feat: add recruiter dashboard and job creation entry`
- **本次上传文件**：

  - `framework/recruitassist-web/src/main/webapp/WEB-INF/jsp/dashboard-mo.jsp`
  - `framework/recruitassist-web/src/main/java/com/recruitassist/web/CreateJobServlet.java`

```bash
PACKAGE_DIR="/absolute/path/to/项目_Jie_Ren"
GROUP_REPO="/absolute/path/to/Group38-TA_Recruitment"

git -C "$GROUP_REPO" checkout main
git -C "$GROUP_REPO" pull origin main
git -C "$GROUP_REPO" checkout -b "JieRen/Sprint1"

cd "$PACKAGE_DIR"
rsync -avR \
    "framework/recruitassist-web/src/main/webapp/WEB-INF/jsp/dashboard-mo.jsp" \
    "framework/recruitassist-web/src/main/java/com/recruitassist/web/CreateJobServlet.java" \
    "$GROUP_REPO/"

git -C "$GROUP_REPO" status --short
git -C "$GROUP_REPO" add \
    "framework/recruitassist-web/src/main/webapp/WEB-INF/jsp/dashboard-mo.jsp" \
    "framework/recruitassist-web/src/main/java/com/recruitassist/web/CreateJobServlet.java"

git -C "$GROUP_REPO" commit -m "feat: add recruiter dashboard and job creation entry"
git -C "$GROUP_REPO" push -u origin "JieRen/Sprint1"
```

### 3.2 Sprint 2 - 岗位编辑与服务层支撑

- **分支名**：`JieRen/Sprint2`
- **建议 commit message**：`feat: add job update flow and service support`
- **本次上传文件**：

  - `framework/recruitassist-web/src/main/java/com/recruitassist/web/UpdateJobServlet.java`
  - `framework/recruitassist-web/src/main/java/com/recruitassist/service/JobService.java`

```bash
PACKAGE_DIR="/absolute/path/to/项目_Jie_Ren"
GROUP_REPO="/absolute/path/to/Group38-TA_Recruitment"

git -C "$GROUP_REPO" checkout main
git -C "$GROUP_REPO" pull origin main
git -C "$GROUP_REPO" checkout -b "JieRen/Sprint2"

cd "$PACKAGE_DIR"
rsync -avR \
    "framework/recruitassist-web/src/main/java/com/recruitassist/web/UpdateJobServlet.java" \
    "framework/recruitassist-web/src/main/java/com/recruitassist/service/JobService.java" \
    "$GROUP_REPO/"

git -C "$GROUP_REPO" status --short
git -C "$GROUP_REPO" add \
    "framework/recruitassist-web/src/main/java/com/recruitassist/web/UpdateJobServlet.java" \
    "framework/recruitassist-web/src/main/java/com/recruitassist/service/JobService.java"

git -C "$GROUP_REPO" commit -m "feat: add job update flow and service support"
git -C "$GROUP_REPO" push -u origin "JieRen/Sprint2"
```

### 3.3 Sprint 3 - 岗位状态切换与岗位数据

- **分支名**：`JieRen/Sprint3`
- **建议 commit message**：`feat: add job status management and seed jobs data`
- **本次上传文件**：

  - `framework/recruitassist-web/src/main/java/com/recruitassist/web/ChangeJobStatusServlet.java`
  - `data/jobs`

```bash
PACKAGE_DIR="/absolute/path/to/项目_Jie_Ren"
GROUP_REPO="/absolute/path/to/Group38-TA_Recruitment"

git -C "$GROUP_REPO" checkout main
git -C "$GROUP_REPO" pull origin main
git -C "$GROUP_REPO" checkout -b "JieRen/Sprint3"

cd "$PACKAGE_DIR"
rsync -avR \
    "framework/recruitassist-web/src/main/java/com/recruitassist/web/ChangeJobStatusServlet.java" \
    "data/jobs" \
    "$GROUP_REPO/"

git -C "$GROUP_REPO" status --short
git -C "$GROUP_REPO" add \
    "framework/recruitassist-web/src/main/java/com/recruitassist/web/ChangeJobStatusServlet.java" \
    "data/jobs"

git -C "$GROUP_REPO" commit -m "feat: add job status management and seed jobs data"
git -C "$GROUP_REPO" push -u origin "JieRen/Sprint3"
```

## 4. 注意事项

- **不要执行** `git add .`
- **不要执行** 整包 `rsync -av "$PACKAGE_DIR"/ ./` 后再一次性提交
- 每次只同步当前 Sprint 小节列出的文件
- 如果某个目录（如 `data/applications`、`data/jobs`、`data/cv`）已经在某一批提交，就不要在其他批次重复提交同一目录
- 如果前一批还没合入 `main`，下一批开始前先和组内确认是否需要 rebase 或等待合并
