# RecruitAssist 三版本验收清单

基于 `ProductBacklog_group38.xlsx` Sprint 1-3 需求验证

---

## v1.0.0 - Sprint 1 验收清单

### Product Backlog 需求覆盖

| Story ID | Story Name | Assignee | 状态 | 实现组件 |
|----------|-----------|----------|------|---------|
| 1 | User Login & Role-Based Access | Yi Qi | ✅ | LoginServlet, AuthService, index.jsp, login.jsp |
| 2 | TA Profile Creation & Editing | Tianyu Zhao | ✅ | UpdateProfileServlet, UserService, dashboard-ta.jsp |
| 3 | MO Post TA Position | Jie Ren | ✅ | CreateJobServlet, JobService, dashboard-mo.jsp |
| 4 | Browse Available TA Positions | Haopeng Jin | ✅ | JobDetailServlet, DashboardServlet, job-detail.jsp |
| 5 | TA Apply for Position | Zhuang Hou | ✅ | ApplyServlet, ApplicationService |
| 6 | TA Upload CV | Zexuan Dong | ✅ | UploadCvServlet, UserService, data/cv/ |

**Sprint 1 覆盖率: 6/6 (100%)**

### 技术实现验证

- [x] **登录系统**: 支持 TA/MO/Admin 三角色
- [x] **Session 管理**: 登录后持久化用户状态
- [x] **角色路由**: 登录后自动跳转到对应 Dashboard
- [x] **个人资料**: TA 可编辑技能、经验、时间偏好
- [x] **岗位发布**: MO 可创建岗位(模块、技能、时间)
- [x] **岗位浏览**: 显示所有开放岗位列表
- [x] **申请提交**: TA 点击 Apply 创建申请记录
- [x] **CV 上传**: 文件上传到 `data/cv/`
- [x] **数据持久化**: JSON 文件存储(users/jobs/applications)

### 文件统计
- Java 类: 32 个
- JSP 页面: 6 个
- 数据文件: users(30), jobs(20), applications(50), cv(18)

### 运行验证
```bash
cd /Users/haopengjin/Desktop/School/project/RecruitAssist/homework/releases/v1.0.0
export RECRUITASSIST_BASE_DIR=$(pwd)
mvn -f framework/recruitassist-web/pom.xml org.eclipse.jetty.ee10:jetty-ee10-maven-plugin:12.0.15:run -Djetty.http.port=8081
```

**预期结果**:
- http://127.0.0.1:8081/ 显示登录页
- 登录 `alice.ta` / `demo123` 进入 TA Dashboard
- 登录 `mo.chen` / `demo123` 进入 MO Dashboard
- TA 可以看到岗位列表并申请
- MO 可以创建新岗位

---

## v2.0.0 - Sprint 2 验收清单

### Product Backlog 需求覆盖 (累积)

**Sprint 1 需求 (继承)**: 6/6 ✅

**Sprint 2 新增需求**:

| Story ID | Story Name | Assignee | 状态 | 实现组件 |
|----------|-----------|----------|------|---------|
| 7 | MO View Applicant List & CV | Yi Qi | ✅ | DashboardServlet, DownloadCvServlet, dashboard-mo.jsp |
| 8 | MO Accept / Reject Application | Tianyu Zhao | ✅ | UpdateApplicationStatusServlet, ApplicationService |
| 9 | TA Check Application Status | Jie Ren | ✅ | DashboardServlet, dashboard-ta.jsp (状态显示) |
| 10 | Admin Recruitment Overview Dashboard | Haopeng Jin | ✅ | DashboardServlet (Admin 视图), dashboard-admin.jsp |
| 11 | JSON Data Persistence Layer | Zhuang Hou | ✅ | 所有 Repository 类, JsonFileStore |
| 12 | User Registration & Password Hashing | Zexuan Dong | ⚠️ | 预留 AuthService (演示版用明文密码) |

**Sprint 1+2 覆盖率: 11/12 (92%)** (注:密码哈希在演示版中简化)

### 新增技术实现

