package com.exemplo.pedidos.dto;

public class PedidoResponseDTO {
    private final double total;
    private final double totalComDesconto;
    private final String mensagem;

    public PedidoResponseDTO(double total, double totalComDesconto, String mensagem) {
        this.total = total;
        this.totalComDesconto = totalComDesconto;
        this.mensagem = mensagem;
    }

    public double getTotal() {
        return total;
    }

    public double getTotalComDesconto() {
        return totalComDesconto;
    }

    public String getMensagem() {
        return mensagem;
    }
}
