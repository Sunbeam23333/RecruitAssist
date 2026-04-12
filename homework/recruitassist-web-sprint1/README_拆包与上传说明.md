# RecruitAssist Sprint1 拆包与上传说明

这个目录下我已经帮你生成了两类内容：

## 1. 完整提交基线

- `merged_submission_base/`

这是一个已经和当前完整项目对齐后的 **可运行基线版本**：

- 已复制 `data/`
- 已保留 `framework/recruitassist-web/` 相对路径结构
- 可以直接作为独立根目录运行

运行说明见：`merged_submission_base/README_运行说明.md`

## 2. 六个可独立分发的上传包

- **项目_Yi_Qi**：Yi Qi，负责 首页、登录、README、展示素材（分 3 次上传，分支前缀 `YiQi/SprintX`）
- **项目_Tianyu_Zhao**：Tianyu Zhao，负责 TA 流程、CV 上传下载、前端样式与交互（分 3 次上传，分支前缀 `TianyuZhao/SprintX`）
- **项目_Jie_Ren**：Jie Ren，负责 Recruiter/MO 岗位管理与 recruiter dashboard（分 3 次上传，分支前缀 `JieRen/SprintX`）
- **项目_Haopeng_Jin**：Haopeng Jin，负责 推荐引擎、岗位详情、候选人排序与申请服务层（分 3 次上传，分支前缀 `HaopengJin/SprintX`）
- **项目_Zhuang_Hou**：Zhuang Hou，负责 Admin 监控、工作量治理、全局 dashboard 分流（分 3 次上传，分支前缀 `ZhuangHou/SprintX`）
- **项目_Zexuan_Dong**：Zexuan Dong，负责 平台底层、存储、配置、模型、脚本与基础数据（分 3 次上传，分支前缀 `ZexuanDong/SprintX`）

每个 `项目_名字/` 目录里都已经带了：

- `README_上传方法.md`：该成员按 `Sprint1` / `Sprint2` / `Sprint3` 分三次上传的详细步骤
- `FILE_LIST.txt`：该成员负责的总文件清单

## 3. 这次新的上传规则

1. 每个人不再一次性整包上传，而是拆成 **3 个 branch + 3 次 commit**
2. 分支命名统一使用：`姓名英文连写/Sprint1`、`姓名英文连写/Sprint2`、`姓名英文连写/Sprint3`
3. 每次只同步本次 Sprint 对应的小批文件，不要整包 `rsync` 后再 `git add .`
4. 如果上一次整包上传已经被 revert，就从最新 `main` 重新按新的 Sprint 分支方式上传

## 4. 推荐你现在怎么做

1. 先检查 `merged_submission_base/` 是否符合你们本次提交范围
2. 然后把对应的 `项目_名字/` 文件夹分别打包发给每个人
3. 让每个人严格按自己目录里的 `README_上传方法.md` 的三个 Sprint 小节执行
4. 每传完一批，就在 GitHub 上单独发一个 PR，避免一次提交过大

## 5. 当前拆包规则说明

这次拆包基于 `homework/plan/github-upload-split/group38_upload_split_zh.md`，同时我补齐了当前完整项目里原文档没有单独列出的公共文件归属，例如：

- `AuditRepository.java` 被归入底层平台包
- `AuthService.java` 被归入底层平台包
- `index.jsp` 和 `PERFORMANCE_EVOLUTION.md` 被归入首页/文档包
- `data/cv/` 被归入 TA / CV 功能包

这样拆出来以后，六个人的文件夹之间 **不会互相覆盖同一路径里的同一个文件**；同时现在每个人包内又进一步拆成了 3 个更小的上传批次。
