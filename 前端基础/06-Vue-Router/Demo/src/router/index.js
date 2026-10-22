import { createRouter, createWebHashHistory } from "vue-router";
import DetailView from "../views/DetailView.vue";
import HomeView from "../views/HomeView.vue";
import LoginView from "../views/LoginView.vue";

const router = createRouter({
  history: createWebHashHistory(),
  routes: [
    { path: "/", redirect: "/login" },
    { path: "/login", name: "login", component: LoginView },
    { path: "/home", name: "home", component: HomeView },
    { path: "/detail/:id", name: "detail", component: DetailView },
  ],
});

export default router;
