const express = require("express");

const app = express();
const porta = 3000;

// Permite receber JSON no corpo das requisições
app.use(express.json());

// Permite servir arquivos HTML, CSS e JS da pasta public
app.use(express.static("public"));

// Simulando um banco de dados em memória
let produtos = [
  { id: 1, nome: "Notebook", preco: 3500 },
  { id: 2, nome: "Mouse", preco: 80 },
  { id: 3, nome: "Teclado", preco: 150 }
];

let proximoId = 4;

// GET - Listar todos os produtos
app.get("/produtos", (req, res) => {
  res.json(produtos);
});

// GET - Buscar um produto pelo ID
app.get("/produtos/:id", (req, res) => {
  const id = Number(req.params.id);

  const produto = produtos.find((p) => p.id === id);

  if (!produto) {
    return res.status(404).json({ mensagem: "Produto não encontrado." });
  }

  res.json(produto);
});

// POST - Cadastrar um novo produto
app.post("/produtos", (req, res) => {
  const { nome, preco } = req.body;

  if (!nome || preco === undefined) {
    return res.status(400).json({
      mensagem: "Nome e preço são obrigatórios."
    });
  }

  const novoProduto = {
    id: proximoId,
    nome,
    preco: Number(preco)
  };

  produtos.push(novoProduto);
  proximoId++;

  res.status(201).json(novoProduto);
});

// PUT - Atualizar um produto existente
app.put("/produtos/:id", (req, res) => {
  const id = Number(req.params.id);

  const { nome, preco } = req.body;

  const produto = produtos.find((p) => p.id === id);

  if (!produto) {
    return res.status(404).json({ mensagem: "Produto não encontrado." });
  }

  if (!nome || preco === undefined) {
    return res.status(400).json({
      mensagem: "Nome e preço são obrigatórios."
    });
  }

  produto.nome = nome;
  produto.preco = Number(preco);

  res.json(produto);
});

// DELETE - Excluir um produto
app.delete("/produtos/:id", (req, res) => {
  const id = Number(req.params.id);

  const produtoExiste = produtos.some((p) => p.id === id);

  if (!produtoExiste) {
    return res.status(404).json({ mensagem: "Produto não encontrado." });
  }

  produtos = produtos.filter((p) => p.id !== id);

  res.status(204).send();
});

app.listen(porta, () => {
  console.log(`Servidor rodando em http://localhost:${porta}`);
});