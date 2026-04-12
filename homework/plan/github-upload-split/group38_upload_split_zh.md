# Group38 项目分工与 GitHub 上传拆分方案（中文）

## 1. 说明

这份文档基于当前 `RecruitAssist` 最新项目快照整理，组员名单来自 `homework/Group38-TA_Recruitment/README.md`。

本方案的目标是：

- 让 **6 位成员都能各自上传一部分内容**；
- 尽量按照 **角色 / 功能边界** 拆分，减少冲突；
- **不去改动当前已经存在的 `framework/` 代码**，这里只做提交归属和上传责任划分；
- 文档放在 `homework/plan/github-upload-split/`，避免影响后续正式上传仓库。

> 这不是重新开发计划，而是 **GitHub 提交 / 上传责任拆分方案**。

---

## 2. 小组成员

| 序号 | 姓名 | GitHub 用户名 | 建议负责模块 |
| --- | --- | --- | --- |
| 1 | Yi Qi | `yi-Q945` | 首页 / 登录 / README / 展示素材 |
| 2 | Tianyu Zhao | `DayDreemurr0615` | TA 流程与前端交互 |
| 3 | Jie Ren | `JieJieSAM` | Recruiter(MO) 岗位管理 |
| 4 | Haopeng Jin | `Sunbeam23333` | 推荐引擎 / 岗位详情 / 候选人排序 |
| 5 | Zhuang Hou | `qiye-cv` | Admin 监控 / 工作量治理 |
| 6 | Zexuan Dong | `GuMiShDo666` | 平台底层 / 数据存储 / 启动与种子数据 |

---

## 3. 拆分原则

### 3.1 总体原则

- 每个人都负责 **一整块相对独立的文件集合**；
- 尽量避免两个人同时负责同一个共享文件；
- 如果某个文件是 **公共底层文件**，必须指定唯一 owner；
- 正式上传时，优先按本文件规定的 owner 来 `git add` / `git commit`。

### 3.2 共享文件归属原则

以下文件容易冲突，必须按 owner 处理：

- `DashboardServlet.java` → **Zhuang Hou**
- `ApplicationService.java` → **Haopeng Jin**
- `app.css` / `app.js` → **Tianyu Zhao**
- `README.md` / `README_zh.md` / `figure/` → **Yi Qi**
- `data/system/config.json` → **Zhuang Hou**
- `data/system/id-counters.json` / `scripts/generate_demo_load.py` → **Zexuan Dong**

---

## 4. 六人拆分方案

## 4.1 Yi Qi — 首页、登录、README、展示素材

### 负责范围

- 项目首页与登录入口
- 对外展示说明文档
- README 配图与展示素材

### 建议上传文件

- `README.md`
- `README_zh.md`
- `figure/recommendation-engine.png`
- `figure/software-overview.png`
- `figure/system-architecture.png`
- `framework/recruitassist-web/src/main/webapp/WEB-INF/jsp/home.jsp`
- `framework/recruitassist-web/src/main/webapp/WEB-INF/jsp/login.jsp`
- `framework/recruitassist-web/src/main/java/com/recruitassist/web/HomeServlet.java`
- `framework/recruitassist-web/src/main/java/com/recruitassist/web/LoginServlet.java`
- `framework/recruitassist-web/src/main/java/com/recruitassist/web/LogoutServlet.java`

### 对应功能

- 首页介绍
- Quick demo sign-in / 快捷登录展示
- 登录与退出登录
- 项目说明、运行说明、展示图片

### 建议 commit 主题

- `docs: polish project readme and landing/login experience`

---

## 4.2 Tianyu Zhao — TA 流程与前端交互

### 负责范围

- TA dashboard
- TA 个人信息维护
- CV 上传 / 下载
- TA 侧前端交互与样式

### 建议上传文件

