<script setup>
import { computed, onMounted, ref } from "vue";
import { SwitchButton } from "@element-plus/icons-vue";
import { ElMessage, ElMessageBox } from "element-plus";

const sessionKey = "studyJwtToken";
const token = ref(sessionStorage.getItem(sessionKey) || "");
const currentUser = ref(null);
const restoringSession = ref(Boolean(token.value));
const authBusy = ref(false);
const authError = ref("");
const authMode = ref("login");
const credentials = ref({ name: "", email: "", password: "", confirmPassword: "" });

const dialogVisible = ref(false);
const keyword = ref("");
const filterCategory = ref("");
const filterStatus = ref("");
const tasksLoading = ref(false);
const taskBusy = ref(false);
const editingTaskId = ref(null);
const busyTaskIds = ref(new Set());
const newTask = ref({ title: "", category: "Web", priority: "普通" });
const tasks = ref([]);
const stats = ref({ total: 0, completed: 0, inProgress: 0, notStarted: 0 });

const activePage = ref("1");
const displayName = ref(localStorage.getItem("studyDisplayName") || "郭家乐");
const filteredTasks = computed(() => tasks.value.filter((task) => {
  const matchesKeyword = task.title.includes(keyword.value.trim());
  const matchesCategory = !filterCategory.value || task.category === filterCategory.value;
  const matchesStatus = !filterStatus.value || task.status === filterStatus.value;
  return matchesKeyword && matchesCategory && matchesStatus;
}));
const completedCount = computed(() => stats.value.completed);
const pendingCount = computed(() => stats.value.total - stats.value.completed);
const pageTitle = computed(() => ({ "1": "任务管理", "2": "学习统计", "3": "个人设置" })[activePage.value]);

async function readJson(response, unauthorizedMessage) {
  const data = await response.json().catch(() => null);
  if (!response.ok) {
    if (response.status === 401) throw new Error(unauthorizedMessage);
    if (response.status === 409) throw new Error("这个邮箱已注册，请直接登录或更换邮箱");
    if (response.status === 400) throw new Error("请求内容有误，请检查输入");
    if (response.status >= 500) throw new Error("服务出错，请查看 Spring Boot 窗口的日志");
    throw new Error(data?.message || "请求失败，请稍后再试");
  }
  if (!data) throw new Error("服务器返回的数据格式不正确");
  return data;
}

async function getProfile(accessToken) {
  const response = await fetch("/api/auth/me", {
    headers: { Authorization: `Bearer ${accessToken}` },
  });
  const profile = await readJson(response, "登录已失效，请重新登录");
  if (!profile.email) throw new Error("用户信息格式不正确");
  return profile;
}

async function apiRequest(path, options = {}) {
  let response;
  try {
    response = await fetch(path, {
    ...options,
    headers: {
      ...(options.body ? { "Content-Type": "application/json" } : {}),
      Authorization: `Bearer ${token.value}`,
      ...(options.headers || {}),
    },
    });
  } catch {
    throw new Error("无法连接后端，请确认 Spring Boot 正在运行");
  }
  if (response.status === 401) {
    signOut();
    authError.value = "登录已失效，请重新登录";
  }
  if (response.status === 204) return null;
  return readJson(response, "登录已失效，请重新登录");
}

async function loadTasks() {
  tasksLoading.value = true;
  try {
    tasks.value = await apiRequest("/api/tasks");
  } catch (error) {
    if (error.message.includes("登录已失效")) signOut();
    else ElMessage.error(error.message);
  } finally {
    tasksLoading.value = false;
  }
}

async function loadStats() {
  try {
    stats.value = await apiRequest("/api/tasks/stats");
  } catch (error) {
    if (error.message.includes("登录已失效")) signOut();
    else ElMessage.error(error.message);
  }
}

async function loadTaskData() {
  await Promise.all([loadTasks(), loadStats()]);
}

function switchAuthMode() {
  authError.value = "";
  credentials.value.password = "";
  credentials.value.confirmPassword = "";
}

