<script setup>
import { computed } from "vue";
import { useRoute } from "vue-router";

const route = useRoute();
const courseMap = {
  1: { title: "HTML 页面结构", content: "HTML 负责结构和内容。页面中使用 header、main、section、form 等语义化标签。" },
  2: { title: "CSS 登录页面", content: "CSS 负责颜色、间距和布局。Flex 让左右两栏或上下布局更容易实现。" },
  3: { title: "Vue Router", content: "Vue Router 根据 URL 匹配组件。RouterLink 改变地址，RouterView 显示匹配的页面。" },
};

const course = computed(() => courseMap[route.params.id]);
</script>

<template>
  <main class="page-content">
    <RouterLink class="back-link" to="/home">返回首页</RouterLink>
    <section v-if="course" class="detail-card">
      <p class="eyebrow">COURSE DETAIL</p>
      <h1>{{ course.title }}</h1>
      <p>{{ course.content }}</p>
      <p class="route-note">当前 URL 参数：{{ route.params.id }}</p>
    </section>
    <section v-else class="detail-card">
      <h1>课程不存在</h1>
      <RouterLink to="/home">返回首页</RouterLink>
    </section>
  </main>
</template>
