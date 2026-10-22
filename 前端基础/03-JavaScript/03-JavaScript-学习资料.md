# JavaScript Demo 3 学习资料：Todo List

## 1. JavaScript 在页面中的作用

HTML 提供输入框、按钮和列表结构；CSS 控制外观；JavaScript 负责读取用户输入、修改页面和保存数据。Todo List 是这三者第一次协作。

## 2. 获取页面元素

```js
const form = document.querySelector("#todo-form");
```

`document` 代表当前网页，`querySelector` 按 CSS 选择器寻找元素，`#todo-form` 表示寻找 `id="todo-form"` 的元素。`const` 创建不会重新指向其他元素的变量。

## 3. 数组、对象和状态

```js
let todos = [
  { id: 1, title: "复习 HTML", completed: false }
];
```

`todos` 是数组，数组中的每一项是一个对象。对象用属性描述一项任务：唯一编号 `id`、文字 `title`、完成状态 `completed`。这些数据就是页面的“状态”。

## 4. 函数和重新渲染

`renderTodos` 负责根据 `todos` 数组重新生成列表。代码先用 `list.innerHTML = ""` 清空旧列表，再通过 `forEach` 遍历每项任务，创建 `li`，最后用 `list.append(item)` 放进页面。

当任务状态发生改变时，程序再次调用 `renderTodos`，所以页面会显示最新数据。这种“数据改变后重新渲染”的思想以后在 Vue 中会经常遇到。

## 5. 事件：让页面响应用户

```js
form.addEventListener("submit", (event) => {
  event.preventDefault();
});
```

`addEventListener` 注册事件监听器。用户提交表单时执行函数。`preventDefault` 阻止浏览器默认提交和刷新页面，让我们用 JavaScript 自己处理数据。

复选框监听 `change`，删除按钮监听 `click`。不同用户动作对应不同事件。

## 6. 输入、push、filter

```js
const title = input.value.trim();
todos.push({ id: Date.now(), title, completed: false });
todos = todos.filter((todo) => todo.id !== id);
```

- `input.value` 读取输入框文字。
- `trim()` 去掉首尾空格。
- `push` 向数组末尾添加数据。
- `filter` 保留满足条件的数据，这里用于删除任务。
- `Date.now()` 生成当前时间数字，作为简单的任务编号。

## 7. localStorage 和 JSON

浏览器的 `localStorage` 可以在当前网站保存少量数据，但它只能保存字符串。

```js
localStorage.setItem("learning-todos", JSON.stringify(todos));
const todos = JSON.parse(localStorage.getItem("learning-todos") || "[]");
```

`JSON.stringify` 把数组和对象转换成字符串；`JSON.parse` 把字符串恢复成 JavaScript 数据。刷新页面时，程序从 `localStorage` 读取任务，所以任务不会消失。

这不是数据库。数据只保存在当前浏览器中，换电脑或清除网站数据后就没有了。真实项目会把数据发送给 Spring Boot，再保存到 MySQL。

## 8. 安全细节

任务文字使用 `textContent` 放入页面，而不是直接拼接到 `innerHTML`，这样用户输入的内容会被当成普通文字，不会被当成 HTML 执行。这是实际开发中很重要的习惯。

## 9. 现在必须理解与可以暂缓的内容

必须理解：变量、数组、对象、函数、DOM 查询、事件、条件判断、`push`、`filter`、JSON 和 `localStorage`。暂时可以不深入原型链、闭包、异步请求和模块打包，后面会单独学习。

## 10. 自测题

1. 为什么 `querySelector("#todo-form")` 前面有 `#`？
2. `todos` 数组中的对象保存了哪些信息？
3. 为什么提交表单需要 `event.preventDefault()`？
4. `push` 和 `filter` 在 Demo 中分别做什么？
5. 为什么保存前要 `JSON.stringify`，读取后要 `JSON.parse`？
6. 为什么任务文字使用 `textContent` 而不是直接放进 `innerHTML`？