- [x] **候选人列表**: MO Dashboard 显示每个岗位的申请者
- [x] **CV 下载**: 点击下载按钮获取 CV 文件
- [x] **状态更新**: MO 可以接受/拒绝申请
- [x] **申请状态查看**: TA Dashboard 显示 Pending/Accepted/Rejected
- [x] **Admin 总览**: 统计所有岗位、申请数、接受率
- [x] **工作量统计**: WorkloadService 计算 TA 已接受岗位数

### 新增文件
- Java: +4 (WorkloadService, DownloadCvServlet, UpdateApplicationStatusServlet, WorkloadEntry)
- JSP: +1 (dashboard-admin.jsp)
- 数据: users(60), jobs(35), applications(150)

### 运行验证
```bash
# 运行方式同 v1
```

**预期新增功能**:
- MO Dashboard 显示候选人列表,带 Accept/Reject 按钮
- 点击 CV 链接可以下载
- TA Dashboard 显示申请状态(绿色 Accepted / 红色 Rejected)
- 登录 `admin.sarah` / `demo123` 进入 Admin Dashboard
- Admin 可以看到全局统计

---

## v3.0.0 - Sprint 3 验收清单 (最终验收版)

### Product Backlog 需求覆盖 (完整)

**Sprint 1+2 需求 (继承)**: 11/12 ✅

**Sprint 3 新增需求**:

| Story ID | Story Name | Assignee | 状态 | 实现组件 |
|----------|-----------|----------|------|---------|
| 13 | Responsive & Consistent UI | Yi Qi | ✅ | 所有 JSP 使用统一样式,Bootstrap 响应式 |
| 14 | TA Job Search & Filter | Tianyu Zhao | ✅ | DashboardServlet (搜索参数), dashboard-ta.jsp |
| 15 | Admin Historical Recruitment Records | Jie Ren | ✅ | DashboardServlet (历史视图), dashboard-admin.jsp |
| 16 | Real-Time Applicant Count | Haopeng Jin | ✅ | ApplicationService.countByJobId(), 岗位详情页实时统计 |
| 17 | Privacy & Role-Based Access Control | Zhuang Hou | ✅ | AppServlet (角色检查), AuthService |
| 18 | MO Edit / Close Job Posting | Zexuan Dong | ✅ | UpdateJobServlet, ChangeJobStatusServlet, dashboard-mo.jsp |

**Sprint 1+2+3 覆盖率: 17/18 (94%)** (完整功能版)

### 新增技术实现 (相对 v2)

- [x] **推荐引擎**: RecommendationService 综合评分排序
- [x] **可解释推荐**: JobRecommendation 提供详细匹配理由
- [x] **岗位搜索**: 按模块/技能/时间过滤
- [x] **岗位编辑**: MO 可以修改岗位信息
- [x] **岗位关闭/重开**: ChangeJobStatusServlet 切换状态
- [x] **撤回申请**: TA 可以撤回 Pending 申请
- [x] **历史记录**: Admin Dashboard 显示已关闭岗位
- [x] **实时计数**: 岗位详情页显示当前申请人数
- [x] **响应式 UI**: 所有页面支持移动端

### 最终技术栈
- Java 类: 42 个 (完整)
- JSP 页面: 7 个 (完整)
- 数据文件: users(83), jobs(50), applications(500), cv(18)
- 推荐算法: 6维评分 (技能匹配、时间匹配、经验、工作量、资料完整度、竞争压力)

### 核心功能演示流程

#### 1. TA 完整流程
```
登录 alice.ta → 查看推荐岗位(带评分) → 搜索过滤 → 查看岗位详情 
→ 提交申请 → 查看申请状态 → (可选)撤回申请
```

#### 2. MO 完整流程
```
登录 mo.chen → 查看我的岗位 → 创建新岗位 → 查看候选人列表 
→ 下载CV → 审核(接受/拒绝) → 编辑岗位 → 关闭/重开岗位
```

#### 3. Admin 完整流程
```
登录 admin.sarah → 查看全局统计 → 查看工作量平衡 
→ 查看历史记录 → 监控近期申请动态
```

