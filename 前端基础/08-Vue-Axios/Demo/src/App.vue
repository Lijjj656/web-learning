<script setup>
import { ref } from "vue";
import http from "./api/http";

const userId = ref(1);
const user = ref(null);
const loading = ref(false);
const errorMessage = ref("");

async function searchUser() {
  const id = Number(userId.value);
  if (!Number.isInteger(id) || id < 1 || id > 10) {
    user.value = null;
    errorMessage.value = "请输入 1 到 10 之间的整数。";
    return;
  }

  loading.value = true;
  errorMessage.value = "";
  user.value = null;

  try {
    const response = await http.get(`/users/${id}`);
    user.value = response.data;
  } catch (error) {
    if (error.code === "ECONNABORTED") {
      errorMessage.value = "请求超时，请检查网络后重试。";
    } else if (error.response?.status === 404) {
      errorMessage.value = "找不到该用户。";
    } else {
      errorMessage.value = "请求失败，请稍后重试。";
    }
  } finally {
    loading.value = false;
  }
}
</script>

<template>
  <main class="page">
    <header><p class="eyebrow">VUE + AXIOS</p><h1>用户信息查询</h1><p class="muted">使用 Axios 请求公开 API，并展示加载、成功和失败状态。</p></header>
    <section class="card" aria-labelledby="search-title">
      <h2 id="search-title">查询用户</h2>
      <form @submit.prevent="searchUser">
        <label for="user-id">用户编号（1 到 10）</label>
        <div class="search-row"><input id="user-id" v-model="userId" type="number" min="1" max="10" /><button :disabled="loading" type="submit">{{ loading ? "查询中..." : "查询" }}</button></div>
      </form>
      <p v-if="loading" class="status">正在向 API 请求数据...</p>
      <p v-else-if="errorMessage" class="status error">{{ errorMessage }}</p>
      <article v-else-if="user" class="user-card">
        <h3>{{ user.name }}</h3>
        <dl><div><dt>用户名</dt><dd>{{ user.username }}</dd></div><div><dt>邮箱</dt><dd>{{ user.email }}</dd></div><div><dt>公司</dt><dd>{{ user.company.name }}</dd></div><div><dt>城市</dt><dd>{{ user.address.city }}</dd></div><div><dt>网站</dt><dd>{{ user.website }}</dd></div></dl>
      </article>
      <p v-else class="status">输入编号后点击查询。</p>
    </section>
  </main>
</template>
