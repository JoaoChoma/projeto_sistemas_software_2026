package com.exemplo.pedidos.repository;

import java.util.ArrayList;
import java.util.List;
import com.exemplo.pedidos.model.Pedido;

public class PedidoRepository {
    private final List<Pedido> pedidos = new ArrayList<>();

    public void salvar(Pedido pedido) {
        pedidos.add(pedido);
    }

    public List<Pedido> listarTodos() {
        return pedidos;
    }
}
