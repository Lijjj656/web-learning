# Vue Demo 8 学习资料：Axios 调用后端 API

## Axios 解决什么问题

Axios 是一个常用 HTTP 客户端。它可以向后端 API 发 GET、POST、PUT、DELETE 请求。真实 Vue 项目通常把 Axios 配置集中到一个文件，避免每个页面重复写服务器地址、超时和认证逻辑。

```text
Vue 页面
→ Axios 请求 API
→ Spring Boot Controller
→ Service / MySQL
→ JSON 响应
→ Axios response.data
→ Vue 更新页面
```

## Axios 实例

```js
const http = axios.create({
  baseURL: "https://jsonplaceholder.typicode.com",
  timeout: 8000,
});
```

`baseURL` 是公共前缀，因此 `http.get('/users/1')` 实际访问完整地址 `https://jsonplaceholder.typicode.com/users/1`。

以后切换 Spring Boot 后端时，只需要改这里的 `baseURL`，例如 `http://localhost:8080/api`。

## 请求与响应

```js
const response = await http.get(`/users/${id}`);
user.value = response.data;
```

Axios 返回的 `response` 包含状态码、响应头和数据；最常用的是 `response.data`。Axios 会自动把 JSON 转为 JavaScript 对象，因此不需要像 fetch 一样额外写 `await response.json()`。

## 三种界面状态

Demo 使用三个 ref：

- `loading`：正在请求时为 `true`。
- `errorMessage`：请求失败时保存提示文字。
- `user`：请求成功时保存用户数据。

模板根据这些数据用 `v-if / v-else-if / v-else` 显示加载、错误、用户卡片或初始提示。真实项目也必须处理这三种状态，不能只写“成功时显示数据”。

## try、catch、finally

```js
try {
  // 请求成功后的逻辑
} catch (error) {
  // 网络、超时、HTTP 错误后的逻辑
} finally {
  // 无论成功或失败都运行
}
```

Demo 在 `finally` 里写 `loading.value = false`，保证请求结束后按钮恢复可点击。如果不写，失败时按钮可能永远显示“查询中”。

## 前端校验和后端校验

页面先检查编号是否为 1 到 10 的整数，减少无意义请求。但真实项目中后端仍必须校验所有参数，不能信任前端传来的数据。

## Axios 与 fetch 的区别

| fetch | Axios |
|---|---|
| 浏览器原生提供 | 需要安装依赖 |
| 需手动 `response.json()` | 自动解析 JSON 到 `response.data` |
| HTTP 4xx/5xx 通常需手动检查 | HTTP 非成功状态通常进入 catch |
| 配置需自己封装 | 可直接创建实例、设置 baseURL 和拦截器 |

两者都能使用。企业 Vue 项目中 Axios 仍然常见；现代项目也有团队选择 fetch 并自行封装。

## 当前必须理解

- API 请求需要处理加载、成功和失败。
- `axios.create` 让公共配置集中。
- `response.data` 是最常用的 API 数据。
- `finally` 适合恢复加载状态。
- 前端校验提升体验，后端校验保证安全。

下一节可以学习 Element Plus，用成熟组件库更快搭建后台管理界面。
