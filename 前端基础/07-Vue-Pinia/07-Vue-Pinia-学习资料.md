# Vue Demo 7 学习资料：Pinia 状态管理

## Pinia 解决什么问题

组件数量变多时，把用户名放在登录页、再通过多层组件传递给导航栏和首页会很麻烦。Pinia 提供全局共享的 Store：任何组件都能读取同一份数据，也通过统一的方法修改它。

```text
LoginView 修改 userStore
→ App 顶部导航自动读取新用户名
→ Dashboard 自动读取新用户名
```

## 创建 Pinia

```js
createApp(App).use(createPinia()).use(router).mount("#app");
```

`createPinia()` 创建状态管理器，`use()` 安装到 Vue 应用。之后组件才能调用 Store。

## Store 的三个部分

```js
export const useUserStore = defineStore("user", {
  state: () => ({ name: "", loginTime: "" }),
  getters: { isLoggedIn: (state) => Boolean(state.name) },
  actions: { login(name) { this.name = name; } },
});
```

- `state`：真实数据。
- `getters`：由 state 推导的只读结果，类似 `computed`。
- `actions`：修改 state 的方法，集中处理业务逻辑。

## 在组件中使用

```js
const userStore = useUserStore();
userStore.login("小李");
console.log(userStore.name);
```

模板中可以直接用：

```vue
{{ userStore.greeting }}
```

Store 是响应式的：登录页调用 `login` 后，使用 `userStore.name` 的所有页面都会自动更新。

## Pinia 与 localStorage 的区别

Pinia 保存的是应用运行期间的内存状态；刷新页面后通常会丢失。Demo 中在 action 内同步写入 localStorage，所以刷新后能恢复用户名。真实项目登录状态通常还会配合 JWT Token、后端接口和路由权限控制。

## 真实工作中的用途

- 保存当前登录用户、Token、权限信息。
- 保存浅色/深色主题。
- 保存全局通知和加载状态。
- 保存跨页面共享的待办、聊天会话或购物车数据。

## 当前必须理解

- Pinia 用于多个组件共享状态。
- `state` 是数据，`getters` 是计算结果，`actions` 是修改数据的方法。
- Store 更新后，所有使用它的页面会自动更新。

下一节学习 Axios：让 Vue 以更工程化的方式调用后端 API。
