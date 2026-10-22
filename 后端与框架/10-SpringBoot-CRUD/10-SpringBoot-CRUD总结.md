# Spring Boot Demo 1 CRUD 总结

## 已完成的接口

| 操作 | HTTP | 地址 | 作用 |
|---|---|---|---|
| 查询全部 | GET | `/api/users` | 获取用户列表 |
| 查询单个 | GET | `/api/users/{id}` | 获取一个用户 |
| 新增 | POST | `/api/users` | 创建用户 |
| 修改 | PUT | `/api/users/{id}` | 修改用户 |
| 删除 | DELETE | `/api/users/{id}` | 删除用户 |

## 一次新增请求的完整流程

```text
PowerShell 发送 POST JSON
→ UserController 接收请求
→ @RequestBody 转成 UserRequest
→ @Valid 校验姓名和邮箱
→ UserService 执行业务逻辑
→ ConcurrentHashMap 保存数据
→ Spring Boot 返回 JSON
```

## 三层职责

### Controller

负责 HTTP 层：路径、请求方法、参数接收和响应状态码。不应该把所有业务逻辑都写在这里。

### Service

负责业务逻辑：创建 id、查询、修改和删除。以后接 MySQL 时，Controller 基本不用改。

### 数据层

当前使用 `ConcurrentHashMap`，只存在内存中。项目重启后数据会丢失。下一步会换成 MySQL，并增加 Repository/Mapper。

## 测试命令

新增：

```powershell
Invoke-RestMethod -Uri "http://localhost:8081/api/users" -Method POST -ContentType "application/json" -Body '{"name":"小李","email":"li@example.com"}'
```

修改：

```powershell
Invoke-RestMethod -Uri "http://localhost:8081/api/users/2" -Method PUT -ContentType "application/json" -Body '{"name":"小李同学","email":"xiaoli@example.com"}'
```

删除：

```powershell
Invoke-RestMethod -Uri "http://localhost:8081/api/users/2" -Method DELETE
```

## 你现在具备的能力

- 能区分 GET、POST、PUT、DELETE。
- 能使用 JSON 请求体调用后端接口。
- 能理解 Controller → Service → 数据层的调用关系。
- 能用 PowerShell 验证 API，而不是只看代码。

## 当前限制

数据没有持久化；重启 Spring Boot 后会恢复初始用户。真实项目必须使用 MySQL 或其他数据库保存数据。
