# Vue Demo 9：Element Plus 后台管理页

## 运行

```powershell
cd "$env:USERPROFILE\Desktop\09-ElementPlus-后台Demo"
npm.cmd install
npm.cmd run dev
```

## 操作

先在另一个窗口运行桌面 `14-SpringBoot-JWT-Demo` 后端，使用已有账号登录，或在页面注册。前端默认 5173，后端 8084，`vite.config.js` 将 `/api` 请求转发到后端。

任务现已保存到 MySQL；新账号看到空列表是正常的。可以新建、编辑名称/分类/优先级、改状态、删除、按名称/分类/状态组合筛选，并查看后端统计。任务按登录账号隔离。

前端构建命令：`npm.cmd run build`。当前仍为开发联调，不能把 Vite 开发代理当作生产部署。

1. 在任务名称中搜索。
2. 点击“新建任务”，填写名称、分类和优先级，创建任务。
3. 点击删除，观察确认弹窗；分别测试确认和取消。
4. 缩小浏览器，观察页面的基础响应式效果。

## 关键组件

- `el-container`、`el-aside`、`el-header`、`el-main`：后台页面布局。
- `el-table`、`el-table-column`：表格和列。
- `el-dialog`：弹窗。
- `el-form`、`el-input`、`el-select`、`el-radio`：表单控件。
- `ElMessage`、`ElMessageBox`：成功提示和确认弹窗。

## 学习重点

组件库提供统一样式和交互。你要理解组件的属性（props）、事件（events）和插槽（slots），而不是背所有组件名称。
