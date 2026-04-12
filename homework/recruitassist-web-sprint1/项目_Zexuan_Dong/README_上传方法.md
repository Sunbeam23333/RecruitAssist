# 项目_Zexuan_Dong 上传说明

## 1. 本包负责人

- **姓名**：Zexuan Dong
- **负责内容**：平台底层、存储、配置、模型、脚本与基础数据
- **本次上传策略**：按 `ZexuanDong/Sprint1`、`ZexuanDong/Sprint2`、`ZexuanDong/Sprint3` 三个分支分三次上传
- **建议节奏**：每次只同步本 Sprint 对应文件，不再整包一次性 `git add`

## 2. 本包全部文件总览

- `.gitignore`
- `framework/recruitassist-web/pom.xml`
- `framework/recruitassist-web/src/main/webapp/WEB-INF/web.xml`
- `framework/recruitassist-web/src/main/java/com/recruitassist/config`
- `framework/recruitassist-web/src/main/java/com/recruitassist/web/AppServlet.java`
- `scripts/mvn17.sh`
- `framework/recruitassist-web/src/main/java/com/recruitassist/repository`
- `framework/recruitassist-web/src/main/java/com/recruitassist/util/JsonFileStore.java`
- `framework/recruitassist-web/src/main/java/com/recruitassist/service/AuthService.java`
- `data/users`
- `data/system/id-counters.json`
- `framework/recruitassist-web/src/test/java/com/recruitassist/config/AppPathsTest.java`
- `framework/recruitassist-web/src/main/java/com/recruitassist/model/ActionResult.java`
- `framework/recruitassist-web/src/main/java/com/recruitassist/model/ApplicationRecord.java`
- `framework/recruitassist-web/src/main/java/com/recruitassist/model/ApplicationStatus.java`
- `framework/recruitassist-web/src/main/java/com/recruitassist/model/JobPosting.java`
- `framework/recruitassist-web/src/main/java/com/recruitassist/model/JobStatus.java`
- `framework/recruitassist-web/src/main/java/com/recruitassist/model/UserProfile.java`
- `framework/recruitassist-web/src/main/java/com/recruitassist/model/UserRole.java`
- `scripts/generate_demo_load.py`

## 3. 分三次上传方案

> 建议每次都从远程最新 `main` 新开分支；如果上一批刚被 revert，也不要复用旧分支，直接按下面分支名重新开新分支即可。

### 3.1 Sprint 1 - 项目基础配置与启动入口

- **分支名**：`ZexuanDong/Sprint1`
- **建议 commit message**：`chore: add project bootstrap config and web setup`
- **本次上传文件**：

  - `.gitignore`
  - `framework/recruitassist-web/pom.xml`
  - `framework/recruitassist-web/src/main/webapp/WEB-INF/web.xml`
  - `framework/recruitassist-web/src/main/java/com/recruitassist/config`
  - `framework/recruitassist-web/src/main/java/com/recruitassist/web/AppServlet.java`
  - `scripts/mvn17.sh`

```bash
PACKAGE_DIR="/absolute/path/to/项目_Zexuan_Dong"
GROUP_REPO="/absolute/path/to/Group38-TA_Recruitment"

git -C "$GROUP_REPO" checkout main
git -C "$GROUP_REPO" pull origin main
git -C "$GROUP_REPO" checkout -b "ZexuanDong/Sprint1"

cd "$PACKAGE_DIR"
rsync -avR \
    ".gitignore" \
    "framework/recruitassist-web/pom.xml" \
    "framework/recruitassist-web/src/main/webapp/WEB-INF/web.xml" \
    "framework/recruitassist-web/src/main/java/com/recruitassist/config" \
    "framework/recruitassist-web/src/main/java/com/recruitassist/web/AppServlet.java" \
    "scripts/mvn17.sh" \
    "$GROUP_REPO/"

git -C "$GROUP_REPO" status --short
git -C "$GROUP_REPO" add \
    ".gitignore" \
    "framework/recruitassist-web/pom.xml" \
    "framework/recruitassist-web/src/main/webapp/WEB-INF/web.xml" \
    "framework/recruitassist-web/src/main/java/com/recruitassist/config" \
    "framework/recruitassist-web/src/main/java/com/recruitassist/web/AppServlet.java" \
    "scripts/mvn17.sh"

git -C "$GROUP_REPO" commit -m "chore: add project bootstrap config and web setup"
git -C "$GROUP_REPO" push -u origin "ZexuanDong/Sprint1"
```

### 3.2 Sprint 2 - JSON 存储与认证基础

- **分支名**：`ZexuanDong/Sprint2`
- **建议 commit message**：`feat: add json repository layer and auth foundation`
- **本次上传文件**：

  - `framework/recruitassist-web/src/main/java/com/recruitassist/repository`
  - `framework/recruitassist-web/src/main/java/com/recruitassist/util/JsonFileStore.java`
  - `framework/recruitassist-web/src/main/java/com/recruitassist/service/AuthService.java`
  - `data/users`
  - `data/system/id-counters.json`

