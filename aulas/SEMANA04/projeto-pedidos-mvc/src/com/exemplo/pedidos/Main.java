package com.exemplo.pedidos;

import com.exemplo.pedidos.controller.PedidoController;
import com.exemplo.pedidos.view.PedidoView;

public class Main {
    public static void main(String[] args) {
        PedidoView view = new PedidoView();
        PedidoController controller = new PedidoController(view);
        controller.registrarPedido();
    }
}
