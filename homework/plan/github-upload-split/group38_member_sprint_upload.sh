#!/usr/bin/env bash
set -euo pipefail

usage() {
  cat <<'EOF'
用法：
  bash group38_member_sprint_upload.sh <MemberHandle> <SprintNo|all> <PACKAGE_DIR> <GROUP_REPO>

示例：
  bash group38_member_sprint_upload.sh YiQi 1 "/absolute/path/to/项目_Yi_Qi" "/absolute/path/to/Group38-TA_Recruitment"
  bash group38_member_sprint_upload.sh TianyuZhao all "/absolute/path/to/项目_Tianyu_Zhao" "/absolute/path/to/Group38-TA_Recruitment"

支持的 MemberHandle：
  YiQi
  TianyuZhao
  JieRen
  HaopengJin
  ZhuangHou
  ZexuanDong

说明：
- 本脚本会从最新的 origin/main 创建或重置对应 Sprint 分支。
- 每个 Sprint 只会同步该批次 README 中列出的文件，不会执行 git add .
- 如果远程已存在同名分支，脚本会直接退出，避免误覆盖历史提交。
EOF
}

if [[ $# -ne 4 ]]; then
  usage
  exit 1
fi

MEMBER_HANDLE="$1"
SPRINT_INPUT="$2"
PACKAGE_DIR="$3"
GROUP_REPO="$4"

if [[ ! -d "$PACKAGE_DIR" ]]; then
  echo "[错误] PACKAGE_DIR 不存在：$PACKAGE_DIR" >&2
  exit 1
fi

if [[ ! -d "$GROUP_REPO/.git" ]]; then
  echo "[错误] GROUP_REPO 不是一个 git 仓库：$GROUP_REPO" >&2
  exit 1
fi

if [[ "$SPRINT_INPUT" != "all" && ! "$SPRINT_INPUT" =~ ^[123]$ ]]; then
  echo "[错误] SprintNo 只能是 1 / 2 / 3 / all" >&2
  exit 1
fi

ensure_clean_repo() {
  local dirty
  dirty="$(git -C "$GROUP_REPO" status --porcelain | grep -vE '^[?][?] \.DS_Store$' || true)"
  if [[ -n "$dirty" ]]; then
    echo "[错误] 组仓库当前有未提交改动，请先处理后再执行：" >&2
    echo "$dirty" >&2
    exit 1
  fi
}

set_config() {
  local key="$1:$2"
  case "$key" in
    "YiQi:1")
      BRANCH="YiQi/Sprint1"
      COMMIT_MSG="feat: add landing page and login flow"
      PATHS=(
        "framework/recruitassist-web/src/main/webapp/index.jsp"
        "framework/recruitassist-web/src/main/webapp/WEB-INF/jsp/login.jsp"
        "framework/recruitassist-web/src/main/java/com/recruitassist/web/LoginServlet.java"
        "framework/recruitassist-web/src/main/java/com/recruitassist/web/LogoutServlet.java"
      )
      ;;
    "YiQi:2")
      BRANCH="YiQi/Sprint2"
      COMMIT_MSG="docs: add home page flow and bilingual readme"
      PATHS=(
        "README.md"
        "README_zh.md"
        "framework/recruitassist-web/src/main/webapp/WEB-INF/jsp/home.jsp"
        "framework/recruitassist-web/src/main/java/com/recruitassist/web/HomeServlet.java"
      )
      ;;
    "YiQi:3")
      BRANCH="YiQi/Sprint3"
      COMMIT_MSG="docs: add architecture figures and performance notes"
      PATHS=(
        "figure/recommendation-engine.png"
        "figure/software-overview.png"
        "figure/system-architecture.png"
        "PERFORMANCE_EVOLUTION.md"
      )
      ;;
    "TianyuZhao:1")
      BRANCH="TianyuZhao/Sprint1"
      COMMIT_MSG="feat: add TA dashboard and profile editing"
      PATHS=(
        "framework/recruitassist-web/src/main/webapp/WEB-INF/jsp/dashboard-ta.jsp"
        "framework/recruitassist-web/src/main/java/com/recruitassist/web/UpdateProfileServlet.java"
        "framework/recruitassist-web/src/main/java/com/recruitassist/service/UserService.java"
      )
      ;;
    "TianyuZhao:2")
      BRANCH="TianyuZhao/Sprint2"
      COMMIT_MSG="feat: add CV upload and download flow"
      PATHS=(
        "framework/recruitassist-web/src/main/java/com/recruitassist/web/UploadCvServlet.java"
        "framework/recruitassist-web/src/main/java/com/recruitassist/web/DownloadCvServlet.java"
        "data/cv"
      )
      ;;
    "TianyuZhao:3")
      BRANCH="TianyuZhao/Sprint3"
      COMMIT_MSG="feat: add apply withdraw flow and frontend interactions"
      PATHS=(
        "framework/recruitassist-web/src/main/webapp/assets/css/app.css"
        "framework/recruitassist-web/src/main/webapp/assets/js/app.js"
        "framework/recruitassist-web/src/main/java/com/recruitassist/web/ApplyServlet.java"
        "framework/recruitassist-web/src/main/java/com/recruitassist/web/WithdrawApplicationServlet.java"
      )
      ;;
    "JieRen:1")
      BRANCH="JieRen/Sprint1"
      COMMIT_MSG="feat: add recruiter dashboard and job creation entry"
      PATHS=(
        "framework/recruitassist-web/src/main/webapp/WEB-INF/jsp/dashboard-mo.jsp"
        "framework/recruitassist-web/src/main/java/com/recruitassist/web/CreateJobServlet.java"
      )
      ;;
    "JieRen:2")
      BRANCH="JieRen/Sprint2"
      COMMIT_MSG="feat: add job update flow and service support"
      PATHS=(
        "framework/recruitassist-web/src/main/java/com/recruitassist/web/UpdateJobServlet.java"
        "framework/recruitassist-web/src/main/java/com/recruitassist/service/JobService.java"
      )
      ;;
    "JieRen:3")
      BRANCH="JieRen/Sprint3"
      COMMIT_MSG="feat: add job status management and seed jobs data"
      PATHS=(
        "framework/recruitassist-web/src/main/java/com/recruitassist/web/ChangeJobStatusServlet.java"
        "data/jobs"
      )
      ;;
    "HaopengJin:1")
      BRANCH="HaopengJin/Sprint1"
      COMMIT_MSG="feat: add job detail page and servlet"
      PATHS=(
        "framework/recruitassist-web/src/main/webapp/WEB-INF/jsp/job-detail.jsp"
        "framework/recruitassist-web/src/main/java/com/recruitassist/web/JobDetailServlet.java"
      )
      ;;
    "HaopengJin:2")
      BRANCH="HaopengJin/Sprint2"
      COMMIT_MSG="feat: add application review flow and application dataset"
      PATHS=(
        "framework/recruitassist-web/src/main/java/com/recruitassist/web/UpdateApplicationStatusServlet.java"
        "framework/recruitassist-web/src/main/java/com/recruitassist/service/ApplicationService.java"
        "data/applications"
      )
      ;;
    "HaopengJin:3")
      BRANCH="HaopengJin/Sprint3"
      COMMIT_MSG="feat: add recommendation service and ranking model"
      PATHS=(
        "framework/recruitassist-web/src/main/java/com/recruitassist/service/RecommendationService.java"
        "framework/recruitassist-web/src/main/java/com/recruitassist/model/view/JobRecommendation.java"
      )
      ;;
    "ZhuangHou:1")
      BRANCH="ZhuangHou/Sprint1"
      COMMIT_MSG="feat: add admin dashboard entry"
      PATHS=(
        "framework/recruitassist-web/src/main/webapp/WEB-INF/jsp/dashboard-admin.jsp"
        "framework/recruitassist-web/src/main/java/com/recruitassist/web/DashboardServlet.java"
      )
      ;;
    "ZhuangHou:2")
      BRANCH="ZhuangHou/Sprint2"
      COMMIT_MSG="feat: add workload service and view model"
      PATHS=(
        "framework/recruitassist-web/src/main/java/com/recruitassist/service/WorkloadService.java"
        "framework/recruitassist-web/src/main/java/com/recruitassist/model/view/WorkloadEntry.java"
      )
      ;;
    "ZhuangHou:3")
      BRANCH="ZhuangHou/Sprint3"
      COMMIT_MSG="chore: add admin config for workload governance"
      PATHS=(
        "framework/recruitassist-web/src/main/java/com/recruitassist/model/SystemConfig.java"
        "data/system/config.json"
      )
      ;;
    "ZexuanDong:1")
      BRANCH="ZexuanDong/Sprint1"
      COMMIT_MSG="chore: add project bootstrap config and web setup"
      PATHS=(
        ".gitignore"
        "framework/recruitassist-web/pom.xml"
        "framework/recruitassist-web/src/main/webapp/WEB-INF/web.xml"
        "framework/recruitassist-web/src/main/java/com/recruitassist/config"
        "framework/recruitassist-web/src/main/java/com/recruitassist/web/AppServlet.java"
        "scripts/mvn17.sh"
      )
      ;;
    "ZexuanDong:2")
      BRANCH="ZexuanDong/Sprint2"
      COMMIT_MSG="feat: add json repository layer and auth foundation"
      PATHS=(
        "framework/recruitassist-web/src/main/java/com/recruitassist/repository"
        "framework/recruitassist-web/src/main/java/com/recruitassist/util/JsonFileStore.java"
        "framework/recruitassist-web/src/main/java/com/recruitassist/service/AuthService.java"
        "data/users"
        "data/system/id-counters.json"
      )
      ;;
    "ZexuanDong:3")
      BRANCH="ZexuanDong/Sprint3"
      COMMIT_MSG="chore: add domain models seeds and supporting scripts"
      PATHS=(
        "framework/recruitassist-web/src/test/java/com/recruitassist/config/AppPathsTest.java"
        "framework/recruitassist-web/src/main/java/com/recruitassist/model/ActionResult.java"
        "framework/recruitassist-web/src/main/java/com/recruitassist/model/ApplicationRecord.java"
        "framework/recruitassist-web/src/main/java/com/recruitassist/model/ApplicationStatus.java"
        "framework/recruitassist-web/src/main/java/com/recruitassist/model/JobPosting.java"
        "framework/recruitassist-web/src/main/java/com/recruitassist/model/JobStatus.java"
        "framework/recruitassist-web/src/main/java/com/recruitassist/model/UserProfile.java"
        "framework/recruitassist-web/src/main/java/com/recruitassist/model/UserRole.java"
        "scripts/generate_demo_load.py"
      )
      ;;
    *)
      echo "[错误] 不支持的组合：$key" >&2
      exit 1
      ;;
  esac
}