async function submitAuth() {
  authError.value = "";
  const registering = authMode.value === "register";
  const name = credentials.value.name.trim();
  const email = credentials.value.email.trim();
  const password = credentials.value.password;
  if (!email || !password || (registering && !name)) {
    authError.value = registering ? "请填写姓名、邮箱和密码" : "请输入邮箱和密码";
    return;
  }
  if (registering && password !== credentials.value.confirmPassword) {
    authError.value = "两次输入的密码不一致";
    return;
  }

  authBusy.value = true;
  try {
    const response = await fetch(`/api/auth/${registering ? "register" : "login"}`, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(registering ? { name, email, password } : { email, password }),
    });
    const result = await readJson(response, "邮箱或密码错误");
    if (typeof result.token !== "string" || !result.token) throw new Error("响应缺少 token");
    const profile = await getProfile(result.token);
    sessionStorage.setItem(sessionKey, result.token);
    token.value = result.token;
    currentUser.value = profile;
    await loadTaskData();
    credentials.value.password = "";
    credentials.value.confirmPassword = "";
    if (registering) ElMessage.success("注册成功");
  } catch (error) {
    authError.value = error instanceof TypeError ? "无法连接服务，请确认前后端都在运行" : error instanceof Error ? error.message : "操作失败，请稍后再试";
  } finally {
    authBusy.value = false;
  }
}

function signOut() {
  sessionStorage.removeItem(sessionKey);
  token.value = "";
  currentUser.value = null;
  credentials.value.password = "";
  credentials.value.confirmPassword = "";
  authMode.value = "login";
  activePage.value = "1";
  tasks.value = [];
  stats.value = { total: 0, completed: 0, inProgress: 0, notStarted: 0 };
  keyword.value = "";
  filterCategory.value = "";
  filterStatus.value = "";
  dialogVisible.value = false;
}

onMounted(async () => {
  if (!token.value) return;
  try {
    currentUser.value = await getProfile(token.value);
    await loadTaskData();
  } catch {
    signOut();
    authError.value = "会话已失效或服务暂时不可用，请重新登录";
  } finally {
    restoringSession.value = false;
  }
});

function selectPage(index) {
  activePage.value = index;
}

function saveDisplayName() {
  const name = displayName.value.trim();
  if (!name) {
    ElMessage.warning("请输入显示名称");
    return;
  }
  displayName.value = name;
  localStorage.setItem("studyDisplayName", name);
  ElMessage.success("设置已保存");
}

function openCreateDialog() {
  editingTaskId.value = null;
  newTask.value = { title: "", category: "Web", priority: "普通", status: "未开始" };
  dialogVisible.value = true;
}

function openEditDialog(task) {
  editingTaskId.value = task.id;
  newTask.value = { title: task.title, category: task.category, priority: task.priority, status: task.status };
  dialogVisible.value = true;
}

async function createTask() {
  if (taskBusy.value) return;
  if (!newTask.value.title.trim()) {
    ElMessage.warning("请输入任务名称");
    return;
  }
  taskBusy.value = true;
  const editing = editingTaskId.value !== null;
  try {
    await apiRequest(editing ? `/api/tasks/${editingTaskId.value}` : "/api/tasks", {
      method: editing ? "PUT" : "POST",
      body: JSON.stringify({ ...newTask.value, title: newTask.value.title.trim() }),
    });
    dialogVisible.value = false;
    await loadTaskData();
    ElMessage.success(editing ? "任务修改成功" : "任务创建成功");
  } catch (error) {
    ElMessage.error(error.message);
  } finally {
    taskBusy.value = false;
  }
}

async function updateTask(task) {
  if (busyTaskIds.value.has(task.id)) return;
  busyTaskIds.value.add(task.id);
  try {
    await apiRequest(`/api/tasks/${task.id}`, {
      method: "PUT",
      body: JSON.stringify({
        title: task.title,
        category: task.category,
        priority: task.priority,
        status: task.status,
      }),
    });
    await loadStats();
    ElMessage.success("任务已保存");
  } catch (error) {
    ElMessage.error(error.message);
    if (token.value) await loadTaskData();
  } finally {
    busyTaskIds.value.delete(task.id);
  }
}

async function removeTask(task) {
  if (busyTaskIds.value.has(task.id)) return;
  try {
    await ElMessageBox.confirm(`确定删除“${task.title}”吗？`, "删除确认", { type: "warning" });
  } catch {
    return;
  }
  busyTaskIds.value.add(task.id);
  try {
    await apiRequest(`/api/tasks/${task.id}`, { method: "DELETE" });
    await loadTaskData();
    ElMessage.success("任务已删除");
  } catch (error) {
    ElMessage.error(error.message);
  } finally {
    busyTaskIds.value.delete(task.id);
  }
}
</script>

