# SEMANA16 - Frontend + Backend + PostgreSQL com Docker Compose

Projeto exemplo com uma API REST em Spring Boot para CRUD de pedidos, um banco PostgreSQL e um frontend web que consome essa API.

## Estrutura

- `backend/`: API REST em Spring Boot.
- `frontend/`: interface web servida por Nginx.
- `docker-compose.yml`: orquestra frontend, backend e PostgreSQL.

## Como executar

```bash
docker compose up --build
```

Depois acesse:

- Frontend: http://localhost:8080
- Backend: http://localhost:8081/pedidos
- PostgreSQL: `localhost:5432`

Credenciais do banco:

- Database: `semana16`
- Usuario: `semana16`
- Senha: `semana16`

## Rotas da API

- `GET /pedidos`: lista pedidos.
- `GET /pedidos/:id`: busca pedido por id.
- `POST /pedidos`: cria pedido.
- `PUT /pedidos/:id`: atualiza pedido.
- `DELETE /pedidos/:id`: remove pedido.

Exemplo de corpo para criar ou atualizar:

```json
{
  "cliente": "Maria",
  "produto": "Notebook",
  "quantidade": 1,
  "valor": 3500,
  "status": "pendente"
}
```

Valores aceitos para `status`: `pendente`, `pago`, `enviado`, `cancelado`.
