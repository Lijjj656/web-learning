# 学习任务管理系统：JWT 与任务后端

## 准备

1. 使用已有的 `learning_app` 数据库和 `users` 表。确认用户表已有 `password_hash`；本机已有此字段，不要重复执行 ALTER TABLE。
2. 在 MySQL Workbench 执行 `src/main/resources/02-create-tasks.sql`。本机已创建 `tasks`，脚本可以重复执行。
3. 在 `application.properties` 填写本机 MySQL 配置；不要把包含真实密码的文件公开上传。
4. `app.jwt.secret` 当前仅用于本地学习；配置示例、环境变量和统一交付目录将在计划中的配置整理阶段完成。

## 运行

```powershell
cd "$env:USERPROFILE\Desktop\14-SpringBoot-JWT-Demo"
mvn.cmd spring-boot:run
```

服务端口：`8084`。

另开一个 PowerShell 启动前端，后端窗口保持运行：

```powershell
cd "$env:USERPROFILE\Desktop\09-ElementPlus-后台Demo"
npm.cmd run dev
```

打开前端终端显示的网址，默认 `http://localhost:5173/`。使用 `npm.cmd` 可避免 Windows PowerShell 对 `npm.ps1` 的执行策略限制。

如果提示 8084 被占用，先确认已有后端是否在运行，不要重复启动，也不要停止未知程序。访问 `http://localhost:8084/` 出现 404 不等于服务未启动；后端提供的是 `/api` 接口。

## 接口

- `POST /api/auth/register`：注册，JSON 包含 `name`、`email`、`password`。
- `POST /api/auth/login`：登录，JSON 包含 `email`、`password`，返回 Token。
- `GET /api/auth/me`：请求头添加 `Authorization: Bearer 你的Token`，返回当前用户。
- `GET /api/tasks`：当前用户的全部任务。
- `POST /api/tasks`：新增，成功返回 201 和任务数据。
- `PUT /api/tasks/{id}`：编辑，成功返回更新后的任务。
- `DELETE /api/tasks/{id}`：删除，成功返回 204，无响应体。
- `GET /api/tasks/stats`：返回 `total`、`completed`、`inProgress`、`notStarted`。

任务请求示例：

```json
{"title":"复习 MyBatis","category":"Java","priority":"普通","status":"未开始"}
```

标题必填、最长 200 字符；分类限定 Web/Java/AI，优先级限定普通/高，状态限定未开始/进行中/已完成。后端从验证后的 JWT 取得用户身份，客户端不能指定任务归属。查询、更新和删除 SQL 均包含用户条件。不存在或属于他人的任务统一返回 404；非法输入返回 400；缺少、无效或过期凭证返回 401；意外服务错误返回 500 和通用提示。

密码只保存 BCrypt 哈希，不保存明文密码。

## 验证与构建

后端正在运行时，在后端目录执行：

```powershell
powershell.exe -NoProfile -ExecutionPolicy Bypass -File .\api-smoke-test.ps1
mvn.cmd -q -DskipTests package
```

回归脚本会创建两个随机测试账号并检查 23 项行为，删除本次创建的任务，保留测试账号。不会改动已有账号或任务。执行策略参数只对该次 PowerShell 进程生效。

前端目录执行 `npm.cmd run build`。结果见 `TEST-RESULTS.md`。

当前运行方式是开发联调：Vite 将 `/api` 代理到 8084。前端生产资源由 Spring Boot 提供的同源部署、Vue Router 和统一源代码交付仍按二十天计划继续完成，尚未验收。
