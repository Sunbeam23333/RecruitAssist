## Group38 RecruitAssist 当前六人分包与 Product Backlog 对应总结

### 1. 当前整体情况

- **提交基线**：`homework/recruitassist-web-sprint1/merged_submission_base/` 已生成，包含 `data/`，并保持 `framework/recruitassist-web + data` 的相对路径结构，可作为本次 sprint1 提交基线。
- **六个独立上传包**：`项目_Yi_Qi`、`项目_Tianyu_Zhao`、`项目_Jie_Ren`、`项目_Haopeng_Jin`、`项目_Zhuang_Hou`、`项目_Zexuan_Dong` 已全部生成完毕，每个目录内都包含 `README_上传方法.md` 和 `FILE_LIST.txt`。
- **当前上传策略**：不再一次性整包上传，而是每个人按 `姓名英文连写/Sprint1`、`姓名英文连写/Sprint2`、`姓名英文连写/Sprint3` 分三次、三个分支上传。
- **当前状态说明**：`项目_Haopeng_Jin` 之前的一次性整包上传已经被 revert；现在 6 个包都处于 **本地已就绪、等待按三批 Sprint 方式重新上传** 的状态。
- **一个重要说明**：这次六个包的拆分原则是 **按代码功能边界、文件边界、避免冲突** 来拆，不是机械地按照 `ProductBacklog_group38.xlsx` 里 `Member Assignment` 工作表的原始 assignee 来拆。因此，下面的 backlog 对应关系应理解为：**当前包实际覆盖/支撑了哪些 backlog story**，而不是“Excel 原始分工一字不差复刻”。

### 2. Product Backlog 总览

根据 `ProductBacklog_group38.xlsx`：

- **总 story 数**：24
- **总 story points**：73
- **Sprint 1 焦点**：Login、TA Profile、MO Post Job、Browse Jobs、Apply、CV Upload
- **Sprint 2 焦点**：Applicant Review、Application Status、Admin Overview、JSON Layer、Registration
- **Sprint 3 焦点**：UI、Search/Filter、History、Applicant Count、Privacy、Edit/Close Job
- **Sprint 4 焦点**：AI Matching、Skill Gap、Workload、CSV Export、Notification、Testing
- **当前 Excel 中 story 状态**：工作簿里 24 条 story 的 `Status` 当前都还是 `Planned`，还没有在 backlog 表内被更新为完成状态。

### 3. 六个上传包逐人总结

### 3.1 Yi Qi — `项目_Yi_Qi`

- **当前状态**：包已生成，适合独立发给 Yi Qi；本轮尚未实际试传。
- **包内核心内容**：
  - `README.md`、`README_zh.md`
  - `figure/recommendation-engine.png`
  - `figure/software-overview.png`
  - `figure/system-architecture.png`
  - `index.jsp`、`home.jsp`、`login.jsp`
  - `HomeServlet.java`、`LoginServlet.java`、`LogoutServlet.java`
  - `PERFORMANCE_EVOLUTION.md`
- **主要功能定位**：
  - 首页展示与项目入口
  - 用户登录、登出
  - 登录后进入系统主页的初始体验
  - README/架构图/推荐图等对外展示材料
- **主要对应的 backlog 内容**：
  - **直接对应**：`#1 User Login & Role-Based Access`
  - **较强对应**：`#13 Responsive & Consistent UI`（至少覆盖首页、登录页、项目展示材料层面）
  - **补充支撑**：项目文档、架构图、演示材料不直接是 backlog 独立 story，但对课程提交与展示很重要。
- **一句话总结**：这个包更像是 **“系统入口 + 文档门面 + 演示视觉资产”**，负责让项目看起来完整、能登录、能展示。

### 3.2 Tianyu Zhao — `项目_Tianyu_Zhao`

- **当前状态**：包已生成，适合独立发给 Tianyu Zhao；本轮尚未实际试传。
- **包内核心内容**：
  - `dashboard-ta.jsp`
  - `assets/css/app.css`
  - `assets/js/app.js`
  - `UpdateProfileServlet.java`
  - `UploadCvServlet.java`
  - `DownloadCvServlet.java`
  - `ApplyServlet.java`
  - `WithdrawApplicationServlet.java`
  - `UserService.java`
  - `data/cv`
- **主要功能定位**：
  - TA 个人资料编辑
  - TA Dashboard 页面与前端交互
  - CV 上传与下载
  - 申请岗位、撤回申请
  - 用户服务层与部分前端样式、交互逻辑
