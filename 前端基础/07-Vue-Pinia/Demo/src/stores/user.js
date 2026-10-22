import { defineStore } from "pinia";

export const useUserStore = defineStore("user", {
  state: () => ({
    name: localStorage.getItem("pinia-demo-name") || "",
    loginTime: localStorage.getItem("pinia-demo-login-time") || "",
  }),
  getters: {
    isLoggedIn: (state) => Boolean(state.name),
    greeting: (state) => (state.name ? `${state.name}，你好` : "你好"),
  },
  actions: {
    login(name) {
      this.name = name;
      this.loginTime = new Date().toLocaleString("zh-CN");
      localStorage.setItem("pinia-demo-name", this.name);
      localStorage.setItem("pinia-demo-login-time", this.loginTime);
    },
    logout() {
      this.name = "";
      this.loginTime = "";
      localStorage.removeItem("pinia-demo-name");
      localStorage.removeItem("pinia-demo-login-time");
    },
  },
});
