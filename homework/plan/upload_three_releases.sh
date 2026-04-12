#!/bin/bash
set -euo pipefail

# 配置
RELEASES_DIR="/Users/haopengjin/Desktop/School/project/RecruitAssist/homework/releases"
REPO_DIR="/Users/haopengjin/Desktop/School/project/Group38-TA_Recruitment"

echo "========================================"
echo "RecruitAssist 三版本上传脚本"
echo "========================================"

cd "$REPO_DIR"

# 确保在最新 main
git fetch origin
git checkout main
git pull --ff-only origin main

echo ""
echo "========================================"
echo "推送 v1.0.0 - Sprint 1 基础版"
echo "========================================"

# 创建 v1 分支
git checkout -B release/v1.0.0 origin/main

# 清空当前内容(保留 .git)
find . -maxdepth 1 ! -name '.git' ! -name '.' ! -name '..' -exec rm -rf {} +

# 复制 v1.0.0 内容
rsync -a --exclude='.git' "$RELEASES_DIR/v1.0.0/" ./

# 提交
git add -A
git status --short
git commit -m "release: v1.0.0 - Sprint 1 基础招聘流程

功能:
- 用户登录 & 角色管理
- TA 个人资料创建与编辑
- MO 发布 TA 岗位
- 浏览可用岗位列表
- TA 申请岗位
- TA 上传 CV

技术:
- 32 Java 类
- 6 JSP 页面
- JSON 文件存储
- Maven + Jetty

运行:
RECRUITASSIST_BASE_DIR=\$(pwd) mvn -f framework/recruitassist-web/pom.xml org.eclipse.jetty.ee10:jetty-ee10-maven-plugin:12.0.15:run -Djetty.http.port=8081
"

# 推送分支
git push -u origin release/v1.0.0

# 合并到 main
git checkout main
git merge --no-ff release/v1.0.0 -m "Merge release/v1.0.0 into main"
git push origin main

# 打 tag
git tag -a v1.0.0 -m "Release v1.0.0 - Sprint 1 基础招聘流程"
git push origin v1.0.0

echo "✅ v1.0.0 已推送并打 tag"

echo ""
echo "========================================"
echo "推送 v2.0.0 - Sprint 2 审核版"
echo "========================================"

# 创建 v2 分支(从最新 main)
git checkout main
git checkout -B release/v2.0.0

# 清空并复制 v2
find . -maxdepth 1 ! -name '.git' ! -name '.' ! -name '..' -exec rm -rf {} +
rsync -a --exclude='.git' "$RELEASES_DIR/v2.0.0/" ./

git add -A
git status --short
git commit -m "release: v2.0.0 - Sprint 2 审核与监控

新增功能(相对 v1):
- MO 查看候选人列表 & CV
- MO 接受/拒绝申请
- TA 查看申请状态
- Admin 招聘总览 Dashboard
- 工作量统计基础

技术升级:
- 36 Java 类 (+4)
- 7 JSP 页面 (+1)
- 新增 Admin Dashboard
- 新增审核流程

运行同 v1.0.0
"

git push -u origin release/v2.0.0

# 合并到 main
git checkout main
git merge --no-ff release/v2.0.0 -m "Merge release/v2.0.0 into main"
git push origin main

# 打 tag
git tag -a v2.0.0 -m "Release v2.0.0 - Sprint 2 审核与监控"
git push origin v2.0.0

echo "✅ v2.0.0 已推送并打 tag"

echo ""
echo "========================================"
echo "推送 v3.0.0 - Sprint 3 完整版"
echo "========================================"

# 创建 v3 分支
git checkout main
git checkout -B release/v3.0.0

# 清空并复制 v3
find . -maxdepth 1 ! -name '.git' ! -name '.' ! -name '..' -exec rm -rf {} +
rsync -a --exclude='.git' "$RELEASES_DIR/v3.0.0/" ./

git add -A
git status --short
git commit -m "release: v3.0.0 - Sprint 3 完整功能版 (验收版)

新增功能(相对 v2):
- 智能推荐引擎
- 岗位搜索与过滤
- MO 编辑/关闭/重开岗位
- TA 撤回申请
- Admin 历史记录查看
- 响应式 UI 优化

技术完整版:
- 42 Java 类 (完整)
- 7 JSP 页面 (完整)
- 推荐引擎与排序算法
- 完整数据集 (500+ 申请记录)

验收版本 - 覆盖 Sprint 1-3 所有需求
"

git push -u origin release/v3.0.0

# 合并到 main
git checkout main
git merge --no-ff release/v3.0.0 -m "Merge release/v3.0.0 into main"
git push origin main

# 打 tag
git tag -a v3.0.0 -m "Release v3.0.0 - Sprint 3 完整功能版 (验收版)"
git push origin v3.0.0

echo "✅ v3.0.0 已推送并打 tag"

echo ""
echo "========================================"
echo "✅ 所有版本上传完成!"
echo "========================================"
echo "GitHub Releases 创建链接:"
echo "  v1.0.0: https://github.com/yi-Q945/Group38-TA_Recruitment/releases/new?tag=v1.0.0"
echo "  v2.0.0: https://github.com/yi-Q945/Group38-TA_Recruitment/releases/new?tag=v2.0.0"
echo "  v3.0.0: https://github.com/yi-Q945/Group38-TA_Recruitment/releases/new?tag=v3.0.0"
echo ""
echo "当前 main 分支已更新到 v3.0.0"
echo "验收用 v3.0.0: 包含完整功能,可独立运行"
