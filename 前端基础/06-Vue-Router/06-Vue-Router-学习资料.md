# Vue Demo 6 学习资料：Vue Router

## 1. Vue Router 的作用

单页应用通常只加载一次 HTML，之后不需要整页刷新。Vue Router 根据 URL 选择并显示不同 Vue 组件，使应用拥有“登录页、首页、详情页”等多页面体验。

```text
URL 改变
→ Vue Router 匹配路由规则
→ RouterView 显示对应组件
→ 浏览器不进行整页刷新
```

## 2. 路由表

在 `src/router/index.js`：

```js
{ path: "/login", name: "login", component: LoginView }
```

表示访问 `#/login` 时显示 `LoginView.vue`。

```js
{ path: "/detail/:id", name: "detail", component: DetailView }
```

`:id` 是动态参数。例如访问 `#/detail/2` 时，`id` 的值是 `2`。

本 Demo 使用 `createWebHashHistory()`，所以地址带 `#`。它适合初学和静态部署，不需要服务器额外配置。

## 3. RouterLink 和 RouterView

```vue
<RouterLink to="/home">首页</RouterLink>
<RouterView />
```

`RouterLink` 类似升级版的 `<a>` 标签，会改变 URL 并通知 Vue Router；`RouterView` 是页面内容的占位位置，Vue Router 会把匹配组件显示在这里。

## 4. 编程式导航

```js
const router = useRouter();
router.push("/home");
```

当用户登录成功后，代码自动跳转到首页，这就是编程式导航。退出登录后也使用 `router.push("/login")`。

## 5. 读取当前路由

```js
const route = useRoute();
route.params.id
```

`useRoute()` 获取当前 URL 信息。详情页根据 `route.params.id` 读取课程编号，再决定展示哪个课程内容。

## 6. 真实工作中的用途

AI Web 应用至少会有登录页、聊天页、聊天记录页、个人设置页。Vue Router 负责让这些页面在同一个前端应用中切换。之后学习 Pinia 时，登录用户信息可以放进全局状态；连接 Spring Boot 时，Router 还会配合登录权限控制。

## 7. 当前必须理解

- 路由是 URL 与组件的映射关系。
- `RouterLink` 负责跳转，`RouterView` 负责显示页面。
- 动态路由参数让一个详情组件能处理多条数据。
- `useRouter` 用于跳转，`useRoute` 用于读取 URL。

暂时不用深入嵌套路由、路由守卫、懒加载和服务端历史模式。下一节学习 Pinia，解决多个页面共享登录状态的问题。
