# merged_submission_base 运行说明

这个目录是根据当前 `RecruitAssist` 最新完整项目生成的 **可独立运行提交基线**。

目录特点：

- 已经带上 `data/`
- 保持了 `framework/recruitassist-web` + `data` 的相对结构
- 可以直接从当前目录作为项目根目录启动

## 启动方式

在当前目录执行：

```bash
RECRUITASSIST_BASE_DIR=$(pwd) mvn -f framework/recruitassist-web/pom.xml test
RECRUITASSIST_BASE_DIR=$(pwd) mvn -f framework/recruitassist-web/pom.xml org.eclipse.jetty.ee10:jetty-ee10-maven-plugin:12.0.15:run -Djetty.http.port=8081 -Djetty.contextPath=/
```

如果你在 macOS 且想复用仓库脚本，也可以执行：

```bash
RECRUITASSIST_BASE_DIR=$(pwd) zsh scripts/mvn17.sh -f framework/recruitassist-web/pom.xml test
RECRUITASSIST_BASE_DIR=$(pwd) zsh scripts/mvn17.sh -f framework/recruitassist-web/pom.xml org.eclipse.jetty.ee10:jetty-ee10-maven-plugin:12.0.15:run -Djetty.http.port=8081 -Djetty.contextPath=/
```

页面地址：

```text
http://127.0.0.1:8081/
```
