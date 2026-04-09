package com.exemplo.pedidos.dto;

public class ItemPedidoRequestDTO {
    private final int codigoProduto;
    private final int quantidade;

    public ItemPedidoRequestDTO(int codigoProduto, int quantidade) {
        this.codigoProduto = codigoProduto;
        this.quantidade = quantidade;
    }

    public int getCodigoProduto() {
        return codigoProduto;
    }

    public int getQuantidade() {
        return quantidade;
    }
}