- `framework/recruitassist-web/src/main/webapp/WEB-INF/jsp/dashboard-ta.jsp`
- `framework/recruitassist-web/src/main/webapp/assets/css/app.css`
- `framework/recruitassist-web/src/main/webapp/assets/js/app.js`
- `framework/recruitassist-web/src/main/java/com/recruitassist/web/UpdateProfileServlet.java`
- `framework/recruitassist-web/src/main/java/com/recruitassist/web/UploadCvServlet.java`
- `framework/recruitassist-web/src/main/java/com/recruitassist/web/DownloadCvServlet.java`
- `framework/recruitassist-web/src/main/java/com/recruitassist/web/ApplyServlet.java`
- `framework/recruitassist-web/src/main/java/com/recruitassist/web/WithdrawApplicationServlet.java`
- `framework/recruitassist-web/src/main/java/com/recruitassist/service/UserService.java`

### 对应功能

- TA 个人资料更新
- CV/PDF 上传和下载
- TA 页面交互、按钮、上传控件、前端体验
- TA 发起申请 / 撤回申请的入口层

### 建议 commit 主题

- `feat: improve TA dashboard profile and CV interactions`

---

## 4.3 Jie Ren — Recruiter / MO 岗位管理

### 负责范围

- Recruiter(MO) dashboard
- 岗位创建、修改、关闭、重开
- Recruiter 看到自己名下岗位的流程

### 建议上传文件

- `framework/recruitassist-web/src/main/webapp/WEB-INF/jsp/dashboard-mo.jsp`
- `framework/recruitassist-web/src/main/java/com/recruitassist/web/CreateJobServlet.java`
- `framework/recruitassist-web/src/main/java/com/recruitassist/web/UpdateJobServlet.java`
- `framework/recruitassist-web/src/main/java/com/recruitassist/web/ChangeJobStatusServlet.java`
- `framework/recruitassist-web/src/main/java/com/recruitassist/service/JobService.java`
- `data/jobs/` 下与岗位创建、修改、状态变化相关的文件

### 对应功能

- 发布新岗位
- 编辑岗位
- 关闭 / 重开岗位
- Recruiter dashboard 的 owned jobs 展示

### 建议 commit 主题

- `feat: add recruiter dashboard and job lifecycle management`

---

## 4.4 Haopeng Jin — 推荐引擎、岗位详情、候选人排序

### 负责范围

- 推荐分数与解释逻辑
- 岗位详情页
- 候选人排序 / 状态更新核心逻辑
- 申请记录核心服务层

### 建议上传文件

- `framework/recruitassist-web/src/main/webapp/WEB-INF/jsp/job-detail.jsp`
- `framework/recruitassist-web/src/main/java/com/recruitassist/web/JobDetailServlet.java`
- `framework/recruitassist-web/src/main/java/com/recruitassist/web/UpdateApplicationStatusServlet.java`
- `framework/recruitassist-web/src/main/java/com/recruitassist/service/ApplicationService.java`
- `framework/recruitassist-web/src/main/java/com/recruitassist/service/RecommendationService.java`
- `framework/recruitassist-web/src/main/java/com/recruitassist/model/view/JobRecommendation.java`
- `data/applications/` 下与申请记录、状态变更相关的文件

### 对应功能

- 推荐引擎
- 匹配解释
- 岗位详情页的多角色视图
- MO 审核候选人、更新状态
- 申请记录快照与排序逻辑

### 建议 commit 主题

- `feat: refine recommendation engine and job detail review flow`

---

## 4.5 Zhuang Hou — Admin 监控与工作量治理

### 负责范围

- Admin dashboard
- 工作量阈值与负载监控
- 管理员筛选视图和系统运营视角

### 建议上传文件

- `framework/recruitassist-web/src/main/webapp/WEB-INF/jsp/dashboard-admin.jsp`
- `framework/recruitassist-web/src/main/java/com/recruitassist/web/DashboardServlet.java`
- `framework/recruitassist-web/src/main/java/com/recruitassist/service/WorkloadService.java`
- `framework/recruitassist-web/src/main/java/com/recruitassist/model/view/WorkloadEntry.java`
- `framework/recruitassist-web/src/main/java/com/recruitassist/model/SystemConfig.java`
- `data/system/config.json`

### 对应功能

