const express = require("express");
const http = require("http");
const WebSocket = require("ws");

const app = express();
const porta = 3000;

// Servir arquivos da pasta public
app.use(express.static("public"));

// Criar servidor HTTP
const servidorHttp = http.createServer(app);

// Criar servidor WebSocket usando o mesmo servidor HTTP
const servidorWebSocket = new WebSocket.Server({ server: servidorHttp });

// Guardar os temporizadores de cada cliente conectado
const temporizadores = new Map();

servidorWebSocket.on("connection", (socket) => {
  console.log("Cliente conectado.");

  socket.send(JSON.stringify({
    tipo: "CONEXAO",
    mensagem: "Conectado ao servidor Pomodoro WebSocket."
  }));

  socket.on("message", (dados) => {
    const mensagem = JSON.parse(dados.toString());

    if (mensagem.tipo === "INICIAR_POMODORO") {
      iniciarPomodoro(socket, mensagem.minutos);
    }

    if (mensagem.tipo === "PARAR_POMODORO") {
      pararPomodoro(socket);
    }
  });

  socket.on("close", () => {
    console.log("Cliente desconectado.");
    pararPomodoro(socket);
  });
});

function iniciarPomodoro(socket, minutos) {
  pararPomodoro(socket);

  const minutosNumerico = Number(minutos);

  if (!minutosNumerico || minutosNumerico <= 0) {
    socket.send(JSON.stringify({
      tipo: "ERRO",
      mensagem: "Informe uma quantidade de minutos válida."
    }));

    return;
  }

  const intervaloEmMilissegundos = minutosNumerico * 60 * 100;

  socket.send(JSON.stringify({
    tipo: "POMODORO_INICIADO",
    mensagem: `Pomodoro iniciado. Você será avisado a cada ${minutosNumerico} minuto(s).`
  }));

  const temporizador = setInterval(() => {
    if (socket.readyState === WebSocket.OPEN) {
      socket.send(JSON.stringify({
        tipo: "AVISO_POMODORO",
        mensagem: `Tempo concluído: ${minutosNumerico} minuto(s) se passaram.`,
        dataHora: new Date().toLocaleString("pt-BR")
      }));
    }
  }, intervaloEmMilissegundos);

  temporizadores.set(socket, temporizador);
}

function pararPomodoro(socket) {
  const temporizador = temporizadores.get(socket);

  if (temporizador) {
    clearInterval(temporizador);
    temporizadores.delete(socket);

    if (socket.readyState === WebSocket.OPEN) {
      socket.send(JSON.stringify({
        tipo: "POMODORO_PARADO",
        mensagem: "Pomodoro interrompido."
      }));
    }
  }
}

servidorHttp.listen(porta, () => {
  console.log(`Servidor rodando em http://localhost:${porta}`);
});