# MySQL Demo 1 学习资料：数据库、表和基础 SQL

## 数据库在项目中的位置

内存 Map 中的数据会随着 Spring Boot 重启而消失。MySQL 把数据写入磁盘，服务重启后仍能查询。完整链路：

```text
Vue → Axios → Spring Boot Controller → Service → Repository / Mapper → MySQL
```

## 数据库和表

`learning_app` 是数据库，类似一个项目的数据空间；`users` 是表，类似一张有固定列的表格。每一行是一条用户记录。

## users 表字段

| 字段 | 类型 | 用途 |
|---|---|---|
| `id` | BIGINT | 用户唯一编号、主键 |
| `name` | VARCHAR(50) | 用户姓名 |
| `email` | VARCHAR(100) | 用户邮箱，不能重复 |
| `created_at` | DATETIME | 创建时间 |

## 主键、唯一约束和自增

- `PRIMARY KEY`：每一行唯一标识，通常按 id 查询和修改。
- `AUTO_INCREMENT`：新增记录时 MySQL 自动产生下一个 id。
- `UNIQUE`：禁止重复值。这里防止两个用户使用同一个邮箱。

## 基础 CRUD SQL

```sql
SELECT * FROM users;
INSERT INTO users (name, email) VALUES ('小李', 'li@example.com');
UPDATE users SET name = '小李同学' WHERE id = 1;
DELETE FROM users WHERE id = 1;
```

`WHERE` 极其重要。没有 `WHERE` 的 UPDATE 或 DELETE 会修改或删除整张表的数据。学习期间也必须养成先 SELECT 确认目标，再执行修改的习惯。

## 当前必须理解

- 数据库、表、行、列的关系。
- 为什么 id 需要主键。
- 为什么 email 添加唯一约束。
- `SELECT / INSERT / UPDATE / DELETE` 分别做什么。
- Spring Boot 需要通过 JDBC 连接信息才能访问 MySQL。

下一步将让 Spring Boot 使用 Spring Data JPA 连接 `learning_app`，把内存 CRUD 换成数据库 CRUD。
