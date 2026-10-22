# Spring Boot Demo 2：连接 MySQL 的用户 API

## 第一步：填写密码

打开 `src/main/resources/application.properties`，把这一行：

```properties
spring.datasource.password=请替换为你安装MySQL时设置的root密码
```

改成你自己的密码。不要把密码发给我，也不要提交到 GitHub。

## 第二步：构建和运行

在项目目录打开 PowerShell：

```powershell
mvn clean package
java -jar target/mysql-user-api-0.0.1-SNAPSHOT.jar
```

接口地址：`http://localhost:8082/api/users`。

## 第三步：验证持久化

1. 用浏览器或 PowerShell 查询用户。
2. 用 POST 新增一个用户。
3. 停止 Spring Boot，再重新启动。
4. 再次查询，确认新用户仍然存在。

## 关键变化

上一版使用 `ConcurrentHashMap`；这一版使用 `UserRepository extends JpaRepository`，数据由 MySQL 持久化。
