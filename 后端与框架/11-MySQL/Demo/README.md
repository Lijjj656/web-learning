# MySQL Demo 1：创建数据库和用户表

## 在 MySQL Workbench 中执行

1. 打开 MySQL Workbench，连接本地 `root` 用户。
2. 点击左上角新建 SQL 标签页。
3. 打开 `01-create-database.sql`，复制全部内容到 SQL 编辑器。
4. 点击闪电图标执行。
5. 在左侧 Schemas 中刷新，确认看到 `learning_app`。
6. 展开 `learning_app → Tables`，确认看到 `users`。

## 核心 SQL

- `CREATE DATABASE`：创建数据库。
- `USE learning_app`：选择当前数据库。
- `CREATE TABLE users`：创建用户表。
- `id ... AUTO_INCREMENT`：主键自动递增。
- `email ... UNIQUE`：邮箱不能重复。
- `VARCHAR(50)`：最多 50 个字符的文本。

## 验证

在 Workbench 执行：

```sql
USE learning_app;
SELECT * FROM users;
```

应该能看到初始用户。

## 密码提醒

Spring Boot 连接数据库时需要 root 密码或专用开发账号密码。不要把真实密码提交到 GitHub；之后会用 `application-local.properties` 保存本机配置，并忽略该文件。
