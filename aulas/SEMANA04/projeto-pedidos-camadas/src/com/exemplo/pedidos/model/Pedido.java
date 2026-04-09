package com.exemplo.pedidos.model;

import java.util.ArrayList;
import java.util.List;

public class Pedido {
    private final List<ItemPedido> itens;
    private boolean clienteVip;

    public Pedido() {
        this.itens = new ArrayList<>();
    }

    public void adicionarItem(ItemPedido item) {
        itens.add(item);
    }

    public List<ItemPedido> getItens() {
        return itens;
    }

    public boolean isClienteVip() {
        return clienteVip;
    }

    public void setClienteVip(boolean clienteVip) {
        this.clienteVip = clienteVip;
    }

    public double calcularTotal() {
        double total = 0.0;
        for (ItemPedido item : itens) {
            total += item.getSubtotal();
        }
        return total;
    }

    public double calcularTotalComDesconto() {
        double total = calcularTotal();
        return clienteVip ? total * 0.9 : total;
    }
}
