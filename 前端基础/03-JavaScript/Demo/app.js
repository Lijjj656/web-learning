const form = document.querySelector("#todo-form");
const input = document.querySelector("#todo-input");
const list = document.querySelector("#todo-list");
const count = document.querySelector("#task-count");
const emptyState = document.querySelector("#empty-state");
const clearCompletedButton = document.querySelector("#clear-completed");

let todos = JSON.parse(localStorage.getItem("learning-todos") || "[]");

function saveTodos() {
  localStorage.setItem("learning-todos", JSON.stringify(todos));
}

function renderTodos() {
  list.innerHTML = "";

  todos.forEach((todo) => {
    const item = document.createElement("li");
    item.className = `todo-item${todo.completed ? " completed" : ""}`;
    item.innerHTML = `
      <input type="checkbox" ${todo.completed ? "checked" : ""} aria-label="完成任务" />
      <span class="todo-title"></span>
      <button class="delete-button" type="button">删除</button>
    `;

    item.querySelector(".todo-title").textContent = todo.title;
    item.querySelector("input").addEventListener("change", () => {
      todo.completed = !todo.completed;
      saveTodos();
      renderTodos();
    });
    item.querySelector(".delete-button").addEventListener("click", () => {
      todos = todos.filter((currentTodo) => currentTodo.id !== todo.id);
      saveTodos();
      renderTodos();
    });
    list.append(item);
  });

  const remaining = todos.filter((todo) => !todo.completed).length;
  count.textContent = `${remaining} 项未完成任务`;
  emptyState.hidden = todos.length > 0;
}

form.addEventListener("submit", (event) => {
  event.preventDefault();
  const title = input.value.trim();
  if (!title) return;

  todos.push({ id: Date.now(), title, completed: false });
  saveTodos();
  renderTodos();
  form.reset();
  input.focus();
});

clearCompletedButton.addEventListener("click", () => {
  todos = todos.filter((todo) => !todo.completed);
  saveTodos();
  renderTodos();
});

renderTodos();
