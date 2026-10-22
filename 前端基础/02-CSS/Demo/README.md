# CSS Demo 2：现代登录页

## 本节目标

学会让 HTML 页面具备清晰的布局和样式。重点是理解 CSS 如何选择元素、控制间距和尺寸、使用 Flex 布局、以及在手机上调整布局。

## 如何运行

用 Visual Studio 2022 打开 `index.html`，保存后在浏览器刷新页面。`index.html` 中这行代码把 CSS 文件连接进来：

```html
<link rel="stylesheet" href="style.css" />
```

不要改掉 `href="style.css"`，否则页面会失去样式。

## 推荐学习顺序

1. 先打开浏览器看最终效果。
2. 打开 `index.html`，分清左侧介绍区和右侧登录表单。
3. 打开 `style.css`，按注释的 1 到 4 部分阅读。
4. 每次只改一个数值，保存并刷新观察变化。
5. 缩小浏览器窗口，观察手机布局如何从左右两栏变成上下排列。

## 练习任务

1. 将左侧主题色从 `#147a76` 改成你喜欢的颜色，并同时修改按钮颜色。
2. 将 `.brand-panel` 的 `padding: 64px` 改为 `32px`，观察内容和边缘距离的变化。
3. 将 `.login-page` 的 `flex-direction: column` 临时改成 `row`，缩小浏览器，观察响应式布局是否失效；再改回去。
4. 给按钮增加 `transition: background 0.2s;`，观察鼠标移入时颜色变化更平滑。
5. 试着在 HTML 中增加一个“验证码”输入框，并复制 `.field` 结构让它保持相同样式。

## 完成标准

- 能说明 `class` 和 CSS 选择器如何对应。
- 能说明 `margin` 与 `padding` 的区别。
- 能解释 `display: flex`、`justify-content`、`align-items` 的作用。
- 能说出媒体查询为什么能让手机布局不同。
