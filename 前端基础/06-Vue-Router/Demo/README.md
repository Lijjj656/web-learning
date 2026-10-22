# Vue Demo 6：Vue Router 多页面应用

## 运行

在本目录运行：

```powershell
npm install
npm run dev
```

打开终端显示的本地地址。不要直接双击 `index.html`。

## 操作

1. 输入名字并登录。
2. 在首页打开三个不同的课程详情。
3. 观察浏览器地址从 `#/home` 变为 `#/detail/1`、`#/detail/2` 等。
4. 使用“返回首页”链接和浏览器前进、后退按钮。
5. 点击“退出登录”，观察地址回到登录页。

## 关键文件

- `src/router/index.js`：定义 URL 和 Vue 组件之间的映射。
- `src/App.vue`：放置导航栏和 `RouterView`。
- `src/views/`：每个页面对应一个 Vue 组件。

## 学习重点

- `createRouter` 创建路由器。
- `routes` 定义路径与页面组件的关系。
- `RouterLink` 用于页面跳转。
- `RouterView` 显示当前 URL 匹配的组件。
- `/detail/:id` 中的 `:id` 是动态路由参数。
- `useRouter()` 用代码跳转页面，`useRoute()` 读取当前 URL 信息。
