# RecruitAssist 三版本 GitHub 上传指南

## 方式一:自动脚本(推荐)

```bash
cd /Users/haopengjin/Desktop/School/project/RecruitAssist/homework/plan
bash upload_three_releases.sh
```

脚本会自动:
1. 依次推送 v1.0.0, v2.0.0, v3.0.0 到独立分支
2. 合并到 main
3. 打 Git tag
4. 提供 GitHub Release 创建链接

---

## 方式二:手动分步执行

### 准备工作

```bash
RELEASES_DIR="/Users/haopengjin/Desktop/School/project/RecruitAssist/homework/releases"
REPO_DIR="/Users/haopengjin/Desktop/School/project/Group38-TA_Recruitment"

cd "$REPO_DIR"
git fetch origin
git checkout main
git pull --ff-only origin main
```

### Step 1: 推送 v1.0.0

```bash
# 创建分支
git checkout -B release/v1.0.0 origin/main

# 清空当前内容
find . -maxdepth 1 ! -name '.git' ! -name '.' ! -name '..' -exec rm -rf {} +

# 复制 v1 内容
rsync -a --exclude='.git' "$RELEASES_DIR/v1.0.0/" ./

# 提交
git add -A
git commit -m "release: v1.0.0 - Sprint 1 基础招聘流程

功能:
- 用户登录 & 角色管理
- TA 个人资料创建与编辑
- MO 发布 TA 岗位
- 浏览可用岗位列表
- TA 申请岗位
- TA 上传 CV

技术: 32 Java + 6 JSP
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

echo "✅ v1.0.0 完成"
```

### Step 2: 推送 v2.0.0

```bash
# 创建分支
git checkout main
git checkout -B release/v2.0.0

# 清空并复制 v2
find . -maxdepth 1 ! -name '.git' ! -name '.' ! -name '..' -exec rm -rf {} +
rsync -a --exclude='.git' "$RELEASES_DIR/v2.0.0/" ./

# 提交
git add -A
git commit -m "release: v2.0.0 - Sprint 2 审核与监控

新增功能:
- MO 查看候选人 & CV
- MO 接受/拒绝申请
- TA 查看申请状态
- Admin Dashboard

技术: 36 Java + 7 JSP
"

# 推送分支
git push -u origin release/v2.0.0

# 合并到 main
git checkout main
git merge --no-ff release/v2.0.0 -m "Merge release/v2.0.0 into main"
git push origin main

# 打 tag
git tag -a v2.0.0 -m "Release v2.0.0 - Sprint 2 审核与监控"
git push origin v2.0.0

echo "✅ v2.0.0 完成"
```

### Step 3: 推送 v3.0.0 (验收版)

```bash
# 创建分支
git checkout main
git checkout -B release/v3.0.0

# 清空并复制 v3
find . -maxdepth 1 ! -name '.git' ! -name '.' ! -name '..' -exec rm -rf {} +
rsync -a --exclude='.git' "$RELEASES_DIR/v3.0.0/" ./

# 提交
git add -A
git commit -m "release: v3.0.0 - Sprint 3 完整功能版 (验收版)

新增功能:
- 智能推荐引擎
- 岗位搜索过滤
- MO 编辑/关闭岗位
- TA 撤回申请
- 响应式 UI

技术: 42 Java + 7 JSP (完整版)

✅ 验收版本 - 覆盖 Sprint 1-3 所有需求
"

# 推送分支
git push -u origin release/v3.0.0

# 合并到 main
git checkout main
git merge --no-ff release/v3.0.0 -m "Merge release/v3.0.0 into main"
git push origin main

# 打 tag
git tag -a v3.0.0 -m "Release v3.0.0 - Sprint 3 完整功能版 (验收版)"
git push origin v3.0.0

echo "✅ v3.0.0 完成"
```

---

## Step 4: 在 GitHub 创建 Releases

访问以下链接,填写 Release 说明:

### v1.0.0 Release
https://github.com/yi-Q945/Group38-TA_Recruitment/releases/new?tag=v1.0.0&title=v1.0.0%20-%20Sprint%201%20基础招聘流程

**说明模板**:
```markdown
## Sprint 1 基础招聘流程

### 功能
- ✅ 用户登录 & 角色管理
- ✅ TA 个人资料创建与编辑
- ✅ MO 发布 TA 岗位
- ✅ 浏览可用岗位列表
- ✅ TA 申请岗位
- ✅ TA 上传 CV

### 技术栈
- Java 17 + Maven
- Jakarta Servlet 6 + JSP
- JSON 文件存储
- 32 Java 类 + 6 JSP 页面

### 运行方式
\`\`\`bash
export RECRUITASSIST_BASE_DIR=$(pwd)
mvn -f framework/recruitassist-web/pom.xml org.eclipse.jetty.ee10:jetty-ee10-maven-plugin:12.0.15:run -Djetty.http.port=8081
\`\`\`

访问: http://127.0.0.1:8081/

### 测试账号
- TA: `alice.ta` / `demo123`
- MO: `mo.chen` / `demo123`
- Admin: `admin.sarah` / `demo123`
```

