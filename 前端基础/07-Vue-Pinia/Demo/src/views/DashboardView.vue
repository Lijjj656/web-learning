<script setup>
import { useUserStore } from "../stores/user";

const userStore = useUserStore();
const progress = [
  { title: "HTML", status: "已完成" },
  { title: "CSS", status: "已完成" },
  { title: "JavaScript", status: "已完成" },
  { title: "Vue + Pinia", status: "学习中" },
];
</script>

<template>
  <main class="page"><p class="eyebrow">DASHBOARD</p><h1>{{ userStore.greeting }}</h1><p class="muted">此页和顶部导航读取的是同一个 Pinia 用户状态。</p><section class="card"><h2>登录状态</h2><dl><div><dt>当前用户</dt><dd>{{ userStore.name }}</dd></div><div><dt>登录时间</dt><dd>{{ userStore.loginTime }}</dd></div><div><dt>是否已登录</dt><dd>{{ userStore.isLoggedIn ? "是" : "否" }}</dd></div></dl></section><section class="course-grid"><article v-for="item in progress" :key="item.title" class="card"><h2>{{ item.title }}</h2><p class="tag">{{ item.status }}</p></article></section></main>
</template>
