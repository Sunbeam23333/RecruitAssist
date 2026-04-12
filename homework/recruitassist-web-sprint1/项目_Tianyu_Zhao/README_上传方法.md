# 项目_Tianyu_Zhao 上传说明

## 1. 本包负责人

- **姓名**：Tianyu Zhao
- **负责内容**：TA 流程、CV 上传下载、前端样式与交互
- **本次上传策略**：按 `TianyuZhao/Sprint1`、`TianyuZhao/Sprint2`、`TianyuZhao/Sprint3` 三个分支分三次上传
- **建议节奏**：每次只同步本 Sprint 对应文件，不再整包一次性 `git add`

## 2. 本包全部文件总览

- `framework/recruitassist-web/src/main/webapp/WEB-INF/jsp/dashboard-ta.jsp`
- `framework/recruitassist-web/src/main/java/com/recruitassist/web/UpdateProfileServlet.java`
- `framework/recruitassist-web/src/main/java/com/recruitassist/service/UserService.java`
- `framework/recruitassist-web/src/main/java/com/recruitassist/web/UploadCvServlet.java`
- `framework/recruitassist-web/src/main/java/com/recruitassist/web/DownloadCvServlet.java`
- `data/cv`
- `framework/recruitassist-web/src/main/webapp/assets/css/app.css`
- `framework/recruitassist-web/src/main/webapp/assets/js/app.js`
- `framework/recruitassist-web/src/main/java/com/recruitassist/web/ApplyServlet.java`
- `framework/recruitassist-web/src/main/java/com/recruitassist/web/WithdrawApplicationServlet.java`

## 3. 分三次上传方案

> 建议每次都从远程最新 `main` 新开分支；如果上一批刚被 revert，也不要复用旧分支，直接按下面分支名重新开新分支即可。

### 3.1 Sprint 1 - TA 档案与个人信息编辑

- **分支名**：`TianyuZhao/Sprint1`
- **建议 commit message**：`feat: add TA dashboard and profile editing`
- **本次上传文件**：

  - `framework/recruitassist-web/src/main/webapp/WEB-INF/jsp/dashboard-ta.jsp`
  - `framework/recruitassist-web/src/main/java/com/recruitassist/web/UpdateProfileServlet.java`
  - `framework/recruitassist-web/src/main/java/com/recruitassist/service/UserService.java`

```bash
PACKAGE_DIR="/absolute/path/to/项目_Tianyu_Zhao"
GROUP_REPO="/absolute/path/to/Group38-TA_Recruitment"

git -C "$GROUP_REPO" checkout main
git -C "$GROUP_REPO" pull origin main
git -C "$GROUP_REPO" checkout -b "TianyuZhao/Sprint1"

cd "$PACKAGE_DIR"
rsync -avR \
    "framework/recruitassist-web/src/main/webapp/WEB-INF/jsp/dashboard-ta.jsp" \
    "framework/recruitassist-web/src/main/java/com/recruitassist/web/UpdateProfileServlet.java" \
    "framework/recruitassist-web/src/main/java/com/recruitassist/service/UserService.java" \
    "$GROUP_REPO/"

git -C "$GROUP_REPO" status --short
git -C "$GROUP_REPO" add \
    "framework/recruitassist-web/src/main/webapp/WEB-INF/jsp/dashboard-ta.jsp" \
    "framework/recruitassist-web/src/main/java/com/recruitassist/web/UpdateProfileServlet.java" \
    "framework/recruitassist-web/src/main/java/com/recruitassist/service/UserService.java"

git -C "$GROUP_REPO" commit -m "feat: add TA dashboard and profile editing"
git -C "$GROUP_REPO" push -u origin "TianyuZhao/Sprint1"
```

### 3.2 Sprint 2 - CV 上传下载能力

- **分支名**：`TianyuZhao/Sprint2`
- **建议 commit message**：`feat: add CV upload and download flow`
- **本次上传文件**：

  - `framework/recruitassist-web/src/main/java/com/recruitassist/web/UploadCvServlet.java`
  - `framework/recruitassist-web/src/main/java/com/recruitassist/web/DownloadCvServlet.java`
  - `data/cv`

