# Vue Demo 7：Pinia 登录状态管理

## 运行

```powershell
npm install
npm run dev
```

## 操作

1. 输入名字登录。
2. 对比顶部导航和仪表盘，它们显示相同用户名。
3. 刷新页面，确认用户名和登录时间仍在。
4. 点击退出，观察两个位置的用户名和状态都被清空。

## 关键文件

- `src/stores/user.js`：Pinia Store，保存用户全局状态。
- `state`：实际数据，例如姓名、登录时间。
- `getters`：从 state 计算出的结果，例如 `isLoggedIn`。
- `actions`：修改 state 的方法，例如 `login`、`logout`。

Pinia 适合保存登录用户、主题设置、购物车、全局通知等多个页面或组件都会使用的数据。