<template>
  <main v-if="restoringSession" class="auth-screen">
    <el-card class="auth-panel" shadow="never"><el-skeleton :rows="3" animated /></el-card>
  </main>
  <main v-else-if="!currentUser" class="auth-screen">
    <el-card class="auth-panel" shadow="never">
      <h1 class="auth-title">学习空间</h1>
      <el-tabs v-model="authMode" stretch @tab-change="switchAuthMode">
        <el-tab-pane label="登录" name="login" />
        <el-tab-pane label="注册" name="register" />
      </el-tabs>
      <el-alert v-if="authError" class="auth-error" :title="authError" type="error" show-icon :closable="false" />
      <el-form label-position="top" @submit.prevent="submitAuth">
        <el-form-item v-if="authMode === 'register'" label="姓名">
          <el-input v-model="credentials.name" autocomplete="name" placeholder="请输入姓名" />
        </el-form-item>
        <el-form-item label="邮箱">
          <el-input v-model="credentials.email" type="email" autocomplete="username" placeholder="请输入邮箱" />
        </el-form-item>
        <el-form-item label="密码">
          <el-input v-model="credentials.password" type="password" :autocomplete="authMode === 'register' ? 'new-password' : 'current-password'" show-password placeholder="请输入密码" />
        </el-form-item>
        <el-form-item v-if="authMode === 'register'" label="确认密码">
          <el-input v-model="credentials.confirmPassword" type="password" autocomplete="new-password" show-password placeholder="请再次输入密码" />
        </el-form-item>
        <el-button class="auth-submit" type="primary" native-type="submit" :loading="authBusy">{{ authMode === 'register' ? '注册并进入' : '登录' }}</el-button>
      </el-form>
    </el-card>
  </main>
  <el-container v-else class="layout">
    <el-aside width="220px" class="aside">
      <div class="brand">学习空间</div>
      <el-menu :default-active="activePage" @select="selectPage">
        <el-menu-item index="1">任务管理</el-menu-item>
        <el-menu-item index="2">学习统计</el-menu-item>
        <el-menu-item index="3">个人设置</el-menu-item>
      </el-menu>
    </el-aside>
    <el-container>
      <el-header class="header">
        <div>
          <strong>{{ pageTitle }}</strong>
          <span v-if="activePage === '1'" class="subtitle">记录每天的学习进度</span>
        </div>
        <div class="header-actions">
          <el-button v-if="activePage === '1'" type="primary" @click="openCreateDialog">新建任务</el-button>
          <span class="account-name">{{ currentUser.name }}</span>
          <el-tooltip content="退出登录" placement="bottom">
            <el-button :icon="SwitchButton" circle aria-label="退出登录" @click="signOut" />
          </el-tooltip>
        </div>
      </el-header>
      <el-main v-if="activePage === '1'" class="main">
        <el-card shadow="never">
          <div class="toolbar">
            <el-input v-model="keyword" clearable placeholder="搜索任务名称" class="search" />
            <el-select v-model="filterCategory" clearable placeholder="分类" class="filter-select">
              <el-option label="Web" value="Web" />
              <el-option label="Java" value="Java" />
              <el-option label="AI" value="AI" />
            </el-select>
            <el-select v-model="filterStatus" clearable placeholder="状态" class="filter-select">
              <el-option label="未开始" value="未开始" />
              <el-option label="进行中" value="进行中" />
              <el-option label="已完成" value="已完成" />
            </el-select>
            <el-tag type="info">共 {{ filteredTasks.length }} 项</el-tag>
          </div>
          <el-table v-loading="tasksLoading" :data="filteredTasks" stripe>
            <el-table-column prop="title" label="任务名称" min-width="280" />
            <el-table-column prop="category" label="分类" width="120" />
            <el-table-column prop="priority" label="优先级" width="120">
              <template #default="scope">
                <el-tag :type="scope.row.priority === '高' ? 'danger' : 'info'">{{ scope.row.priority }}</el-tag>
              </template>
            </el-table-column>
            <el-table-column label="状态" width="140">
              <template #default="scope">
                <el-select v-model="scope.row.status" :disabled="busyTaskIds.has(scope.row.id)" size="small" aria-label="任务状态" @change="updateTask(scope.row)">
                  <el-option label="未开始" value="未开始" />
                  <el-option label="进行中" value="进行中" />
                  <el-option label="已完成" value="已完成" />
                </el-select>
              </template>
            </el-table-column>
            <el-table-column label="操作" width="150">
              <template #default="scope">
                <el-button link type="primary" :disabled="busyTaskIds.has(scope.row.id)" @click="openEditDialog(scope.row)">编辑</el-button>
                <el-button link type="danger" :disabled="busyTaskIds.has(scope.row.id)" @click="removeTask(scope.row)">删除</el-button>
              </template>
            </el-table-column>
          </el-table>
          <el-empty v-if="!filteredTasks.length" description="没有匹配任务" />
        </el-card>
      </el-main>
      <el-main v-else-if="activePage === '2'" class="main">
        <el-row :gutter="20" class="stats-row">
          <el-col :xs="24" :sm="8"><el-card shadow="never"><el-statistic title="任务总数" :value="stats.total" /></el-card></el-col>
          <el-col :xs="24" :sm="8"><el-card shadow="never"><el-statistic title="已完成" :value="completedCount" /></el-card></el-col>
          <el-col :xs="24" :sm="8"><el-card shadow="never"><el-statistic title="待完成" :value="pendingCount" /></el-card></el-col>
        </el-row>
      </el-main>
      <el-main v-else class="main">
        <el-card shadow="never">
          <el-descriptions title="账号信息" :column="1">
            <el-descriptions-item label="姓名">{{ currentUser.name }}</el-descriptions-item>
            <el-descriptions-item label="邮箱">{{ currentUser.email }}</el-descriptions-item>
          </el-descriptions>
          <el-divider />
          <el-form label-position="top" style="max-width: 420px">
            <el-form-item label="本机名称">
              <el-input v-model="displayName" maxlength="30" show-word-limit />
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="saveDisplayName">保存</el-button>
            </el-form-item>
          </el-form>
        </el-card>
      </el-main>
    </el-container>
  </el-container>

  <el-dialog v-model="dialogVisible" :title="editingTaskId === null ? '新建任务' : '编辑任务'" width="min(460px, 90vw)" :close-on-click-modal="!taskBusy" :close-on-press-escape="!taskBusy" :show-close="!taskBusy">
    <el-form label-width="80px" :disabled="taskBusy" @submit.prevent="createTask">
      <el-form-item label="任务名称"><el-input v-model="newTask.title" maxlength="200" show-word-limit placeholder="例如：复习 Java Stream" /></el-form-item>
      <el-form-item label="分类"><el-select v-model="newTask.category"><el-option label="Web" value="Web" /><el-option label="Java" value="Java" /><el-option label="AI" value="AI" /></el-select></el-form-item>
      <el-form-item label="优先级"><el-radio-group v-model="newTask.priority"><el-radio value="普通">普通</el-radio><el-radio value="高">高</el-radio></el-radio-group></el-form-item>
      <el-form-item v-if="editingTaskId !== null" label="状态"><el-select v-model="newTask.status"><el-option label="未开始" value="未开始" /><el-option label="进行中" value="进行中" /><el-option label="已完成" value="已完成" /></el-select></el-form-item>
    </el-form>
    <template #footer><el-button :disabled="taskBusy" @click="dialogVisible = false">取消</el-button><el-button type="primary" :loading="taskBusy" @click="createTask">{{ editingTaskId === null ? '创建' : '保存' }}</el-button></template>
  </el-dialog>
</template>

<style scoped>
.auth-screen {
  min-height: 100vh;
  display: grid;
  place-items: center;
  box-sizing: border-box;
  padding: 24px;
  background: #f5f7fa;
}

.auth-panel { width: min(100%, 420px); }
.auth-title { margin: 0 0 24px; font-size: 22px; }
.auth-error { margin-bottom: 18px; }
.auth-submit { width: 100%; }
.header-actions { display: flex; align-items: center; gap: 12px; }
.account-name { max-width: 140px; overflow: hidden; text-overflow: ellipsis; white-space: nowrap; color: #667085; font-size: 14px; }
.filter-select { width: 120px; }
.stats-row .el-col { margin-bottom: 16px; }

@media (max-width: 700px) {
  .header { height: auto; min-height: 60px; flex-wrap: wrap; gap: 10px; padding: 10px 12px; }
  .header-actions { width: 100%; justify-content: space-between; }
  .account-name { display: none; }
  .toolbar { flex-wrap: wrap; gap: 8px; }
  .filter-select { width: 110px; }
}
</style>
