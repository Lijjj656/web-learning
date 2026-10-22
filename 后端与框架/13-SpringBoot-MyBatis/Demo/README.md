# Spring Boot Demo 3：MyBatis 用户 API

先在 `src/main/resources/application.properties` 填写 MySQL 密码，然后运行：

```powershell
mvn spring-boot:run
```

服务地址：`http://localhost:8083/api/users`。

本项目与 JPA 版使用同一个 `learning_app.users` 表，但 SQL 写在 `src/main/resources/mapper/UserMapper.xml` 中。重点阅读五条 SQL 与 `UserMapper` 方法的对应关系。
