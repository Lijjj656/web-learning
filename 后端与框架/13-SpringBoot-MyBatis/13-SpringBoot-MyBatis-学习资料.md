# Spring Boot Demo 3 学习资料：MyBatis

## JPA 和 MyBatis 的区别

JPA 根据 Entity 和 Repository 自动生成大量 SQL；MyBatis 让开发者在 XML 中明确写 SQL，再把 SQL 结果映射到 Java 对象。

```text
UserMapper 方法
↔ UserMapper.xml 的 SQL
↔ users 表
```

## Mapper 接口

```java
@Mapper
public interface UserMapper {
    List<User> findAll();
}
```

接口只描述方法，不写实现。MyBatis 启动时读取 XML，并生成代理实现。

## XML 映射

```xml
<select id="findAll" resultMap="UserMap">
  SELECT id, name, email FROM users ORDER BY id
</select>
```

`id="findAll"` 必须对应接口方法名；`resultMap` 告诉 MyBatis 如何把列放进 Java 对象；`#{id}` 是安全参数占位符，可以避免把用户输入直接拼到 SQL 中。

## 新增、修改和删除

- `insert` 执行 INSERT，并通过 `useGeneratedKeys` 取回自增 id。
- `update` 按 id 更新指定行。
- `deleteById` 按 id 删除指定行。

Service 仍负责业务检查，Mapper 负责数据库 SQL，Controller 负责 HTTP 请求。这种分层与 JPA 版相同，只替换了数据访问实现。

## 为什么学习 MyBatis

企业项目常需要精确控制 SQL、复杂查询、联表、分页和性能。MyBatis 让你能直接阅读和优化 SQL，是 Java 后端实习中常见的技能。

## 必须理解

- Mapper 接口与 XML 的对应关系。
- `resultMap` 如何映射列和属性。
- `#{}` 参数占位符比字符串拼接安全。
- Controller、Service、Mapper 的职责边界。