- Admin 总览面板
- 工作量监控与阈值判断
- 开放 / 关闭岗位筛选
- 近期申请动态与运营视图

### 建议 commit 主题

- `feat: build admin monitoring and workload governance view`

---

## 4.6 Zexuan Dong — 平台底层、数据存储、启动与种子数据

### 负责范围

- 启动与应用装配
- 文件存储与 repository 层
- 核心数据模型
- 种子数据与演示数据生成脚本

### 建议上传文件

- `framework/recruitassist-web/pom.xml`
- `framework/recruitassist-web/src/main/webapp/WEB-INF/web.xml`
- `framework/recruitassist-web/src/main/java/com/recruitassist/config/`
- `framework/recruitassist-web/src/main/java/com/recruitassist/repository/`
- `framework/recruitassist-web/src/main/java/com/recruitassist/util/JsonFileStore.java`
- `framework/recruitassist-web/src/main/java/com/recruitassist/web/AppServlet.java`
- `framework/recruitassist-web/src/main/java/com/recruitassist/model/ActionResult.java`
- `framework/recruitassist-web/src/main/java/com/recruitassist/model/ApplicationRecord.java`
- `framework/recruitassist-web/src/main/java/com/recruitassist/model/ApplicationStatus.java`
- `framework/recruitassist-web/src/main/java/com/recruitassist/model/JobPosting.java`
- `framework/recruitassist-web/src/main/java/com/recruitassist/model/JobStatus.java`
- `framework/recruitassist-web/src/main/java/com/recruitassist/model/UserProfile.java`
- `framework/recruitassist-web/src/main/java/com/recruitassist/model/UserRole.java`
- `data/users/`
- `data/system/id-counters.json`
- `scripts/generate_demo_load.py`
- `.gitignore`

### 对应功能

- 项目启动与服务初始化
- JSON 文件存储
- 用户 / 岗位 / 申请 / 系统配置 repository
- 核心数据结构
- 演示数据生成与基础配置支撑

### 建议 commit 主题

- `chore: maintain core platform storage bootstrap and demo seeds`

---

## 5. 建议上传顺序

为了降低冲突，建议按下面顺序上传：

1. **Zexuan Dong**：先上传底层框架、模型、repository、种子数据
2. **Yi Qi**：再上传 README、首页、登录、展示素材
3. **Tianyu Zhao**：上传 TA dashboard 和前端交互
4. **Jie Ren**：上传 Recruiter / MO 岗位管理部分
5. **Haopeng Jin**：上传推荐引擎、岗位详情、候选人排序逻辑
6. **Zhuang Hou**：最后上传 Admin 监控与工作量治理

这样做的原因是：

- 底层依赖先稳定；
- 页面入口和说明文档先成型；
- 再分别合入 TA / MO / Recommendation / Admin；
- 最后由 Admin 模块收口整体监控视图。

---

## 6. 上传时的执行建议

### 6.1 每个人只 add 自己负责的部分

不要直接：

- `git add .`

建议用：

- `git add <自己负责的文件或目录>`

### 6.2 如果碰到共享文件冲突

按这份文档的 owner 处理：

- 不是 owner 的人，**不要主动上传共享文件**；
- 如果必须修改共享文件，先在组内说明，再由 owner 统一提交。

### 6.3 如果只想让“每个人都有贡献记录”

最稳妥的方式不是强行打散同一个文件，而是：

- 每个人上传 **自己负责的模块文件集合**；
- 每个人各自有 1~2 次 commit 即可；
- 最终能清楚说明：谁负责哪个模块、谁上传了哪部分内容。

---

## 7. 最终建议

如果你们后面要把当前项目同步到 `Group38-TA_Recruitment` 仓库，可以直接按本分工执行。最关键的是：

- **不要六个人同时改同一个共享文件**；
- **每个人只传自己负责的那一包**；
- **先传底层，再传页面和业务功能**；
- **把这份文档当成 GitHub 上传责任划分说明**。

如需继续细化，我下一步可以再帮你补一份：

1. **每个人的 `git add` 示例命令清单**；或
2. **六个人各自单独一页的上传 checklist**。