```bash
PACKAGE_DIR="/absolute/path/to/项目_Zexuan_Dong"
GROUP_REPO="/absolute/path/to/Group38-TA_Recruitment"

git -C "$GROUP_REPO" checkout main
git -C "$GROUP_REPO" pull origin main
git -C "$GROUP_REPO" checkout -b "ZexuanDong/Sprint2"

cd "$PACKAGE_DIR"
rsync -avR \
    "framework/recruitassist-web/src/main/java/com/recruitassist/repository" \
    "framework/recruitassist-web/src/main/java/com/recruitassist/util/JsonFileStore.java" \
    "framework/recruitassist-web/src/main/java/com/recruitassist/service/AuthService.java" \
    "data/users" \
    "data/system/id-counters.json" \
    "$GROUP_REPO/"

git -C "$GROUP_REPO" status --short
git -C "$GROUP_REPO" add \
    "framework/recruitassist-web/src/main/java/com/recruitassist/repository" \
    "framework/recruitassist-web/src/main/java/com/recruitassist/util/JsonFileStore.java" \
    "framework/recruitassist-web/src/main/java/com/recruitassist/service/AuthService.java" \
    "data/users" \
    "data/system/id-counters.json"

git -C "$GROUP_REPO" commit -m "feat: add json repository layer and auth foundation"
git -C "$GROUP_REPO" push -u origin "ZexuanDong/Sprint2"
```

### 3.3 Sprint 3 - 核心模型与演示脚本

- **分支名**：`ZexuanDong/Sprint3`
- **建议 commit message**：`chore: add domain models seeds and supporting scripts`
- **本次上传文件**：

  - `framework/recruitassist-web/src/test/java/com/recruitassist/config/AppPathsTest.java`
  - `framework/recruitassist-web/src/main/java/com/recruitassist/model/ActionResult.java`
  - `framework/recruitassist-web/src/main/java/com/recruitassist/model/ApplicationRecord.java`
  - `framework/recruitassist-web/src/main/java/com/recruitassist/model/ApplicationStatus.java`
  - `framework/recruitassist-web/src/main/java/com/recruitassist/model/JobPosting.java`
  - `framework/recruitassist-web/src/main/java/com/recruitassist/model/JobStatus.java`
  - `framework/recruitassist-web/src/main/java/com/recruitassist/model/UserProfile.java`
  - `framework/recruitassist-web/src/main/java/com/recruitassist/model/UserRole.java`
  - `scripts/generate_demo_load.py`

```bash
PACKAGE_DIR="/absolute/path/to/项目_Zexuan_Dong"
GROUP_REPO="/absolute/path/to/Group38-TA_Recruitment"

git -C "$GROUP_REPO" checkout main
git -C "$GROUP_REPO" pull origin main
git -C "$GROUP_REPO" checkout -b "ZexuanDong/Sprint3"

cd "$PACKAGE_DIR"
rsync -avR \
    "framework/recruitassist-web/src/test/java/com/recruitassist/config/AppPathsTest.java" \
    "framework/recruitassist-web/src/main/java/com/recruitassist/model/ActionResult.java" \
    "framework/recruitassist-web/src/main/java/com/recruitassist/model/ApplicationRecord.java" \
    "framework/recruitassist-web/src/main/java/com/recruitassist/model/ApplicationStatus.java" \
    "framework/recruitassist-web/src/main/java/com/recruitassist/model/JobPosting.java" \
    "framework/recruitassist-web/src/main/java/com/recruitassist/model/JobStatus.java" \
    "framework/recruitassist-web/src/main/java/com/recruitassist/model/UserProfile.java" \
    "framework/recruitassist-web/src/main/java/com/recruitassist/model/UserRole.java" \
    "scripts/generate_demo_load.py" \
    "$GROUP_REPO/"

git -C "$GROUP_REPO" status --short
git -C "$GROUP_REPO" add \
    "framework/recruitassist-web/src/test/java/com/recruitassist/config/AppPathsTest.java" \
    "framework/recruitassist-web/src/main/java/com/recruitassist/model/ActionResult.java" \
    "framework/recruitassist-web/src/main/java/com/recruitassist/model/ApplicationRecord.java" \
    "framework/recruitassist-web/src/main/java/com/recruitassist/model/ApplicationStatus.java" \
    "framework/recruitassist-web/src/main/java/com/recruitassist/model/JobPosting.java" \
    "framework/recruitassist-web/src/main/java/com/recruitassist/model/JobStatus.java" \
    "framework/recruitassist-web/src/main/java/com/recruitassist/model/UserProfile.java" \
    "framework/recruitassist-web/src/main/java/com/recruitassist/model/UserRole.java" \
    "scripts/generate_demo_load.py"

git -C "$GROUP_REPO" commit -m "chore: add domain models seeds and supporting scripts"
git -C "$GROUP_REPO" push -u origin "ZexuanDong/Sprint3"
```

## 4. 注意事项

- **不要执行** `git add .`
- **不要执行** 整包 `rsync -av "$PACKAGE_DIR"/ ./` 后再一次性提交
- 每次只同步当前 Sprint 小节列出的文件
- 如果某个目录（如 `data/applications`、`data/jobs`、`data/cv`）已经在某一批提交，就不要在其他批次重复提交同一目录
- 如果前一批还没合入 `main`，下一批开始前先和组内确认是否需要 rebase 或等待合并
