# Spring Boot Demo 1 学习资料：用户管理 REST API

## 1. 后端在完整 Web 应用中的位置

```text
Vue 前端
→ HTTP 请求
→ Controller
→ Service
→ 数据库或数据层
→ JSON 响应
→ Vue 页面
```

这次先不接 MySQL，使用内存 Map，目的是先理解 Spring Boot 的请求流程。

## 2. 启动类

`@SpringBootApplication` 表示这是 Spring Boot 应用入口。`SpringApplication.run` 启动内置 Web 服务器并扫描项目组件。

## 3. Controller：接收 HTTP 请求

```java
@RestController
@RequestMapping("/api/users")
public class UserController { ... }
```

`@RestController` 表示类中的方法返回值会转换为 JSON；`@RequestMapping` 定义公共路径。

常见映射：

| 注解 | HTTP | 用途 |
|---|---|---|
| `@GetMapping` | GET | 查询 |
| `@PostMapping` | POST | 新增 |
| `@PutMapping` | PUT | 修改 |
| `@DeleteMapping` | DELETE | 删除 |

`@PathVariable` 读取 URL 中的 id；`@RequestBody` 把 JSON 请求体转换成 Java 对象。

## 4. Service：业务逻辑

`UserService` 使用 `@Service` 注册为 Spring Bean。Controller 只负责 HTTP 层，实际的查找、新增、修改、删除交给 Service。这样以后把 Map 换成 MySQL，不需要把数据库代码塞进 Controller。

## 5. 数据对象和校验

`User` 是返回给前端的数据；`UserRequest` 是接收新增和修改请求的数据。`@NotBlank` 检查姓名和邮箱不能为空，`@Email` 检查邮箱格式；Controller 的 `@Valid` 触发校验。

## 6. 为什么当前重启会丢数据

`ConcurrentHashMap` 只存在 JVM 内存中。程序停止后内存清空，所以当前 Demo 不是持久化系统。真实项目会把 Service 调用 Repository/Mapper，再由 MySQL 保存。

## 7. 面试和实际工作重点

- 能解释 REST API 的 URL、方法和状态码。
- 能区分 Controller、Service、Repository。
- 能解释 `@RequestBody`、`@PathVariable` 和 `@Valid`。
- 能用 Postman/Apifox 验证接口，不只会启动项目。

## 当前不用深入

暂时不学习复杂异常统一处理、JWT、事务、MyBatis 和数据库索引。先把 CRUD 请求完整跑通，再接 MySQL。
