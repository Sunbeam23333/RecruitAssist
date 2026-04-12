# 项目_Yi_Qi 上传说明

## 1. 本包负责人

- **姓名**：Yi Qi
- **负责内容**：首页、登录、README、展示素材
- **本次上传策略**：按 `YiQi/Sprint1`、`YiQi/Sprint2`、`YiQi/Sprint3` 三个分支分三次上传
- **建议节奏**：每次只同步本 Sprint 对应文件，不再整包一次性 `git add`

## 2. 本包全部文件总览

- `framework/recruitassist-web/src/main/webapp/index.jsp`
- `framework/recruitassist-web/src/main/webapp/WEB-INF/jsp/login.jsp`
- `framework/recruitassist-web/src/main/java/com/recruitassist/web/LoginServlet.java`
- `framework/recruitassist-web/src/main/java/com/recruitassist/web/LogoutServlet.java`
- `README.md`
- `README_zh.md`
- `framework/recruitassist-web/src/main/webapp/WEB-INF/jsp/home.jsp`
- `framework/recruitassist-web/src/main/java/com/recruitassist/web/HomeServlet.java`
- `figure/recommendation-engine.png`
- `figure/software-overview.png`
- `figure/system-architecture.png`
- `PERFORMANCE_EVOLUTION.md`

## 3. 分三次上传方案

> 建议每次都从远程最新 `main` 新开分支；如果上一批刚被 revert，也不要复用旧分支，直接按下面分支名重新开新分支即可。

### 3.1 Sprint 1 - 系统入口与登录流程

- **分支名**：`YiQi/Sprint1`
- **建议 commit message**：`feat: add landing page and login flow`
- **本次上传文件**：

  - `framework/recruitassist-web/src/main/webapp/index.jsp`
  - `framework/recruitassist-web/src/main/webapp/WEB-INF/jsp/login.jsp`
  - `framework/recruitassist-web/src/main/java/com/recruitassist/web/LoginServlet.java`
  - `framework/recruitassist-web/src/main/java/com/recruitassist/web/LogoutServlet.java`

```bash
PACKAGE_DIR="/absolute/path/to/项目_Yi_Qi"
GROUP_REPO="/absolute/path/to/Group38-TA_Recruitment"

git -C "$GROUP_REPO" checkout main
git -C "$GROUP_REPO" pull origin main
git -C "$GROUP_REPO" checkout -b "YiQi/Sprint1"

cd "$PACKAGE_DIR"
rsync -avR \
    "framework/recruitassist-web/src/main/webapp/index.jsp" \
    "framework/recruitassist-web/src/main/webapp/WEB-INF/jsp/login.jsp" \
    "framework/recruitassist-web/src/main/java/com/recruitassist/web/LoginServlet.java" \
    "framework/recruitassist-web/src/main/java/com/recruitassist/web/LogoutServlet.java" \
    "$GROUP_REPO/"

git -C "$GROUP_REPO" status --short
git -C "$GROUP_REPO" add \
    "framework/recruitassist-web/src/main/webapp/index.jsp" \
    "framework/recruitassist-web/src/main/webapp/WEB-INF/jsp/login.jsp" \
    "framework/recruitassist-web/src/main/java/com/recruitassist/web/LoginServlet.java" \
    "framework/recruitassist-web/src/main/java/com/recruitassist/web/LogoutServlet.java"

git -C "$GROUP_REPO" commit -m "feat: add landing page and login flow"
git -C "$GROUP_REPO" push -u origin "YiQi/Sprint1"
```

### 3.2 Sprint 2 - 主页展示与 README 文档

- **分支名**：`YiQi/Sprint2`
- **建议 commit message**：`docs: add home page flow and bilingual readme`
- **本次上传文件**：

  - `README.md`
  - `README_zh.md`
  - `framework/recruitassist-web/src/main/webapp/WEB-INF/jsp/home.jsp`
  - `framework/recruitassist-web/src/main/java/com/recruitassist/web/HomeServlet.java`

```bash
PACKAGE_DIR="/absolute/path/to/项目_Yi_Qi"
GROUP_REPO="/absolute/path/to/Group38-TA_Recruitment"

git -C "$GROUP_REPO" checkout main
git -C "$GROUP_REPO" pull origin main
git -C "$GROUP_REPO" checkout -b "YiQi/Sprint2"

cd "$PACKAGE_DIR"
rsync -avR \
    "README.md" \
    "README_zh.md" \
    "framework/recruitassist-web/src/main/webapp/WEB-INF/jsp/home.jsp" \
    "framework/recruitassist-web/src/main/java/com/recruitassist/web/HomeServlet.java" \
    "$GROUP_REPO/"

git -C "$GROUP_REPO" status --short
git -C "$GROUP_REPO" add \
    "README.md" \
    "README_zh.md" \
    "framework/recruitassist-web/src/main/webapp/WEB-INF/jsp/home.jsp" \
    "framework/recruitassist-web/src/main/java/com/recruitassist/web/HomeServlet.java"

git -C "$GROUP_REPO" commit -m "docs: add home page flow and bilingual readme"
git -C "$GROUP_REPO" push -u origin "YiQi/Sprint2"
```

### 3.3 Sprint 3 - 架构图与性能演进材料

- **分支名**：`YiQi/Sprint3`
- **建议 commit message**：`docs: add architecture figures and performance notes`
- **本次上传文件**：

  - `figure/recommendation-engine.png`
  - `figure/software-overview.png`
  - `figure/system-architecture.png`
  - `PERFORMANCE_EVOLUTION.md`

```bash
PACKAGE_DIR="/absolute/path/to/项目_Yi_Qi"
GROUP_REPO="/absolute/path/to/Group38-TA_Recruitment"

git -C "$GROUP_REPO" checkout main
git -C "$GROUP_REPO" pull origin main
git -C "$GROUP_REPO" checkout -b "YiQi/Sprint3"

cd "$PACKAGE_DIR"
rsync -avR \
    "figure/recommendation-engine.png" \
    "figure/software-overview.png" \
    "figure/system-architecture.png" \
    "PERFORMANCE_EVOLUTION.md" \
    "$GROUP_REPO/"

git -C "$GROUP_REPO" status --short
git -C "$GROUP_REPO" add \
    "figure/recommendation-engine.png" \
    "figure/software-overview.png" \
    "figure/system-architecture.png" \
    "PERFORMANCE_EVOLUTION.md"

git -C "$GROUP_REPO" commit -m "docs: add architecture figures and performance notes"
git -C "$GROUP_REPO" push -u origin "YiQi/Sprint3"
```

## 4. 注意事项

- **不要执行** `git add .`
- **不要执行** 整包 `rsync -av "$PACKAGE_DIR"/ ./` 后再一次性提交
- 每次只同步当前 Sprint 小节列出的文件
- 如果某个目录（如 `data/applications`、`data/jobs`、`data/cv`）已经在某一批提交，就不要在其他批次重复提交同一目录
- 如果前一批还没合入 `main`，下一批开始前先和组内确认是否需要 rebase 或等待合并
