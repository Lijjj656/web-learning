# Spring Boot Demo 1：用户管理 API

## 运行

在项目目录打开命令提示符，执行：

```bat
mvn spring-boot:run
```

启动成功后，API 地址是 `http://localhost:8080/api/users`。

## 测试接口

查询全部：

```text
GET http://localhost:8080/api/users
```

新增用户，请用 Postman、Apifox 或前端发送 JSON：

```json
POST http://localhost:8080/api/users
{
  "name": "小李",
  "email": "li@example.com"
}
```

修改：`PUT /api/users/1`；删除：`DELETE /api/users/1`；查询单个：`GET /api/users/1`。

## 当前限制

数据暂时保存在内存中，程序重启后会恢复初始数据。下一 Demo 再接 MySQL。
