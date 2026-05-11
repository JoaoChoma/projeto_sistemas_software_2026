# Projeto

## api-fetch-http

api-fetch-http/
├── package.json
├── server.js
└── public/
    └── index.html

---

#. O que é `fetch`?

O `fetch` é uma função nativa do JavaScript usada para realizar requisições HTTP.

## Exemplo simples

```javascript
fetch("http://localhost:3000/produtos")
  .then(resposta => resposta.json())
  .then(dados => console.log(dados));
````

---

## Servidor

npm init -y
npm install express

### Executar o servidor

node server.js

### Acessar via cliente (navegador)

http://localhost:3000


---
````
Navegador
HTML + JavaScript + fetch
        |
        | HTTP
        v
Servidor Node.js / Express
API de Produtos
````

# Operações HTTP da API de Produtos

| Operação | Verbo HTTP | Rota | Função no código |
|---|---|---|---|
| Listar produtos | GET | `/produtos` | `listarProdutos()` |
| Buscar produto | GET | `/produtos/:id` | `buscarProdutoPorId()` |
| Cadastrar produto | POST | `/produtos` | `cadastrarProduto()` |
| Atualizar produto | PUT | `/produtos/:id` | `atualizarProduto()` |
| Excluir produto | DELETE | `/produtos/:id` | `excluirProduto()` |


---

# Comandos para testar a API no Postman

Servidor da API:

````
http://localhost:3000
````

````
Method: GET
URL: http://localhost:3000/produtos
Body: não precisa


Method: GET
URL: http://localhost:3000/produtos/1
Body: não precisa


Method: POST
URL: http://localhost:3000/produtos
Headers:
Content-Type: application/json
Body: raw / JSON

{
  "nome": "Monitor",
  "preco": 900
}

Method: PUT
URL: http://localhost:3000/produtos/4
Headers:
Content-Type: application/json
Body: raw / JSON

{
  "nome": "Monitor Gamer",
  "preco": 1200
}


Method: PUT
URL: http://localhost:3000/produtos/999
Headers:
Content-Type: application/json
Body: raw / JSON

{
  "nome": "Produto Teste",
  "preco": 100
}


Method: DELETE
URL: http://localhost:3000/produtos/4
Body: não precisa

````