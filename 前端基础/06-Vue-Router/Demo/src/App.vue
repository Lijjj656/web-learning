<script setup>
import { useRoute, useRouter } from "vue-router";

const route = useRoute();
const router = useRouter();

function logout() {
  localStorage.removeItem("router-demo-user");
  router.push("/login");
}
</script>

<template>
  <div class="app-shell">
    <header v-if="route.path !== '/login'" class="topbar">
      <RouterLink class="logo" to="/home">学习空间</RouterLink>
      <nav aria-label="主导航">
        <RouterLink to="/home">首页</RouterLink>
        <button type="button" @click="logout">退出登录</button>
      </nav>
    </header>
    <RouterView />
  </div>
</template>
