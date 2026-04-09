package com.exemplo.pedidos.model;

import java.util.ArrayList;
import java.util.List;

public class Pedido {
    private String cliente;
    private boolean clienteVip;
    private List<ItemPedido> itens;

    public Pedido(String cliente, boolean clienteVip) {
        this.cliente = cliente;
        this.clienteVip = clienteVip;
        this.itens = new ArrayList<>();
    }

    public Pedido() {
        //TODO Auto-generated constructor stub
    }

    public void adicionarItem(ItemPedido item) {
        if (item.getQuantidade() <= 0) {
            throw new IllegalArgumentException("A quantidade deve ser maior que zero.");
        }
        itens.add(item);
    }

    public String getCliente() {
        return cliente;
    }

    public boolean isClienteVip() {
        return clienteVip;
    }

    public List<ItemPedido> getItens() {
        return itens;
    }

    public double calcularTotalBruto() {
        double total = 0.0;
        for (ItemPedido item : itens) {
            total += item.getSubtotal();
        }
        return total;
    }

    public double calcularDesconto() {
        if (clienteVip) {
            return calcularTotalBruto() * 0.10;
        }
        return 0.0;
    }

    public double calcularTotalFinal() {
        return calcularTotalBruto() - calcularDesconto();
    }

    public void setClienteVip(boolean clienteVip2) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setClienteVip'");
    }

    public double calcularTotal() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'calcularTotal'");
    }

    public double calcularTotalComDesconto() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'calcularTotalComDesconto'");
    }
}
