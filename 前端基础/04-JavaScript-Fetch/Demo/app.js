const form = document.querySelector("#search-form");
const input = document.querySelector("#user-id");
const status = document.querySelector("#status");
const userCard = document.querySelector("#user-card");

form.addEventListener("submit", async (event) => {
  event.preventDefault();
  const userId = input.value;

  status.className = "status";
  status.textContent = "正在查询...";
  userCard.hidden = true;

  try {
    const response = await fetch(`https://jsonplaceholder.typicode.com/users/${userId}`);
    if (!response.ok) {
      throw new Error("找不到这个用户");
    }

    const user = await response.json();
    userCard.innerHTML = `
      <h3>${user.name}</h3>
      <p><strong>用户名：</strong>${user.username}</p>
      <p><strong>邮箱：</strong>${user.email}</p>
      <p><strong>公司：</strong>${user.company.name}</p>
      <p><strong>城市：</strong>${user.address.city}</p>
    `;
    userCard.hidden = false;
    status.textContent = "查询成功";
  } catch (error) {
    status.className = "status error";
    status.textContent = `查询失败：${error.message}`;
  }
});