run_one_sprint() {
  local sprint_no="$1"
  set_config "$MEMBER_HANDLE" "$sprint_no"

  echo "============================================================"
  echo "开始处理：$MEMBER_HANDLE Sprint$sprint_no -> $BRANCH"
  echo "============================================================"

  if git -C "$GROUP_REPO" ls-remote --exit-code --heads origin "$BRANCH" >/dev/null 2>&1; then
    echo "[错误] 远程分支已存在：$BRANCH" >&2
    echo "请先确认该分支是否需要保留；如果之前已 revert 但分支还在，请先手动删除远程分支后再重跑。" >&2
    exit 1
  fi

  ensure_clean_repo
  git -C "$GROUP_REPO" fetch origin
  git -C "$GROUP_REPO" checkout main
  git -C "$GROUP_REPO" pull --ff-only origin main
  git -C "$GROUP_REPO" checkout -B "$BRANCH" origin/main

  (
    cd "$PACKAGE_DIR"
    rsync -avR "${PATHS[@]}" "$GROUP_REPO/"
  )

  git -C "$GROUP_REPO" status --short
  git -C "$GROUP_REPO" add -- "${PATHS[@]}"
  git -C "$GROUP_REPO" commit -m "$COMMIT_MSG"
  git -C "$GROUP_REPO" push -u origin "$BRANCH"

  echo
  echo "[完成] 已推送：$BRANCH"
  echo "[PR] https://github.com/yi-Q945/Group38-TA_Recruitment/pull/new/${BRANCH}"
  echo
}

case "$SPRINT_INPUT" in
  all)
    run_one_sprint 1
    run_one_sprint 2
    run_one_sprint 3
    ;;
  *)
    run_one_sprint "$SPRINT_INPUT"
    ;;
esac
