<script setup>
import { computed } from "vue";

const courses = [
  { id: 1, title: "HTML 页面结构", progress: "已完成", description: "语义化标签、表单和表格" },
  { id: 2, title: "CSS 登录页面", progress: "已完成", description: "Flex、间距和响应式布局" },
  { id: 3, title: "Vue Router", progress: "学习中", description: "登录页、首页和详情页路由" },
];

const userName = computed(() => localStorage.getItem("router-demo-user") || "同学");
</script>

<template>
  <main class="page-content">
    <p class="eyebrow">HOME</p>
    <h1>{{ userName }}，你好</h1>
    <p class="muted">选择一节课程，体验 URL 和页面组件一起切换。</p>
    <section class="course-grid" aria-label="学习课程">
      <article v-for="course in courses" :key="course.id" class="course-card">
        <span class="tag">{{ course.progress }}</span>
        <h2>{{ course.title }}</h2>
        <p>{{ course.description }}</p>
        <RouterLink :to="`/detail/${course.id}`">查看详情</RouterLink>
      </article>
    </section>
  </main>
</template>
