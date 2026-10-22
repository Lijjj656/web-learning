<script setup>
import { ref } from "vue";
import { useRouter } from "vue-router";

const name = ref("");
const errorMessage = ref("");
const router = useRouter();

function login() {
  const trimmedName = name.value.trim();
  if (!trimmedName) {
    errorMessage.value = "请输入你的名字。";
    return;
  }

  localStorage.setItem("router-demo-user", trimmedName);
  router.push("/home");
}
</script>

<template>
  <main class="login-page">
    <section class="login-card" aria-labelledby="login-title">
      <p class="eyebrow">VUE ROUTER</p>
      <h1 id="login-title">进入学习空间</h1>
      <p class="muted">登录后进入首页，再打开不同课程详情页。</p>
      <form @submit.prevent="login">
        <label for="name">你的名字</label>
        <input id="name" v-model="name" placeholder="例如：小李" autocomplete="name" />
        <p v-if="errorMessage" class="error">{{ errorMessage }}</p>
        <button type="submit">登录并进入首页</button>
      </form>
    </section>
  </main>
</template>
