package com.exemplo.pedidos.controller;

import com.exemplo.pedidos.dto.PedidoRequestDTO;
import com.exemplo.pedidos.dto.PedidoResponseDTO;
import com.exemplo.pedidos.service.PedidoService;

public class PedidoController {
    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    public PedidoResponseDTO registrarPedido(PedidoRequestDTO request) {
        return pedidoService.registrarPedido(request);
    }
}
