# Vue Demo 8：Axios 调用 API

## 运行

```powershell
npm install
npm run dev
```

## 操作

1. 输入 `1`、`5`、`10` 进行查询。
2. 输入 `99`，观察前端校验提示，不会发请求。
3. 打开浏览器 F12 的 Network 面板，查询一次，观察 GET 请求、状态码和 JSON 响应。
4. 断开网络后再次查询，观察错误提示。

## 关键文件

- `src/api/http.js`：集中创建 Axios 实例，配置 API 基础地址和超时时间。
- `src/App.vue`：管理请求状态，调用 `http.get()`，显示不同页面状态。

## 学习重点

- `axios.create`：创建可复用的 HTTP 客户端。
- `baseURL`：多个 API 共用的地址前缀。
- `http.get('/users/1')`：发起 GET 请求。
- `response.data`：Axios 已解析后的响应数据。
- `try / catch / finally`：分别处理成功、失败和无论成功失败都要结束加载状态的逻辑。
