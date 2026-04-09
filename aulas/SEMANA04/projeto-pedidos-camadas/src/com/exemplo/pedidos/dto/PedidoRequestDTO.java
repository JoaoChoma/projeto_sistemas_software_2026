package com.exemplo.pedidos.dto;

import java.util.List;

public class PedidoRequestDTO {
    private final List<ItemPedidoRequestDTO> itens;
    private final boolean clienteVip;

    public PedidoRequestDTO(List<ItemPedidoRequestDTO> itens, boolean clienteVip) {
        this.itens = itens;
        this.clienteVip = clienteVip;
    }

    public List<ItemPedidoRequestDTO> getItens() {
        return itens;
    }

    public boolean isClienteVip() {
        return clienteVip;
    }
}