### 运行验证
```bash
cd /Users/haopengjin/Desktop/School/project/RecruitAssist/homework/releases/v3.0.0
export RECRUITASSIST_BASE_DIR=$(pwd)
mvn -f framework/recruitassist-web/pom.xml clean compile
mvn -f framework/recruitassist-web/pom.xml org.eclipse.jetty.ee10:jetty-ee10-maven-plugin:12.0.15:run -Djetty.http.port=8081
```

访问: http://127.0.0.1:8081/

### 验收通过标准

- [x] **编译成功**: `mvn compile` 无错误
- [x] **启动成功**: Jetty 在 8081 端口正常运行
- [x] **登录正常**: 三种角色都能登录并进入对应 Dashboard
- [x] **TA 推荐**: 岗位列表有推荐评分和匹配理由
- [x] **搜索过滤**: 搜索框可以按关键词过滤岗位
- [x] **申请流程**: TA 可以申请、查看状态、撤回
- [x] **审核流程**: MO 可以查看候选人、下载CV、接受/拒绝
- [x] **岗位管理**: MO 可以编辑、关闭、重开岗位
- [x] **Admin 监控**: Admin 可以看到全局统计和历史记录
- [x] **数据持久化**: 操作后刷新页面,数据仍然存在

---

## 版本对比总结

| 项目 | v1.0.0 | v2.0.0 | v3.0.0 |
|------|--------|--------|--------|
| Sprint 覆盖 | Sprint 1 | Sprint 1+2 | Sprint 1+2+3 |
| Java 类 | 32 | 36 | 42 |
| JSP 页面 | 6 | 7 | 7 |
| 功能需求 | 6/6 | 11/12 | 17/18 |
| 用户数据 | 30 | 60 | 83 |
| 岗位数据 | 20 | 35 | 50 |
| 申请记录 | 50 | 150 | 500 |
| 推荐引擎 | ❌ | ❌ | ✅ |
| 审核流程 | ❌ | ✅ | ✅ |
| 岗位管理 | 部分 | 部分 | ✅ |
| 验收适用 | 演示基础流程 | 演示审核功能 | **✅ 完整验收版** |

---

## 建议验收重点

### 核心展示 (基于 v3.0.0)

1. **登录与角色分流** (Story 1)
   - 演示三种角色登录后进入不同页面

2. **TA 推荐与申请** (Story 4, 5, 14, 16)
   - 展示推荐评分和匹配理由
   - 演示搜索过滤功能
   - 提交申请并查看实时申请人数

3. **MO 审核流程** (Story 7, 8, 18)
   - 查看候选人列表和排序
   - 下载 CV
   - 接受/拒绝申请
   - 编辑/关闭岗位

4. **Admin 监控** (Story 10, 15)
   - 全局统计面板
   - 工作量平衡查看
   - 历史记录

### 技术亮点

- **推荐算法**: 6维可解释评分
- **纯文件存储**: 无需数据库,JSON 持久化
- **角色化设计**: 三种用户不同视图和权限
- **响应式 UI**: 支持移动端访问

---

## 上传计划

**GitHub 仓库**: https://github.com/yi-Q945/Group38-TA_Recruitment

1. **v1.0.0**: 
   - 分支: `release/v1.0.0`
   - Tag: `v1.0.0`
   - Release: "Sprint 1 基础招聘流程"

2. **v2.0.0**:
   - 分支: `release/v2.0.0`
   - Tag: `v2.0.0`
   - Release: "Sprint 2 审核与监控"

3. **v3.0.0**:
   - 分支: `release/v3.0.0`
   - Tag: `v3.0.0`
   - Release: "**Sprint 3 完整功能版 (验收版)**"

**最终 main 分支**: 指向 v3.0.0

---

## 验收结论

✅ **v3.0.0 满足验收要求**:
- 覆盖 Sprint 1-3 的 17/18 核心需求 (94%)
- 包含完整的推荐引擎和审核流程
- 可独立编译和运行
- 数据持久化正常
- 三种角色功能完整

**建议以 v3.0.0 作为验收演示版本**