- **主要对应的 backlog 内容**：
  - **直接对应**：`#2 TA Profile Creation & Editing`
  - **直接对应**：`#5 TA Apply for Position`
  - **直接对应**：`#6 TA Upload CV`
  - **部分支撑**：`#14 TA Job Search & Filter`（当前包更偏 TA 侧交互与申请，不是完整搜索过滤实现）
- **一句话总结**：这个包是 **“TA 端核心交互包”**，把 TA 用户从建档、上传 CV 到申请岗位的主要动作都串起来了。

### 3.3 Jie Ren — `项目_Jie_Ren`

- **当前状态**：包已生成，适合独立发给 Jie Ren；本轮尚未实际试传。
- **包内核心内容**：
  - `dashboard-mo.jsp`
  - `CreateJobServlet.java`
  - `UpdateJobServlet.java`
  - `ChangeJobStatusServlet.java`
  - `JobService.java`
  - `data/jobs`
- **主要功能定位**：
  - Recruiter / MO Dashboard
  - 岗位发布
  - 岗位修改
  - 岗位关闭、重开、状态切换
  - 岗位数据持久化与服务层逻辑
- **主要对应的 backlog 内容**：
  - **直接对应**：`#3 MO Post TA Position`
  - **直接对应**：`#18 MO Edit / Close Job Posting`
  - **部分支撑**：`#7 MO View Applicant List & CV`（MO dashboard 是查看候选人的基础入口，但申请人列表细节并不完全由本包独立完成）
- **一句话总结**：这个包是 **“MO / Recruiter 侧岗位生命周期管理包”**，核心在于发布、编辑、关闭岗位。

### 3.4 Haopeng Jin — `项目_Haopeng_Jin`

- **当前状态**：本包之前的一次性整包上传已经被 revert；现在应改为按 `HaopengJin/Sprint1`、`HaopengJin/Sprint2`、`HaopengJin/Sprint3` 三个分支分批上传。
- **包内核心内容**：
  - `job-detail.jsp`
  - `JobDetailServlet.java`
  - `UpdateApplicationStatusServlet.java`
  - `ApplicationService.java`
  - `RecommendationService.java`
  - `JobRecommendation.java`
  - `data/applications`
- **主要功能定位**：
  - 岗位详情页
  - 申请记录服务层
  - 更新申请状态
  - 推荐引擎
  - 候选人匹配/排序
  - 申请数据集
- **主要对应的 backlog 内容**：
  - **直接对应**：`#4 Browse Available TA Positions`
  - **直接对应**：`#8 MO Accept / Reject Application`（通过 `UpdateApplicationStatusServlet` 体现）
  - **较强对应**：`#19 AI Skill Matching & Ranking`（通过 `RecommendationService` 与 `JobRecommendation` 体现）
  - **部分支撑**：`#16 Real-Time Applicant Count`（有申请数据与服务层基础，但不是单独统计展示包）
- **一句话总结**：这个包是 **“岗位详情 + 申请处理 + 推荐排序引擎包”**，现在已经改成需要按 3 个 Sprint 小批次分别上传的分包。

### 3.5 Zhuang Hou — `项目_Zhuang_Hou`

- **当前状态**：包已生成，适合独立发给 Zhuang Hou；本轮尚未实际试传。
- **包内核心内容**：
  - `dashboard-admin.jsp`
  - `DashboardServlet.java`
  - `WorkloadService.java`
  - `WorkloadEntry.java`
  - `SystemConfig.java`
  - `data/system/config.json`
- **主要功能定位**：
  - Admin Dashboard
  - 全局 dashboard 路由/分流
  - 工作量统计与治理
  - 系统配置读取
  - 管理端监控视图
- **主要对应的 backlog 内容**：
  - **直接对应**：`#10 Admin Recruitment Overview Dashboard`
  - **直接对应**：`#21 Admin Workload Monitoring & Alerts`
  - **部分支撑**：`#17 Privacy & Role-Based Access Control`（`DashboardServlet` 的入口控制和系统配置能力可为角色访问控制提供支撑）
- **一句话总结**：这个包是 **“Admin 管理监控包”**，负责让系统从“能用”走向“能管理、能治理”。

### 3.6 Zexuan Dong — `项目_Zexuan_Dong`

