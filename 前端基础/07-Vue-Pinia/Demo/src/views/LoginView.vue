<script setup>
import { ref } from "vue";
import { useRouter } from "vue-router";
import { useUserStore } from "../stores/user";

const name = ref("");
const errorMessage = ref("");
const router = useRouter();
const userStore = useUserStore();

function login() {
  const trimmedName = name.value.trim();
  if (!trimmedName) { errorMessage.value = "请输入名字。"; return; }
  userStore.login(trimmedName);
  router.push("/dashboard");
}
</script>

<template>
  <main class="login-page"><section class="card login-card"><p class="eyebrow">PINIA</p><h1>登录学习空间</h1><p class="muted">登录信息会保存到 Pinia，多个组件都能读取。</p><form @submit.prevent="login"><label for="name">名字</label><input id="name" v-model="name" placeholder="例如：小李" /><p v-if="errorMessage" class="error">{{ errorMessage }}</p><button type="submit">登录</button></form></section></main>
</template>