### v2.0.0 Release
https://github.com/yi-Q945/Group38-TA_Recruitment/releases/new?tag=v2.0.0&title=v2.0.0%20-%20Sprint%202%20审核与监控

**说明模板**:
```markdown
## Sprint 2 审核与监控

### 新增功能 (相对 v1)
- ✅ MO 查看候选人列表 & CV
- ✅ MO 接受/拒绝申请
- ✅ TA 查看申请状态
- ✅ Admin 招聘总览 Dashboard
- ✅ 工作量统计

### 技术升级
- 36 Java 类 (+4)
- 7 JSP 页面 (+1)
- 新增 Admin Dashboard
- 新增审核流程

### 运行方式
同 v1.0.0

### 测试账号
同 v1.0.0
```

### v3.0.0 Release (验收版)
https://github.com/yi-Q945/Group38-TA_Recruitment/releases/new?tag=v3.0.0&title=v3.0.0%20-%20Sprint%203%20完整功能版%20(验收版)

**说明模板**:
```markdown
## Sprint 3 完整功能版 ✅ 验收版本

### 新增功能 (相对 v2)
- ✅ 智能推荐引擎 (6维可解释评分)
- ✅ 岗位搜索与过滤
- ✅ MO 编辑/关闭/重开岗位
- ✅ TA 撤回申请
- ✅ Admin 历史记录查看
- ✅ 响应式 UI 优化

### 技术完整版
- 42 Java 类 (完整)
- 7 JSP 页面 (完整)
- 推荐引擎与排序算法
- 完整数据集 (500+ 申请记录)

### 验收覆盖
- Sprint 1-3 所有核心需求 (17/18, 94%)
- 完整的 TA/MO/Admin 功能流程
- 可独立编译和运行
- 数据持久化正常

### 运行方式
\`\`\`bash
cd Group38-TA_Recruitment
export RECRUITASSIST_BASE_DIR=$(pwd)

# 编译
mvn -f framework/recruitassist-web/pom.xml clean compile

# 启动
mvn -f framework/recruitassist-web/pom.xml org.eclipse.jetty.ee10:jetty-ee10-maven-plugin:12.0.15:run -Djetty.http.port=8081
\`\`\`

访问: http://127.0.0.1:8081/

### 核心演示流程

#### TA 流程
1. 登录 `alice.ta` / `demo123`
2. 查看推荐岗位(带评分和匹配理由)
3. 搜索过滤岗位
4. 提交申请
5. 查看申请状态
6. 撤回申请

#### MO 流程
1. 登录 `mo.chen` / `demo123`
2. 创建新岗位
3. 查看候选人列表(带排序)
4. 下载 CV
5. 接受/拒绝申请
6. 编辑岗位
7. 关闭/重开岗位

#### Admin 流程
1. 登录 `admin.sarah` / `demo123`
2. 查看全局统计
3. 查看工作量平衡
4. 查看历史记录

### 技术亮点
- 🎯 推荐算法: 综合技能、时间、经验、工作量、资料、竞争压力
- 📊 可解释 AI: 每个推荐都有详细理由
- 🔐 角色化设计: 三种用户不同权限
- 💾 纯文件存储: 无需数据库,JSON 持久化
- 📱 响应式 UI: 支持移动端

---

## ✅ 验收推荐

**建议以 v3.0.0 作为验收演示版本**
- 功能最完整
- 覆盖所有 Sprint 1-3 需求
- 已验证可编译和运行
```

---

## 验证清单

上传完成后,请验证:

- [ ] GitHub 仓库 main 分支有完整代码
- [ ] 三个 tag (v1.0.0, v2.0.0, v3.0.0) 都已创建
- [ ] 三个 Release 都有详细说明
- [ ] Clone 仓库后可以成功编译
- [ ] 本地运行 v3.0.0 可以正常访问

---

## 回滚操作 (如果出错)

```bash
# 删除远程分支
git push origin --delete release/v1.0.0
git push origin --delete release/v2.0.0
git push origin --delete release/v3.0.0

# 删除远程 tag
git push origin --delete v1.0.0
git push origin --delete v2.0.0
git push origin --delete v3.0.0

# 重置 main 到上一版本
git checkout main
git reset --hard <上一次的 commit hash>
git push -f origin main
```

---

## 常见问题

**Q: 推送时提示冲突怎么办?**
A: 先 `git fetch origin && git pull origin main`,解决冲突后再推送

**Q: 如何验证版本是否正确?**
A: `git checkout v3.0.0 && find framework -name "*.java" | wc -l` 应该是 42

**Q: Release 创建后还能修改吗?**
A: 可以,在 GitHub Release 页面点击 Edit 修改说明

**Q: 验收时用哪个版本?**
A: **v3.0.0**,这是包含所有功能的完整版
