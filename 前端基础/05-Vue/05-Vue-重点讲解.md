# Vue Demo 5 重点讲解：响应式 Todo List

## 这次项目解决什么问题

原生 JavaScript Todo 中，数据变化后需要手动调用 `renderTodos()`，创建元素、插入页面、重新绑定事件。Vue 将这件事自动化：开发者维护数据，Vue 根据数据更新页面。

```text
用户操作
→ 修改 Vue 数据
→ Vue 自动更新页面 DOM
→ 用户看到最新页面
```

## 项目文件职责

| 文件 | 作用 |
|---|---|
| `index.html` | 提供 `<div id="app"></div>` 挂载位置 |
| `src/main.js` | 创建 Vue 应用，挂载 `App.vue` |
| `src/App.vue` | 页面、数据和任务功能的核心文件 |
| `src/style.css` | 页面样式 |
| `package.json` | 项目依赖和 `npm run dev` 命令 |
| `vite.config.js` | 让 Vite 能解析 `.vue` 文件 |

## Vite 为什么必须启动

`.vue` 文件不是浏览器直接认识的普通 JavaScript 文件。Vite 会把 Vue 单文件组件转换成浏览器能运行的代码，还会在保存文件时自动刷新页面。

所以 Vue 项目要用：

```powershell
npm run dev
```

然后通过终端给出的 `http://127.0.0.1:5173/` 打开。不能像前面 HTML Demo 那样直接双击 `index.html`。

## ref：让数据可被 Vue 追踪

```js
const newTodo = ref("");
const filter = ref("all");
const todos = ref([]);
```

`ref` 创建响应式数据：当值变化时，Vue 知道哪些页面部分需要更新。

在 JavaScript 代码中，读写 ref 要使用 `.value`：

```js
newTodo.value = "学习 Vue";
todos.value.push({ id: 1, title: "学习 Vue", completed: false });
```

在模板中不需要 `.value`，Vue 会自动处理：

```vue
{{ newTodo }}
```

## v-model：双向绑定

```vue
<input v-model="newTodo" />
```

它实现两个方向的同步：

```text
用户在输入框输入文字 → newTodo 自动更新
JavaScript 修改 newTodo → 输入框内容自动更新
```

复选框同理：

```vue
<input v-model="todo.completed" type="checkbox" />
```

勾选状态和任务对象中的 `completed` 自动同步。

## v-for：根据数组显示列表

```vue
<li v-for="todo in filteredTodos" :key="todo.id">
  <span>{{ todo.title }}</span>
</li>
```

- `v-for`：遍历数组，每个任务生成一项 `li`。
- `todo`：当前遍历到的一项任务。
- `{{ todo.title }}`：插值语法，把数据展示为文字。
- `:key="todo.id"`：给每一项稳定且唯一的身份，帮助 Vue 在添加、删除、筛选时正确更新列表。

## @submit.prevent 和 @click：事件

```vue
<form @submit.prevent="addTodo">
<button @click="removeTodo(todo.id)">删除</button>
```

- `@submit` 和 `@click` 用来绑定浏览器事件。
- `.prevent` 等价于原生 JavaScript 的 `event.preventDefault()`，阻止表单提交后刷新页面。
- 用户提交表单会调用 `addTodo`；点击删除按钮会调用 `removeTodo` 并传入任务 id。

## computed：自动计算的结果

```js
const remainingCount = computed(() =>
  todos.value.filter((todo) => !todo.completed).length
);
```

`remainingCount` 不是手动维护的变量，而是由 `todos` 推导出来的结果。任务添加、删除、勾选完成时，Vue 自动重新计算它。

筛选列表的 `filteredTodos` 也是 computed：根据当前筛选条件决定显示全部、未完成或已完成任务。

## v-if / v-else：按条件显示

```vue
<ul v-if="filteredTodos.length">...</ul>
<p v-else>当前没有符合条件的任务。</p>
```

有任务时显示列表，没有任务时显示空状态。Vue 会根据条件自动决定哪个元素出现在页面上。

## watch 和 localStorage

```js
watch(
  todos,
  (latestTodos) => localStorage.setItem("vue-learning-todos", JSON.stringify(latestTodos)),
  { deep: true },
);
```

`watch` 监听 `todos` 的变化。每次变化都把最新任务转换成 JSON 字符串后保存到浏览器本地存储，刷新后任务依然存在。

`deep: true` 表示深度监听。因为勾选任务会修改数组内对象的 `completed` 属性，只有深度监听才能可靠捕捉这种内部修改。

## 与原生 JavaScript Todo 的对应关系

| 原生 JavaScript | Vue |
|---|---|
| `querySelector()` 找页面元素 | 模板直接使用数据和指令 |
| 手写 `renderTodos()` | `v-for` 根据数组自动渲染 |
| 手写输入框读取和清空 | `v-model` 自动同步 |
| 手动更新任务数量 | `computed` 自动计算 |
| 手动监听并刷新 DOM | `ref` 数据变化后 Vue 自动更新 |

## 现在必须掌握

- Vue 是数据驱动页面更新的框架。
- `ref` 用于响应式数据。
- JavaScript 中 ref 用 `.value`，模板中通常不用。
- `v-model` 是数据和表单控件的双向绑定。
- `v-for` 按数组渲染列表，必须提供稳定 `key`。
- `computed` 用于从已有数据得出自动更新的结果。
- `watch` 用于在数据变化时执行额外操作，例如保存 localStorage。

## 暂时不用深入

生命周期、`reactive` 的复杂用法、组件通信、Vue Router、Pinia 和 TypeScript。下一节先学习 Vue Router，让一个 Vue 应用拥有多个页面。
