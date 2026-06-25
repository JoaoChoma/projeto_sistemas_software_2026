const API_URL = "/api/pedidos";

const form = document.querySelector("#pedidoForm");
const pedidoId = document.querySelector("#pedidoId");
const cliente = document.querySelector("#cliente");
const produto = document.querySelector("#produto");
const quantidade = document.querySelector("#quantidade");
const valor = document.querySelector("#valor");
const statusPedido = document.querySelector("#status");
const pedidosBody = document.querySelector("#pedidosBody");
const message = document.querySelector("#message");
const formTitle = document.querySelector("#formTitle");
const submitButton = document.querySelector("#submitButton");
const cancelButton = document.querySelector("#cancelButton");
const reloadButton = document.querySelector("#reloadButton");
const counter = document.querySelector("#counter");

let pedidos = [];

function formatCurrency(value) {
  return Number(value).toLocaleString("pt-BR", {
    style: "currency",
    currency: "BRL"
  });
}

function escapeHtml(value) {
  return String(value)
    .replaceAll("&", "&amp;")
    .replaceAll("<", "&lt;")
    .replaceAll(">", "&gt;")
    .replaceAll('"', "&quot;")
    .replaceAll("'", "&#039;");
}

function showMessage(text) {
  message.textContent = text;
  window.setTimeout(() => {
    message.textContent = "";
  }, 3000);
}

function getFormData() {
  return {
    cliente: cliente.value,
    produto: produto.value,
    quantidade: Number(quantidade.value),
    valor: Number(valor.value),
    status: statusPedido.value
  };
}

function resetForm() {
  form.reset();
  pedidoId.value = "";
  formTitle.textContent = "Novo pedido";
  submitButton.textContent = "Salvar pedido";
  cancelButton.classList.add("hidden");
}

function renderPedidos() {
  counter.textContent = `${pedidos.length} ${pedidos.length === 1 ? "pedido" : "pedidos"}`;

  if (!pedidos.length) {
    pedidosBody.innerHTML = '<tr><td colspan="6">Nenhum pedido cadastrado.</td></tr>';
    return;
  }

  pedidosBody.innerHTML = pedidos
    .map(
      (pedido) => `
        <tr>
          <td>${escapeHtml(pedido.cliente)}</td>
          <td>${escapeHtml(pedido.produto)}</td>
          <td>${pedido.quantidade}</td>
          <td>${formatCurrency(pedido.valor)}</td>
          <td><span class="status status-${escapeHtml(pedido.status)}">${escapeHtml(pedido.status)}</span></td>
          <td>
            <div class="actions">
              <button type="button" class="ghost" data-action="edit" data-id="${pedido.id}">Editar</button>
              <button type="button" class="danger" data-action="delete" data-id="${pedido.id}">Excluir</button>
            </div>
          </td>
        </tr>
      `
    )
    .join("");
}

async function requestJson(url, options = {}) {
  const response = await fetch(url, {
    headers: {
      "Content-Type": "application/json",
      ...options.headers
    },
    ...options
  });

  const text = await response.text();
  const data = text ? JSON.parse(text) : null;

  if (!response.ok) {
    const errorData = data || {};
    const errorMessage = errorData.erro || (errorData.erros || []).join(" ");
    throw new Error(errorMessage || "Nao foi possivel concluir a operacao.");
  }

  return data;
}

async function loadPedidos() {
  pedidosBody.innerHTML = '<tr><td colspan="6">Carregando pedidos...</td></tr>';
  pedidos = await requestJson(API_URL);
  renderPedidos();
}

function startEdit(id) {
  const pedido = pedidos.find((item) => item.id === id);

  if (!pedido) return;

  pedidoId.value = pedido.id;
  cliente.value = pedido.cliente;
  produto.value = pedido.produto;
  quantidade.value = pedido.quantidade;
  valor.value = pedido.valor;
  statusPedido.value = pedido.status;
  formTitle.textContent = `Editando pedido #${pedido.id}`;
  submitButton.textContent = "Atualizar pedido";
  cancelButton.classList.remove("hidden");
}

async function deletePedido(id) {
  const pedido = pedidos.find((item) => item.id === id);
  const confirmed = window.confirm(`Excluir o pedido de ${pedido?.cliente || "cliente"}?`);

  if (!confirmed) return;

  await requestJson(`${API_URL}/${id}`, {
    method: "DELETE"
  });
  showMessage("Pedido removido.");
  resetForm();
  await loadPedidos();
}

form.addEventListener("submit", async (event) => {
  event.preventDefault();

  const id = pedidoId.value;
  const method = id ? "PUT" : "POST";
  const url = id ? `${API_URL}/${id}` : API_URL;

  try {
    await requestJson(url, {
      method,
      body: JSON.stringify(getFormData())
    });

    showMessage(id ? "Pedido atualizado." : "Pedido criado.");
    resetForm();
    await loadPedidos();
  } catch (error) {
    showMessage(error.message);
  }
});

pedidosBody.addEventListener("click", async (event) => {
  const button = event.target.closest("button[data-action]");
  if (!button) return;

  const id = Number(button.dataset.id);

  if (button.dataset.action === "edit") {
    startEdit(id);
  }

  if (button.dataset.action === "delete") {
    try {
      await deletePedido(id);
    } catch (error) {
      showMessage(error.message);
    }
  }
});

cancelButton.addEventListener("click", resetForm);
reloadButton.addEventListener("click", loadPedidos);

loadPedidos().catch((error) => {
  pedidosBody.innerHTML = `<tr><td colspan="6">${error.message}</td></tr>`;
});
