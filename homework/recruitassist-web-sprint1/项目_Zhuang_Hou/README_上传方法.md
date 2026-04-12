# 项目_Zhuang_Hou 上传说明

## 1. 本包负责人

- **姓名**：Zhuang Hou
- **负责内容**：Admin 监控、工作量治理、全局 dashboard 分流
- **本次上传策略**：按 `ZhuangHou/Sprint1`、`ZhuangHou/Sprint2`、`ZhuangHou/Sprint3` 三个分支分三次上传
- **建议节奏**：每次只同步本 Sprint 对应文件，不再整包一次性 `git add`

## 2. 本包全部文件总览

- `framework/recruitassist-web/src/main/webapp/WEB-INF/jsp/dashboard-admin.jsp`
- `framework/recruitassist-web/src/main/java/com/recruitassist/web/DashboardServlet.java`
- `framework/recruitassist-web/src/main/java/com/recruitassist/service/WorkloadService.java`
- `framework/recruitassist-web/src/main/java/com/recruitassist/model/view/WorkloadEntry.java`
- `framework/recruitassist-web/src/main/java/com/recruitassist/model/SystemConfig.java`
- `data/system/config.json`

## 3. 分三次上传方案

> 建议每次都从远程最新 `main` 新开分支；如果上一批刚被 revert，也不要复用旧分支，直接按下面分支名重新开新分支即可。

### 3.1 Sprint 1 - Admin dashboard 入口

- **分支名**：`ZhuangHou/Sprint1`
- **建议 commit message**：`feat: add admin dashboard entry`
- **本次上传文件**：

  - `framework/recruitassist-web/src/main/webapp/WEB-INF/jsp/dashboard-admin.jsp`
  - `framework/recruitassist-web/src/main/java/com/recruitassist/web/DashboardServlet.java`

```bash
PACKAGE_DIR="/absolute/path/to/项目_Zhuang_Hou"
GROUP_REPO="/absolute/path/to/Group38-TA_Recruitment"

git -C "$GROUP_REPO" checkout main
git -C "$GROUP_REPO" pull origin main
git -C "$GROUP_REPO" checkout -b "ZhuangHou/Sprint1"

cd "$PACKAGE_DIR"
rsync -avR \
    "framework/recruitassist-web/src/main/webapp/WEB-INF/jsp/dashboard-admin.jsp" \
    "framework/recruitassist-web/src/main/java/com/recruitassist/web/DashboardServlet.java" \
    "$GROUP_REPO/"

git -C "$GROUP_REPO" status --short
git -C "$GROUP_REPO" add \
    "framework/recruitassist-web/src/main/webapp/WEB-INF/jsp/dashboard-admin.jsp" \
    "framework/recruitassist-web/src/main/java/com/recruitassist/web/DashboardServlet.java"

git -C "$GROUP_REPO" commit -m "feat: add admin dashboard entry"
git -C "$GROUP_REPO" push -u origin "ZhuangHou/Sprint1"
```

### 3.2 Sprint 2 - 工作量统计服务与视图模型

- **分支名**：`ZhuangHou/Sprint2`
- **建议 commit message**：`feat: add workload service and view model`
- **本次上传文件**：

  - `framework/recruitassist-web/src/main/java/com/recruitassist/service/WorkloadService.java`
  - `framework/recruitassist-web/src/main/java/com/recruitassist/model/view/WorkloadEntry.java`

```bash
PACKAGE_DIR="/absolute/path/to/项目_Zhuang_Hou"
GROUP_REPO="/absolute/path/to/Group38-TA_Recruitment"

git -C "$GROUP_REPO" checkout main
git -C "$GROUP_REPO" pull origin main
git -C "$GROUP_REPO" checkout -b "ZhuangHou/Sprint2"

cd "$PACKAGE_DIR"
rsync -avR \
    "framework/recruitassist-web/src/main/java/com/recruitassist/service/WorkloadService.java" \
    "framework/recruitassist-web/src/main/java/com/recruitassist/model/view/WorkloadEntry.java" \
    "$GROUP_REPO/"

git -C "$GROUP_REPO" status --short
git -C "$GROUP_REPO" add \
    "framework/recruitassist-web/src/main/java/com/recruitassist/service/WorkloadService.java" \
    "framework/recruitassist-web/src/main/java/com/recruitassist/model/view/WorkloadEntry.java"

git -C "$GROUP_REPO" commit -m "feat: add workload service and view model"
git -C "$GROUP_REPO" push -u origin "ZhuangHou/Sprint2"
```

### 3.3 Sprint 3 - 系统配置与治理参数

- **分支名**：`ZhuangHou/Sprint3`
- **建议 commit message**：`chore: add admin config for workload governance`
- **本次上传文件**：

  - `framework/recruitassist-web/src/main/java/com/recruitassist/model/SystemConfig.java`
  - `data/system/config.json`

```bash
PACKAGE_DIR="/absolute/path/to/项目_Zhuang_Hou"
GROUP_REPO="/absolute/path/to/Group38-TA_Recruitment"

git -C "$GROUP_REPO" checkout main
git -C "$GROUP_REPO" pull origin main
git -C "$GROUP_REPO" checkout -b "ZhuangHou/Sprint3"

cd "$PACKAGE_DIR"
rsync -avR \
    "framework/recruitassist-web/src/main/java/com/recruitassist/model/SystemConfig.java" \
    "data/system/config.json" \
    "$GROUP_REPO/"

git -C "$GROUP_REPO" status --short
git -C "$GROUP_REPO" add \
    "framework/recruitassist-web/src/main/java/com/recruitassist/model/SystemConfig.java" \
    "data/system/config.json"

git -C "$GROUP_REPO" commit -m "chore: add admin config for workload governance"
git -C "$GROUP_REPO" push -u origin "ZhuangHou/Sprint3"
```

## 4. 注意事项

- **不要执行** `git add .`
- **不要执行** 整包 `rsync -av "$PACKAGE_DIR"/ ./` 后再一次性提交
- 每次只同步当前 Sprint 小节列出的文件
- 如果某个目录（如 `data/applications`、`data/jobs`、`data/cv`）已经在某一批提交，就不要在其他批次重复提交同一目录
- 如果前一批还没合入 `main`，下一批开始前先和组内确认是否需要 rebase 或等待合并
