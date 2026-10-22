<script setup>
import { useRouter } from "vue-router";
import { useUserStore } from "./stores/user";

const router = useRouter();
const userStore = useUserStore();

function logout() {
  userStore.logout();
  router.push("/login");
}
</script>

<template>
  <header v-if="userStore.isLoggedIn" class="topbar">
    <RouterLink to="/dashboard" class="logo">学习空间</RouterLink>
    <div class="user-area"><span>{{ userStore.greeting }}</span><button type="button" @click="logout">退出登录</button></div>
  </header>
  <RouterView />
</template>
