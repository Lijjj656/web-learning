<script setup>
import { computed, ref, watch } from "vue";

const newTodo = ref("");
const filter = ref("all");
const savedTodos = localStorage.getItem("vue-learning-todos");
const todos = ref(savedTodos ? JSON.parse(savedTodos) : []);

const remainingCount = computed(() => todos.value.filter((todo) => !todo.completed).length);
const filteredTodos = computed(() => {
  if (filter.value === "active") return todos.value.filter((todo) => !todo.completed);
  if (filter.value === "completed") return todos.value.filter((todo) => todo.completed);
  return todos.value;
});

function addTodo() {
  const title = newTodo.value.trim();
  if (!title) return;

  todos.value.push({ id: Date.now(), title, completed: false });
  newTodo.value = "";
}

function removeTodo(id) {
  todos.value = todos.value.filter((todo) => todo.id !== id);
}

function clearCompleted() {
  todos.value = todos.value.filter((todo) => !todo.completed);
}

watch(
  todos,
  (latestTodos) => localStorage.setItem("vue-learning-todos", JSON.stringify(latestTodos)),
  { deep: true },
);
</script>

<template>
  <main class="todo-app">
    <header class="app-header">
      <p class="eyebrow">VUE 3 + VITE</p>
      <h1>我的学习任务</h1>
      <p>Vue 会根据任务数据自动更新页面，不需要手动创建或重新渲染 DOM。</p>
    </header>

    <section class="todo-panel" aria-labelledby="todo-title">
      <h2 id="todo-title">任务清单</h2>
      <form class="add-form" @submit.prevent="addTodo">
        <label class="sr-only" for="new-todo">新任务</label>
        <input id="new-todo" v-model="newTodo" placeholder="例如：学习 Vue 的 ref" autocomplete="off" />
        <button type="submit">添加任务</button>
      </form>

      <div class="toolbar">
        <span>还剩 {{ remainingCount }} 项</span>
        <button class="text-button" type="button" @click="clearCompleted">清除已完成</button>
      </div>

      <div class="filters" aria-label="任务筛选">
        <button :class="{ active: filter === 'all' }" type="button" @click="filter = 'all'">全部</button>
        <button :class="{ active: filter === 'active' }" type="button" @click="filter = 'active'">未完成</button>
        <button :class="{ active: filter === 'completed' }" type="button" @click="filter = 'completed'">已完成</button>
      </div>

      <ul v-if="filteredTodos.length" class="todo-list">
        <li v-for="todo in filteredTodos" :key="todo.id" :class="{ completed: todo.completed }">
          <input v-model="todo.completed" type="checkbox" :aria-label="`完成任务：${todo.title}`" />
          <span>{{ todo.title }}</span>
          <button class="delete-button" type="button" @click="removeTodo(todo.id)">删除</button>
        </li>
      </ul>
      <p v-else class="empty-state">当前没有符合条件的任务。</p>
    </section>
  </main>
</template>
