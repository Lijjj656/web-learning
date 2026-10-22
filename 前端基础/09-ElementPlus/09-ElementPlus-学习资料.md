# Vue Demo 9 学习资料：Element Plus

## 1. 组件库解决什么问题

后台系统有大量重复控件：表格、分页、弹窗、表单、下拉框、消息提示。Element Plus 提供经过统一设计和交互处理的 Vue 组件，减少手写 CSS 和边界逻辑的工作量。

## 2. 安装和注册

```js
import ElementPlus from "element-plus";
import "element-plus/dist/index.css";
createApp(App).use(ElementPlus).mount("#app");
```

注册后，模板可以使用 `el-button`、`el-table` 等组件。全量注册适合学习；大型项目可能按需引入减少体积。

## 3. 组件的三种沟通方式

### Props：父组件传给子组件的配置

```vue
<el-button type="primary">新建任务</el-button>
<el-table-column prop="title" label="任务名称" />
```

`type`、`prop`、`label` 都是组件接收的配置。

### Events：组件通知父组件发生了什么

```vue
<el-button @click="openCreateDialog">新建任务</el-button>
```

用户点击按钮后触发 `click`，执行页面函数。

### Slots：自定义组件内部某个区域

```vue
<el-table-column label="操作">
  <template #default="scope">
    <el-button @click="removeTask(scope.row)">删除</el-button>
  </template>
</el-table-column>
```

默认情况下表格列显示字段值；通过 `#default` 插槽，可以自定义这一列的按钮。`scope.row` 是当前行数据。

## 4. 对话框和表单

```vue
<el-dialog v-model="dialogVisible">
```

`v-model` 控制弹窗显示和隐藏。点击取消时设置 `dialogVisible = false`，点击创建时先检查输入，再把任务放进数组。

## 5. 消息和确认框

```js
ElMessage.success("任务创建成功");
await ElMessageBox.confirm("确定删除吗？");
```

`ElMessage` 适合短提示；`ElMessageBox.confirm` 返回 Promise，用户确认后继续执行，取消则进入 `catch`。

## 6. 真实工作中的用途

Element Plus 常用于管理后台、运营平台、内部工具。它不能替代 HTML、CSS 和 Vue 基础：当组件效果不符合需求时，你仍需要理解布局、状态和事件，才能正确调整。

## 当前必须理解

- 组件库减少重复界面代码。
- Props 配置组件，事件响应操作，插槽自定义内部内容。
- `v-model` 可以控制组件状态。
- 表格、弹窗、表单和消息是后台系统的核心组合。

下一步可以进入 Java 后端基础复习，开始搭建 Spring Boot 用户 API。

## 7. 为什么三个菜单原来显示同一页

原版 `App.vue` 的 `<el-menu default-active="1">` 只设置默认高亮。三个菜单项没有把选中的编号传给页面，而标题和任务表也固定写在模板里，所以点击“学习统计”或“个人设置”时，主体内容不会变化。

现在用 `activePage = ref("1")` 保存当前菜单编号。`@select="selectPage"` 在点击菜单时更新它；模板用 `v-if`、`v-else-if`、`v-else` 分别显示任务管理、学习统计和个人设置。这里是同一个 Vue 页面内部切换视图，浏览器地址不变；需要不同网址时才使用 Vue Router。

## 8. 统计和设置的数据从哪里来

- `tasks` 是当前页面的任务数组。`completedCount` 使用 `computed` 从数组中计算已完成数量；新增、删除或修改任务状态后，统计会自动更新。
- `displayName` 是个人设置的输入状态。点击“保存”后写入 `localStorage`，刷新页面还能读回来。
- 任务数组仍是 Demo 的内存数据，刷新会恢复初始三项；它还没有存入后端或 MySQL。`localStorage` 也只属于当前浏览器，不等于数据库。

本节检查：点击三个菜单时，标题和主体内容应分别切换；在任务管理中修改状态后，学习统计的数量应跟着变化；保存显示名称并刷新后，名称应保留。