```bash
PACKAGE_DIR="/absolute/path/to/项目_Tianyu_Zhao"
GROUP_REPO="/absolute/path/to/Group38-TA_Recruitment"

git -C "$GROUP_REPO" checkout main
git -C "$GROUP_REPO" pull origin main
git -C "$GROUP_REPO" checkout -b "TianyuZhao/Sprint2"

cd "$PACKAGE_DIR"
rsync -avR \
    "framework/recruitassist-web/src/main/java/com/recruitassist/web/UploadCvServlet.java" \
    "framework/recruitassist-web/src/main/java/com/recruitassist/web/DownloadCvServlet.java" \
    "data/cv" \
    "$GROUP_REPO/"

git -C "$GROUP_REPO" status --short
git -C "$GROUP_REPO" add \
    "framework/recruitassist-web/src/main/java/com/recruitassist/web/UploadCvServlet.java" \
    "framework/recruitassist-web/src/main/java/com/recruitassist/web/DownloadCvServlet.java" \
    "data/cv"

git -C "$GROUP_REPO" commit -m "feat: add CV upload and download flow"
git -C "$GROUP_REPO" push -u origin "TianyuZhao/Sprint2"
```

### 3.3 Sprint 3 - 申请撤回与前端交互优化

- **分支名**：`TianyuZhao/Sprint3`
- **建议 commit message**：`feat: add apply withdraw flow and frontend interactions`
- **本次上传文件**：

  - `framework/recruitassist-web/src/main/webapp/assets/css/app.css`
  - `framework/recruitassist-web/src/main/webapp/assets/js/app.js`
  - `framework/recruitassist-web/src/main/java/com/recruitassist/web/ApplyServlet.java`
  - `framework/recruitassist-web/src/main/java/com/recruitassist/web/WithdrawApplicationServlet.java`

```bash
PACKAGE_DIR="/absolute/path/to/项目_Tianyu_Zhao"
GROUP_REPO="/absolute/path/to/Group38-TA_Recruitment"

git -C "$GROUP_REPO" checkout main
git -C "$GROUP_REPO" pull origin main
git -C "$GROUP_REPO" checkout -b "TianyuZhao/Sprint3"

cd "$PACKAGE_DIR"
rsync -avR \
    "framework/recruitassist-web/src/main/webapp/assets/css/app.css" \
    "framework/recruitassist-web/src/main/webapp/assets/js/app.js" \
    "framework/recruitassist-web/src/main/java/com/recruitassist/web/ApplyServlet.java" \
    "framework/recruitassist-web/src/main/java/com/recruitassist/web/WithdrawApplicationServlet.java" \
    "$GROUP_REPO/"

git -C "$GROUP_REPO" status --short
git -C "$GROUP_REPO" add \
    "framework/recruitassist-web/src/main/webapp/assets/css/app.css" \
    "framework/recruitassist-web/src/main/webapp/assets/js/app.js" \
    "framework/recruitassist-web/src/main/java/com/recruitassist/web/ApplyServlet.java" \
    "framework/recruitassist-web/src/main/java/com/recruitassist/web/WithdrawApplicationServlet.java"

git -C "$GROUP_REPO" commit -m "feat: add apply withdraw flow and frontend interactions"
git -C "$GROUP_REPO" push -u origin "TianyuZhao/Sprint3"
```

## 4. 注意事项

- **不要执行** `git add .`
- **不要执行** 整包 `rsync -av "$PACKAGE_DIR"/ ./` 后再一次性提交
- 每次只同步当前 Sprint 小节列出的文件
- 如果某个目录（如 `data/applications`、`data/jobs`、`data/cv`）已经在某一批提交，就不要在其他批次重复提交同一目录
- 如果前一批还没合入 `main`，下一批开始前先和组内确认是否需要 rebase 或等待合并
