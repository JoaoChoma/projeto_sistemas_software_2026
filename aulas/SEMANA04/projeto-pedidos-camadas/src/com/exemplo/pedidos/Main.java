package com.exemplo.pedidos;

import com.exemplo.pedidos.controller.PedidoController;
import com.exemplo.pedidos.dto.PedidoRequestDTO;
import com.exemplo.pedidos.repository.PedidoRepository;
import com.exemplo.pedidos.repository.ProdutoRepository;
import com.exemplo.pedidos.service.PedidoService;
import com.exemplo.pedidos.view.PedidoView;

public class Main {
    public static void main(String[] args) {
        ProdutoRepository produtoRepository = new ProdutoRepository();
        PedidoRepository pedidoRepository = new PedidoRepository();
        PedidoService pedidoService = new PedidoService(produtoRepository, pedidoRepository);
        PedidoController controller = new PedidoController(pedidoService);
        PedidoView view = new PedidoView();

        view.mostrarProdutos(produtoRepository.listarTodos());

        try {
            PedidoRequestDTO request = view.lerDadosPedido();
            view.mostrarResultado(controller.registrarPedido(request));
        } catch (IllegalArgumentException e) {
            view.mostrarErro(e.getMessage());
        }
    }
}
