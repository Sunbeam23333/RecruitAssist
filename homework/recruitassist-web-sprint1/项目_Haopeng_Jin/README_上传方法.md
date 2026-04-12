# 项目_Haopeng_Jin 上传说明

## 1. 本包负责人

- **姓名**：Haopeng Jin
- **负责内容**：推荐引擎、岗位详情、候选人排序与申请服务层
- **本次上传策略**：按 `HaopengJin/Sprint1`、`HaopengJin/Sprint2`、`HaopengJin/Sprint3` 三个分支分三次上传
- **建议节奏**：每次只同步本 Sprint 对应文件，不再整包一次性 `git add`

## 2. 本包全部文件总览

- `framework/recruitassist-web/src/main/webapp/WEB-INF/jsp/job-detail.jsp`
- `framework/recruitassist-web/src/main/java/com/recruitassist/web/JobDetailServlet.java`
- `framework/recruitassist-web/src/main/java/com/recruitassist/web/UpdateApplicationStatusServlet.java`
- `framework/recruitassist-web/src/main/java/com/recruitassist/service/ApplicationService.java`
- `data/applications`
- `framework/recruitassist-web/src/main/java/com/recruitassist/service/RecommendationService.java`
- `framework/recruitassist-web/src/main/java/com/recruitassist/model/view/JobRecommendation.java`

## 3. 分三次上传方案

> 建议每次都从远程最新 `main` 新开分支；如果上一批刚被 revert，也不要复用旧分支，直接按下面分支名重新开新分支即可。

### 3.1 Sprint 1 - 岗位详情浏览入口

- **分支名**：`HaopengJin/Sprint1`
- **建议 commit message**：`feat: add job detail page and servlet`
- **本次上传文件**：

  - `framework/recruitassist-web/src/main/webapp/WEB-INF/jsp/job-detail.jsp`
  - `framework/recruitassist-web/src/main/java/com/recruitassist/web/JobDetailServlet.java`

```bash
PACKAGE_DIR="/absolute/path/to/项目_Haopeng_Jin"
GROUP_REPO="/absolute/path/to/Group38-TA_Recruitment"

git -C "$GROUP_REPO" checkout main
git -C "$GROUP_REPO" pull origin main
git -C "$GROUP_REPO" checkout -b "HaopengJin/Sprint1"

cd "$PACKAGE_DIR"
rsync -avR \
    "framework/recruitassist-web/src/main/webapp/WEB-INF/jsp/job-detail.jsp" \
    "framework/recruitassist-web/src/main/java/com/recruitassist/web/JobDetailServlet.java" \
    "$GROUP_REPO/"

git -C "$GROUP_REPO" status --short
git -C "$GROUP_REPO" add \
    "framework/recruitassist-web/src/main/webapp/WEB-INF/jsp/job-detail.jsp" \
    "framework/recruitassist-web/src/main/java/com/recruitassist/web/JobDetailServlet.java"

git -C "$GROUP_REPO" commit -m "feat: add job detail page and servlet"
git -C "$GROUP_REPO" push -u origin "HaopengJin/Sprint1"
```

### 3.2 Sprint 2 - 申请状态处理与申请数据

- **分支名**：`HaopengJin/Sprint2`
- **建议 commit message**：`feat: add application review flow and application dataset`
- **本次上传文件**：

  - `framework/recruitassist-web/src/main/java/com/recruitassist/web/UpdateApplicationStatusServlet.java`
  - `framework/recruitassist-web/src/main/java/com/recruitassist/service/ApplicationService.java`
  - `data/applications`

```bash
PACKAGE_DIR="/absolute/path/to/项目_Haopeng_Jin"
GROUP_REPO="/absolute/path/to/Group38-TA_Recruitment"

git -C "$GROUP_REPO" checkout main
git -C "$GROUP_REPO" pull origin main
git -C "$GROUP_REPO" checkout -b "HaopengJin/Sprint2"

cd "$PACKAGE_DIR"
rsync -avR \
    "framework/recruitassist-web/src/main/java/com/recruitassist/web/UpdateApplicationStatusServlet.java" \
    "framework/recruitassist-web/src/main/java/com/recruitassist/service/ApplicationService.java" \
    "data/applications" \
    "$GROUP_REPO/"

git -C "$GROUP_REPO" status --short
git -C "$GROUP_REPO" add \
    "framework/recruitassist-web/src/main/java/com/recruitassist/web/UpdateApplicationStatusServlet.java" \
    "framework/recruitassist-web/src/main/java/com/recruitassist/service/ApplicationService.java" \
    "data/applications"

git -C "$GROUP_REPO" commit -m "feat: add application review flow and application dataset"
git -C "$GROUP_REPO" push -u origin "HaopengJin/Sprint2"
```

### 3.3 Sprint 3 - 推荐排序引擎

- **分支名**：`HaopengJin/Sprint3`
- **建议 commit message**：`feat: add recommendation service and ranking model`
- **本次上传文件**：

  - `framework/recruitassist-web/src/main/java/com/recruitassist/service/RecommendationService.java`
  - `framework/recruitassist-web/src/main/java/com/recruitassist/model/view/JobRecommendation.java`

```bash
PACKAGE_DIR="/absolute/path/to/项目_Haopeng_Jin"
GROUP_REPO="/absolute/path/to/Group38-TA_Recruitment"

git -C "$GROUP_REPO" checkout main
git -C "$GROUP_REPO" pull origin main
git -C "$GROUP_REPO" checkout -b "HaopengJin/Sprint3"

cd "$PACKAGE_DIR"
rsync -avR \
    "framework/recruitassist-web/src/main/java/com/recruitassist/service/RecommendationService.java" \
    "framework/recruitassist-web/src/main/java/com/recruitassist/model/view/JobRecommendation.java" \
    "$GROUP_REPO/"

git -C "$GROUP_REPO" status --short
git -C "$GROUP_REPO" add \
    "framework/recruitassist-web/src/main/java/com/recruitassist/service/RecommendationService.java" \
    "framework/recruitassist-web/src/main/java/com/recruitassist/model/view/JobRecommendation.java"

git -C "$GROUP_REPO" commit -m "feat: add recommendation service and ranking model"
git -C "$GROUP_REPO" push -u origin "HaopengJin/Sprint3"
```

## 4. 注意事项

- **不要执行** `git add .`
- **不要执行** 整包 `rsync -av "$PACKAGE_DIR"/ ./` 后再一次性提交
- 每次只同步当前 Sprint 小节列出的文件
- 如果某个目录（如 `data/applications`、`data/jobs`、`data/cv`）已经在某一批提交，就不要在其他批次重复提交同一目录
- 如果前一批还没合入 `main`，下一批开始前先和组内确认是否需要 rebase 或等待合并
