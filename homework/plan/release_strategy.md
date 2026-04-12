# RecruitAssist 三版本渐进式发布策略

## 版本规划总览

基于 Product Backlog Sprint 1-3 需求,将完整项目拆分为三个可独立运行的渐进版本。

---

## v1.0.0 - Sprint 1 基础招聘流程 (2026-03-14)

### 核心功能
- [x] 用户登录 & 角色管理
- [x] TA 个人资料创建与编辑
- [x] MO 发布 TA 岗位
- [x] 浏览可用岗位列表
- [x] TA 申请岗位
- [x] TA 上传 CV

### 技术组件

#### Java 类 (27 个)
**config/**
- AppBootstrapListener.java
- AppContextKeys.java
- AppPaths.java
- AppServices.java
- DemoDataInitializer.java

**model/**
- ActionResult.java
- ApplicationRecord.java
- ApplicationStatus.java
- JobPosting.java
- JobStatus.java
- SystemConfig.java
- UserProfile.java
- UserRole.java

**repository/**
- ApplicationRepository.java
- IdCounterRepository.java
- JobRepository.java
- SystemConfigRepository.java
- UserRepository.java

**service/**
- ApplicationService.java (基础版,只含申请提交)
- AuthService.java
- JobService.java (基础版,不含编辑/关闭)
- UserService.java

**util/**
- JsonFileStore.java

**web/**
- AppServlet.java (路由基础)
- ApplyServlet.java
- CreateJobServlet.java
- DashboardServlet.java (v1 简化版,只显示列表)
- HomeServlet.java
- JobDetailServlet.java (v1 简化版,只显示详情)
- LoginServlet.java
- LogoutServlet.java
- UpdateProfileServlet.java
- UploadCvServlet.java

#### JSP 页面 (6 个)
- index.jsp
- WEB-INF/jsp/home.jsp
- WEB-INF/jsp/login.jsp
- WEB-INF/jsp/dashboard-ta.jsp (v1 简化版)
- WEB-INF/jsp/dashboard-mo.jsp (v1 简化版)
- WEB-INF/jsp/job-detail.jsp (v1 简化版)

#### 数据文件
- data/users/*.json (基础用户)
- data/jobs/*.json (示例岗位 10-20 个)
- data/applications/*.json (示例申请 20-30 个)
- data/cv/*.txt (示例简历)
- data/system/config.json
- data/system/id-counters.json

#### 配置文件
- framework/recruitassist-web/pom.xml
- framework/recruitassist-web/src/main/webapp/WEB-INF/web.xml
- .gitignore
- README.md

### 验收标准
- ✅ 可以成功登录 3 种角色
- ✅ TA 可以浏览岗位并提交申请
- ✅ MO 可以创建新岗位
- ✅ CV 上传功能正常

---

## v2.0.0 - Sprint 2 审核与监控 (2026-03-22)

### 新增功能 (在 v1 基础上)
- [x] MO 查看候选人列表 & CV
- [x] MO 接受/拒绝申请
- [x] TA 查看申请状态
- [x] Admin 招聘总览 Dashboard
- [x] 工作量统计基础

### 新增/修改组件

#### 新增 Java 类 (3 个)
**service/**
- WorkloadService.java (工作量统计)

**web/**
- DownloadCvServlet.java
- UpdateApplicationStatusServlet.java

#### 升级现有类
- DashboardServlet.java (增加 Admin 视图和 MO 候选人列表)
- ApplicationService.java (增加状态更新逻辑)
- JobDetailServlet.java (增加候选人排序显示)

#### 新增 JSP 页面 (1 个)
- WEB-INF/jsp/dashboard-admin.jsp

#### 升级 JSP
- WEB-INF/jsp/dashboard-ta.jsp (显示申请状态)
- WEB-INF/jsp/dashboard-mo.jsp (显示候选人列表)
- WEB-INF/jsp/job-detail.jsp (显示候选人排序)

#### 数据扩展
- data/applications/*.json (增加到 100+ 申请记录)
- data/cv/*.txt (增加到 50+ 简历)

### 验收标准
- ✅ MO 可以审核申请并更新状态
- ✅ TA 可以看到申请被接受/拒绝
- ✅ Admin 可以看到全局招聘统计
- ✅ CV 下载功能正常

---

## v3.0.0 - Sprint 3 完整功能版 (2026-04-11)

### 新增功能 (在 v2 基础上)
- [x] 智能推荐引擎
- [x] 岗位搜索与过滤
- [x] MO 编辑/关闭/重开岗位
- [x] TA 撤回申请
- [x] Admin 历史记录查看
- [x] 响应式 UI 与一致性优化

### 新增/修改组件

#### 新增 Java 类 (4 个)
**model/view/**
- JobRecommendation.java (推荐结果视图)
- WorkloadEntry.java (工作量条目)

**service/**
- RecommendationService.java (推荐引擎)

**web/**
- ChangeJobStatusServlet.java (关闭/重开岗位)
- UpdateJobServlet.java (编辑岗位)
- WithdrawApplicationServlet.java (撤回申请)

#### 最终升级
- DashboardServlet.java (完整版:推荐、搜索、过滤)
- JobService.java (完整版:编辑、状态管理)
- ApplicationService.java (完整版:撤回逻辑)
- dashboard-ta.jsp (完整版:推荐列表 + 搜索)
- dashboard-mo.jsp (完整版:岗位管理)
- dashboard-admin.jsp (完整版:历史记录)
- job-detail.jsp (完整版:所有操作)

#### 数据完整集
- data/users/*.json (100+ 用户)
- data/jobs/*.json (50+ 岗位)
- data/applications/*.json (500+ 申请记录)
- data/cv/*.txt (100+ 简历)
- logs/audit.jsonl (审计日志)

#### 文档完善
- README.md (完整使用说明)
- README_zh.md (中文完整说明)
- figure/*.png (架构图、推荐引擎图)
- PERFORMANCE_EVOLUTION.md (性能优化说明)

### 验收标准 (最终版)
- ✅ 推荐引擎给出合理排序
- ✅ 搜索过滤功能正常
- ✅ 所有角色的完整流程闭环
- ✅ UI 响应式适配
- ✅ 完整的演示数据集
- ✅ 可解释的推荐理由
- ✅ 工作量平衡监控

---

## 版本间依赖关系

```
v1.0.0 (基础可运行)
   ↓
v2.0.0 (增加审核)
   ↓
v3.0.0 (完整功能)
```

每个版本都是 **独立可运行** 的完整系统,后续版本在前一版本基础上累积增强。

---

## 上传策略

1. **v1.0.0**: 推送到 `release/v1.0.0` 分支 → 合并到 main → 打 tag `v1.0.0`
2. **v2.0.0**: 推送到 `release/v2.0.0` 分支 → 合并到 main → 打 tag `v2.0.0`
3. **v3.0.0**: 推送到 `release/v3.0.0` 分支 → 合并到 main → 打 tag `v3.0.0`

每次合并后在 GitHub 创建对应 Release,附上功能说明和运行指南。
