# Spring Boot Demo 4 学习资料：JWT 登录认证

## 1. 登录认证解决什么问题

CRUD 接口目前任何人都能访问。认证要解决“你是谁”，授权才解决“你能做什么”。本 Demo 先做认证：用户登录成功后获得 Token，后续请求带上 Token。

```text
注册/登录
→ 后端校验密码
→ 返回 JWT
→ 前端保存 JWT
→ 后续请求携带 Authorization: Bearer Token
→ 后端验证签名和过期时间
```

## 2. 密码为什么不能明文保存

数据库泄露时，明文密码会直接暴露。BCrypt 会把密码转换为不可逆哈希；登录时用 `matches` 比较输入密码和哈希，而不是还原密码。

## 3. JWT 包含什么

JWT 由签名保护的声明组成。本 Demo 把用户邮箱放在 `subject`，同时写入签发时间和过期时间。服务端使用密钥验证签名，防止客户端伪造内容。

JWT 不是加密保险箱，Payload 不应放密码等敏感信息。密钥必须保密。

## 4. Authorization 头

```text
Authorization: Bearer eyJ...
```

`Bearer` 表示“持有这个 Token 的人”。后端去掉前缀，解析并验证 Token，再根据邮箱查询用户。

## 5. 当前 Demo 的边界

为了聚焦原理，Token 校验写在 `AuthService` 中，后续学习 Spring Security 时再把校验放进过滤器，实现所有接口统一保护。真实项目还需要刷新 Token、注销策略、权限角色和 HTTPS。

## 当前必须理解

- 认证和授权的区别。
- BCrypt 只保存密码哈希。
- JWT 是带签名和过期时间的身份凭证。
- 后续请求通过 Bearer Token 表示身份。
- 密钥和密码不能提交到 GitHub。
