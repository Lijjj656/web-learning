# Java 基础复习：面向对象与集合

## 为什么现在回顾 Java

你已经写过 Spring Boot，但 Spring Boot 的代码本质仍然是 Java：`User` 是类，Controller 和 Service 是对象，`List<User>` 是集合。理解这些基础，才能看懂后端项目，而不是只会复制注解。

## 1. 类和对象

类是“模板”，对象是按模板创建的具体数据。

```java
public class User {
    private Long id;
    private String name;
    private String email;
}
```

这是 User 类，描述用户应有哪些数据。创建对象：

```java
User user = new User();
user.setName("小李");
user.setEmail("li@example.com");
```

这里 `user` 是一个具体用户对象。

在 Spring Boot 中：

```text
MySQL users 表的一行
→ MyBatis 读取
→ User Java 对象
→ Controller 返回 JSON
```

## 2. 字段、方法和构造方法

```java
public class User {
    private String name;              // 字段：保存状态

    public String getName() {         // 方法：读取状态
        return name;
    }

    public void setName(String name) { // 方法：修改状态
        this.name = name;
    }
}
```

- 字段：对象保存的数据。
- 方法：对象能做的动作。
- 构造方法：创建对象时初始化数据。
- `this.name`：当前对象自己的 `name` 字段。

## 3. 封装

字段通常写成 `private`，不让外部代码随意修改：

```java
private String email;
```

对外提供 `getEmail()` 和 `setEmail()`。这样未来可以在 setter 内加校验，例如邮箱不能为空。这个思想叫封装：对象自己控制自己的数据。

## 4. record：简单数据对象的简写

你之前见过：

```java
public record UserRequest(String name, String email) {
}
```

它适合只负责传输数据的对象。Java 会自动生成构造方法、读取方法、`equals`、`hashCode` 和 `toString`。

读取 record 字段写：

```java
request.name()
```

普通类通常写：

```java
user.getName()
```

当前可以记住：请求 DTO 常用 record；需要被 MyBatis/JPA 填充并可修改的 Entity 常用普通类。

## 5. List：有顺序的集合

```java
List<User> users = new ArrayList<>();
users.add(user);
```

`List` 适合保存多个有顺序的数据，例如用户列表、Todo 列表、聊天记录。

遍历：

```java
for (User currentUser : users) {
    System.out.println(currentUser.getName());
}
```

Spring Boot 中：

```java
public List<User> findAll() {
    return mapper.findAll();
}
```

表示查询后返回多个用户。

## 6. Map：按键快速查找

```java
Map<Long, User> userMap = new HashMap<>();
userMap.put(1L, user);
User found = userMap.get(1L);
```

Map 是“键 → 值”的关系。你最早的内存版用户 API 使用 `ConcurrentHashMap<Long, User>`：id 是 key，用户对象是 value。

```text
Map：程序内存临时查找
MySQL：长期保存关系数据
Redis：高速键值缓存
```

## 7. 接口和实现

```java
public interface UserMapper {
    User findById(Long id);
}
```

接口描述“需要做什么”，不写具体实现。MyBatis 根据 XML 为 `UserMapper` 生成实现；Spring 注入这个实现到 Service。

```text
UserService
→ 调用 UserMapper.findById
→ MyBatis 执行 SQL
→ MySQL
```

## 8. 现在必须理解

- 类是模板，对象是具体实例。
- private 字段 + getter/setter 是封装。
- List 保存多个有顺序对象。
- Map 根据 key 查找 value。
- record 适合简单请求/响应数据。
- 接口描述能力，框架或其他类提供实现。

## 暂时不需要深入

继承、多态的复杂设计、抽象类、反射、泛型边界、JVM 内存模型。它们重要，但当前优先把类、对象、集合与 Spring Boot 的关系理解清楚。
