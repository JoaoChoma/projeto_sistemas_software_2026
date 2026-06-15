Cliente Web
HTML + JavaScript
        |
        | WebSocket
        v
Servidor Node.js
WebSocket Server
        |
        | setInterval()
        v
Envia avisos periódicos ao cliente

websocket para aplicar a técnica pomodoro

exemplo-websocket/
├── package.json
├── server.js
└── public/
    └── index.html


npm init -y
npm install express ws


executar o projeto

node server.js

http://localhost:3000


Cliente conecta ao servidor
        |
        v
Cliente envia:
{
  "tipo": "INICIAR_POMODORO",
  "minutos": 25
}
        |
        v
Servidor cria um temporizador
        |
        v
A cada 25 minutos, servidor envia:
{
  "tipo": "AVISO_POMODORO",
  "mensagem": "Tempo concluído."
}
        |
        v
Cliente recebe e exibe na tela




https