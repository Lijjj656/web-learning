# JavaScript Demo 3：学习任务 Todo List

## 运行方式

用 Visual Studio 2022 打开 `index.html`，保存后用浏览器打开。`index.html`、`style.css`、`app.js` 必须在同一个文件夹。

## 练习顺序

1. 添加三项任务。
2. 点击复选框，观察任务变成删除线。
3. 删除一项任务。
4. 刷新浏览器，确认任务仍然存在。
5. 点击“清除已完成”。
6. 打开 `app.js`，先阅读变量、`renderTodos`、表单 `submit` 事件。

## 修改任务

1. 把页面标题改成你自己的学习计划。
2. 把任务数量文字改成“还剩 X 项”。
3. 增加一个“全部清除”按钮。
4. 为任务增加优先级字段：普通、重要、紧急。
5. 防止添加长度少于 2 个字符的任务，并在页面上显示提示。

## 完成标准

- 能添加、完成、删除任务。
- 能解释 `querySelector`、事件监听、数组 `push` 和 `filter`。
- 能解释为什么提交表单时要 `event.preventDefault()`。
- 能解释 `localStorage` 为什么只能保存字符串，以及 `JSON.stringify` / `JSON.parse` 的作用。