- **当前状态**：包已生成，适合独立发给 Zexuan Dong；本轮尚未实际试传。
- **包内核心内容**：
  - `.gitignore`
  - `framework/recruitassist-web/pom.xml`
  - `web.xml`
  - `src/main/java/com/recruitassist/config`
  - `src/main/java/com/recruitassist/repository`
  - `JsonFileStore.java`
  - `AppServlet.java`
  - `AuthService.java`
  - 多个核心 model：`ActionResult`、`ApplicationRecord`、`ApplicationStatus`、`JobPosting`、`JobStatus`、`UserProfile`、`UserRole`
  - `data/users`
  - `data/system/id-counters.json`
  - `scripts/generate_demo_load.py`
  - `scripts/mvn17.sh`
  - `AppPathsTest.java`
- **主要功能定位**：
  - 平台配置与路径管理
  - JSON 仓储层 / 持久化层
  - 用户与岗位核心模型
  - 应用启动入口与认证基础设施
  - 种子数据、构建脚本、少量测试
- **主要对应的 backlog 内容**：
  - **直接对应**：`#11 JSON Data Persistence Layer`
  - **部分支撑**：`#12 User Registration & Password Hashing`（有 `AuthService` 和用户数据基础，但完整注册入口并不完全体现在当前包内）
  - **部分支撑**：`#24 Unit & Integration Testing`（目前明确包含 `AppPathsTest.java`，更像测试基础而不是完整测试集）
- **一句话总结**：这个包是 **“系统底座包”**，虽然不一定最显眼，但它决定了整个项目能不能稳定运行、能不能存数据、能不能扩展。

### 4. 当前包与 backlog 的整体覆盖情况

### 4.1 已有较明确覆盖的 backlog story

- `#1 User Login & Role-Based Access`
- `#2 TA Profile Creation & Editing`
- `#3 MO Post TA Position`
- `#4 Browse Available TA Positions`
- `#5 TA Apply for Position`
- `#6 TA Upload CV`
- `#8 MO Accept / Reject Application`
- `#10 Admin Recruitment Overview Dashboard`
- `#11 JSON Data Persistence Layer`
- `#18 MO Edit / Close Job Posting`
- `#19 AI Skill Matching & Ranking`
- `#21 Admin Workload Monitoring & Alerts`

### 4.2 当前包中“部分支撑但不算完整闭环”的 story

- `#7 MO View Applicant List & CV`
- `#12 User Registration & Password Hashing`
- `#13 Responsive & Consistent UI`
- `#14 TA Job Search & Filter`
- `#16 Real-Time Applicant Count`
- `#17 Privacy & Role-Based Access Control`
- `#24 Unit & Integration Testing`

### 4.3 在当前六包中尚未看到非常明确独立落点、后续需要重点核查的 story

- `#9 TA Check Application Status`
- `#15 Admin Historical Recruitment Records`
- `#20 AI Missing Skills Identification`
- `#22 Export Recruitment Data as CSV`
- `#23 In-System Notification for Status Change`

这几项不一定完全没做，而是 **从当前六个上传包的显式文件清单来看，没有形成非常清晰的一包一功能落点**。如果后续你要做飞书周报或汇报，建议把这几项单列为“待核查/待补充说明”。

### 5. 推荐你在飞书里怎么表述当前进度

可以直接用下面这段概括：

> 我们已经把 `recruitassist-web-sprint1` 按代码边界拆成 6 个无冲突上传包，每位成员各自负责一个可独立提交的模块。当前 6 个包均已生成，包含各自的上传说明与文件清单；现在统一改为按 `姓名英文连写/Sprint1`、`姓名英文连写/Sprint2`、`姓名英文连写/Sprint3` 分三批上传，之前的一次性整包上传口径不再使用。从 Product Backlog 对照来看，当前代码已经较明确覆盖登录、TA 建档、岗位发布、岗位浏览、申请、CV 上传、申请审核、Admin 总览、JSON 持久化、岗位编辑/关闭、推荐排序、工作量监控等核心 story；而申请状态查询、历史记录、CSV 导出、通知、缺失技能识别等 story 仍建议继续核查或补充说明。

### 6. 一句话结论

- **如果从“能不能分头上传”来看**：已经可以。
- **如果从“能不能对应 Product Backlog 做汇报”来看**：已经可以，但要明确“这是按代码边界拆包后的映射关系，不是原始 assignee 的逐条复刻”。
- **如果从“哪些功能还需要后续补口径”来看**：重点关注 `#9`、`#15`、`#20`、`#22`、`#23`。
