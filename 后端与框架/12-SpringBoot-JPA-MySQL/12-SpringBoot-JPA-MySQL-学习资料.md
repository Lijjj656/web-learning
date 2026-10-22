# Spring Boot Demo 2 学习资料：JPA 连接 MySQL

## 1. 从内存到数据库

上一版的 Service 把用户放进 `ConcurrentHashMap`，重启就丢失。这一版的 Service 调用 `UserRepository`，Repository 由 Spring Data JPA 连接 MySQL。

```text
Controller → Service → Repository → JPA/Hibernate → JDBC → MySQL
```

## 2. Entity：Java 类对应数据表

```java
@Entity
@Table(name = "users")
public class User { ... }
```

`@Entity` 表示这个类要映射到数据库；`@Table` 指定表名。

`@Id` 是主键，`@GeneratedValue(strategy = GenerationType.IDENTITY)` 表示使用 MySQL 的自增 id。`@Column` 可以声明非空、唯一和长度约束。

## 3. Repository：不手写基础 SQL

```java
public interface UserRepository extends JpaRepository<User, Long> { }
```

继承 `JpaRepository` 后，直接拥有 `findAll`、`findById`、`save`、`delete` 等常用方法。Spring Data JPA 根据实体和方法生成 SQL。

## 4. 配置数据库连接

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/learning_app
spring.datasource.username=root
spring.datasource.password=...
```

URL 中包含数据库地址、端口和数据库名。密码只保存在本机配置中，不能提交到公开代码仓库。企业项目通常用环境变量或配置中心管理密码。

## 5. ddl-auto=validate

`validate` 让 Hibernate 检查 Java Entity 和现有表结构是否匹配，但不自动修改表。学习阶段先手动创建 SQL 表，可以避免框架偷偷改变数据库；生产环境更要谨慎使用自动建表。

## 6. 持久化验证

真正接通 MySQL 的关键测试不是“项目启动成功”，而是：新增用户 → 停止服务 → 重启服务 → 仍能查到用户。只有这样才能证明数据确实写进数据库，而不是仍留在内存里。

## 当前必须理解

- Entity 对应表，字段对应列。
- Repository 提供数据库 CRUD。
- JPA/Hibernate 帮助 Java 对象和 SQL 之间转换。
- 数据库连接配置包含 URL、用户名、密码。
- 密码不能写进 GitHub。
