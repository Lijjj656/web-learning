# Vue Demo 5 学习资料：Vue 3、Vite 和响应式 Todo List

## 1. Vue 解决什么问题

原生 JavaScript Todo 中，数据变更后要手动调用 `renderTodos()`，创建 `li`、绑定事件、再插入页面。Vue 让你描述“数据应该如何显示”；当数据变化，Vue 自动更新页面。

```text
数据变化 → Vue 自动更新 DOM → 页面变化
```

## 2. Vite 是什么

Vite 是前端开发工具。它负责启动本地开发服务器、加载 `.vue` 文件、热更新页面和构建生产文件。Vue 写成单文件组件（`.vue`）时，不能直接双击 HTML 文件运行，必须通过 Vite。

## 3. App.vue 的三个部分

`App.vue` 现在只有两部分：

```vue
<script setup>
// JavaScript 数据和函数
</script>

<template>
  <!-- 页面 HTML 模板 -->
</template>
```

以后还可以加入 `<style>`，但本项目将样式单独放进 `style.css`，更容易阅读。

## 4. ref：响应式数据

```js
const newTodo = ref("");
const todos = ref([]);
```

`ref` 把数据变为响应式。JavaScript 代码中读取和修改要使用 `.value`，例如 `newTodo.value = ""`；模板中 Vue 会自动处理，所以可以写 `{{ newTodo }}`。

## 5. v-model：双向绑定

```vue
<input v-model="newTodo" />
```

用户输入时，`newTodo` 自动更新；代码修改 `newTodo` 时，输入框也自动更新。这是双向绑定。

复选框同理：

```vue
<input v-model="todo.completed" type="checkbox" />
```

勾选状态和 `todo.completed` 自动同步。

## 6. v-for、key 和插值

```vue
<li v-for="todo in filteredTodos" :key="todo.id">
  <span>{{ todo.title }}</span>
</li>
```

`v-for` 遍历数组并重复创建元素；`{{ }}` 显示数据；`:key` 给每一项稳定身份，帮助 Vue 正确更新列表。

## 7. 事件

```vue
<form @submit.prevent="addTodo">
<button @click="removeTodo(todo.id)">删除</button>
```

`@submit`、`@click` 是事件绑定。`.prevent` 等于原生 JavaScript 的 `event.preventDefault()`，阻止表单刷新页面。

## 8. computed 和筛选

`computed` 表示由已有响应式数据计算出的新结果：

```js
const remainingCount = computed(() => todos.value.filter((todo) => !todo.completed).length);
```

任务完成状态变动时，Vue 会重新计算剩余数量。`filteredTodos` 根据筛选条件返回要显示的列表。

## 9. watch 和 localStorage

```js
watch(todos, (latestTodos) => {
  localStorage.setItem("vue-learning-todos", JSON.stringify(latestTodos));
}, { deep: true });
```

`watch` 监听数据变化。`deep: true` 很重要，因为勾选复选框是修改数组中某一个对象的 `completed` 属性；没有深度监听时，可能无法保存这类内部变化。

## 10. 必须理解与暂缓内容

必须理解：组件、`ref`、模板、`v-model`、`v-for`、`v-if`、事件、`computed`、`watch`。

暂缓：`reactive` 的复杂用法、生命周期、组件通信、Router、Pinia、TypeScript。它们会在后续 Demo 中逐步学习。

## 11. 自测题

1. Vue 和原生 JavaScript Todo 在“更新页面”上的最大区别是什么？
2. 为什么 JavaScript 代码中 `todos.value`，模板中却能写 `todos`？
3. `v-model` 怎样让输入框与变量同步？
4. `v-for` 中为什么需要 `:key`？
5. `@submit.prevent` 替代了原生 JavaScript 的哪一行？
6. 为什么本 Demo 的 `watch` 需要 `{ deep: true }`？
