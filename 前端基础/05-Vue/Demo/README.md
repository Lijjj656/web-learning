# Vue Demo 5：Vue Todo List

## 运行

在此目录运行：

```powershell
npm install
npm run dev
```

终端会显示本地地址，通常为 `http://localhost:5173/`。浏览器打开这个地址，不要直接双击 `index.html`。

## 先操作

1. 添加三项任务。
2. 勾选一项任务。
3. 点击“未完成”和“已完成”筛选。
4. 刷新浏览器，确认任务仍保留。
5. 点击“清除已完成”。

## 文件职责

- `src/main.js`：创建 Vue 应用并挂载到 `index.html` 的 `#app`。
- `src/App.vue`：页面模板、任务数据和业务逻辑，当前最重要的文件。
- `src/style.css`：页面样式。
- `package.json`：项目依赖和运行脚本。

## 本节重点

- `ref`：创建会被 Vue 追踪的响应式数据。
- `computed`：从现有数据计算结果，例如剩余任务数。
- `v-model`：把输入框或复选框和数据双向绑定。
- `v-for`：根据数组重复渲染任务。
- `v-if` / `v-else`：根据条件显示任务列表或空状态。
- `@click` / `@submit.prevent`：处理点击和表单提交。
- `watch`：监听任务数据变化并保存到 localStorage。

## 修改任务

1. 修改页面标题和提示语。
2. 把“还剩 X 项”改成“已完成 X 项”，使用新的 `computed`。
3. 增加任务优先级：普通、重要、紧急。
4. 增加“全部清除”按钮。
5. 新增一个 `watch`，在控制台输出当前任务数量。
