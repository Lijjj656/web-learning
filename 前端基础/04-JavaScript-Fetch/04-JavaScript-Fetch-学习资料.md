# JavaScript Demo 4 学习资料：Fetch、Promise 和 JSON

## 1. 这次解决什么问题

前一个 Todo Demo 的数据只在浏览器本地。这次让页面向外部 API 请求数据，理解真实 Web 应用最常见的流程：

```text
用户操作 → JavaScript 发请求 → API 返回 JSON → 页面显示
```

## 2. 什么是异步

网络请求需要等待服务器响应。浏览器不能因为等待而完全停止页面，所以 JavaScript 会先发出请求，稍后在结果回来时继续执行，这就是异步。

## 3. fetch 和 Promise

```js
const response = await fetch(url);
```

`fetch` 发起 HTTP 请求，返回一个 Promise。Promise 可以理解为“未来会得到的结果”。`await` 等待这个结果，同时不会阻塞整个浏览器页面。

`async` 必须写在使用 `await` 的函数前面：

```js
form.addEventListener("submit", async (event) => {
```

## 4. response.json()

服务器返回的响应对象还不是最终的 JavaScript 数据。必须执行：

```js
const user = await response.json();
```

它把 JSON 文本解析成 JavaScript 对象。之后才能使用：

```js
user.name
user.company.name
user.address.city
```

对象里的对象叫嵌套对象，使用点号逐层访问。

## 5. try...catch 错误处理

```js
try {
  // 可能成功或失败的请求代码
} catch (error) {
  // 出错时执行
}
```

网络断开、地址错误、服务器异常时，需要给用户明确提示，而不是让页面无反应。

`response.ok` 表示 HTTP 状态是否成功。404 等错误有时不会让 `fetch` 自动进入 `catch`，所以 Demo 主动检查：

```js
if (!response.ok) {
  throw new Error("找不到这个用户");
}
```

`throw` 主动抛出错误，随后由 `catch` 统一处理。

## 6. 状态提示和页面更新

请求开始时显示“正在查询”，成功后显示结果，失败后显示错误。这是用户体验的基本状态：加载中、成功、失败。

```js
status.textContent = "正在查询...";
userCard.hidden = true;
```

`hidden` 可以控制元素是否显示，`textContent` 可以修改页面文字。

## 7. Network 面板

浏览器开发者工具的 Network 面板可以看到：请求 URL、请求方法、状态码和响应内容。实际排查前后端问题时，这是最常用的工具之一。

## 8. 安全提醒

Demo 为了直观使用了模板字符串写入用户数据。真实项目中，如果数据来自不可信用户，应优先使用 `textContent` 创建节点，避免把恶意内容当 HTML 执行。公开 API 的数据通常也应该经过校验。

## 9. 现在必须理解与可以暂缓的内容

必须理解：异步等待、`fetch`、Promise、JSON、HTTP 状态、`try...catch` 和 DOM 更新。暂时可以不深入 Promise 的底层实现、请求取消、重试策略和鉴权，后面接 Spring Boot API 时再学习。

## 10. 自测题

1. 为什么 `fetch` 前面要使用 `await`？
2. `response` 和 `user` 分别是什么？
3. 为什么要调用 `response.json()`？
4. 为什么 `response.ok` 失败时要手动 `throw`？
5. `try...catch` 中哪一部分负责显示错误？
6. 在 Network 面板中可以观察到哪些信息？
