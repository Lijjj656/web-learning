# CSS Demo 2 学习资料：现代登录页

## 1. CSS 是什么

HTML 决定“有什么内容和结构”，CSS 决定“它看起来怎样”。例如 HTML 写一个按钮，CSS 决定按钮的颜色、宽度、边框、悬停效果和位置。

CSS 规则的结构是：

```css
选择器 {
  属性: 值;
}
```

例如：

```css
.form-content {
  max-width: 420px;
}
```

`.` 表示选择 class。它会选中 HTML 中 `class="form-content"` 的元素。

## 2. class 和选择器

```html
<section class="brand-panel">
```

```css
.brand-panel {
  background: #147a76;
}
```

两边的 `brand-panel` 名称必须一致。HTML 用 class 给元素命名，CSS 用 `.类名` 找到它并应用样式。一个 class 可以被多个元素重复使用。

## 3. 盒模型：内容、padding、border、margin

每个元素都可以看成一个盒子：

```text
margin（与外部元素的距离）
  border（边框）
    padding（边框到内容的内部距离）
      content（文字、图片等内容）
```

- `padding: 64px`：内容与元素边缘保持 64 像素的内部距离。
- `margin: 24px 0`：元素与上下其他元素保持 24 像素的外部距离。
- `border`：边框。

`box-sizing: border-box` 使指定的宽度包含 padding 和 border，实际开发中更容易控制尺寸。

## 4. Flex 布局

```css
.login-page {
  display: flex;
}
```

`display: flex` 让直接子元素按“弹性布局”排列。Demo 中左边 `.brand-panel` 和右边 `.form-panel` 是 `.login-page` 的直接子元素，因此默认左右排列。

```css
.brand-panel,
.form-panel {
  width: 50%;
}
```

每一栏占父容器的一半。

常用配合属性：

- `flex-direction: column`：主方向改为从上到下。
- `justify-content: center`：沿主方向居中。
- `align-items: center`：沿交叉方向居中。
- `gap: 8px`：弹性布局子元素之间留出固定间隔。

在 `.field-heading` 中，`justify-content: space-between` 让“密码”和“忘记密码？”分别靠左、靠右。

## 5. 宽度与屏幕高度

- `width: 50%`：父元素宽度的一半。
- `width: 100%`：填满父元素宽度。
- `max-width: 420px`：最大只能是 420 像素，小屏幕仍可缩小。
- `min-height: 100vh`：至少和浏览器可视区域一样高。

`.form-content` 同时有 `width: 100%` 和 `max-width: 420px`，因此大屏幕不会太宽，手机上又能随着屏幕变窄。这是常见的响应式写法。

## 6. 表单与伪类

```css
input[type="email"] {
  border: 1px solid #cbd5e1;
}

input[type="email"]:focus {
  outline: 3px solid #c5eee3;
}
```

`input[type="email"]` 选择 type 为 email 的输入框。`:focus` 是伪类，表示用户点击输入框、正在输入时的状态。`:hover` 表示鼠标悬停，例如 Demo 中的按钮变深。

## 7. 响应式媒体查询

```css
@media (max-width: 760px) {
  .login-page {
    flex-direction: column;
  }
}
```

这段代码的意思是：当浏览器宽度不大于 760 像素时，左右两栏改为上下排列。缩小浏览器窗口就能看到效果。

真实工作中，响应式用于保证手机、平板和电脑都能正常使用同一个页面。

## 8. 你现在必须理解的内容

- class 如何连接 HTML 与 CSS。
- `margin` 和 `padding` 的区别。
- Flex 如何决定子元素的排列方向和对齐方式。
- `width`、`max-width`、`min-height` 的区别。
- `@media` 如何根据屏幕宽度覆盖样式。

暂时不用背复杂选择器、动画系统、CSS Grid、Sass 或各种 CSS 框架。先能独立写出清晰的基础页面。

## 9. 自测题

1. `class="form-panel"` 和 `.form-panel` 的关系是什么？
2. `margin` 与 `padding` 分别控制哪里？
3. 为什么 `.form-content` 同时需要 `width: 100%` 和 `max-width: 420px`？
4. `display: flex` 作用在父元素时，会影响谁？
5. 为什么手机布局中要将 `flex-direction` 改为 `column`？
6. `:hover` 与 `:focus` 分别在什么时候生效？
