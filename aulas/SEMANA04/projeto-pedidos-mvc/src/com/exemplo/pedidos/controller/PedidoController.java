package com.exemplo.pedidos.controller;

import com.exemplo.pedidos.dto.PedidoRequestDTO;
import com.exemplo.pedidos.model.ItemPedido;
import com.exemplo.pedidos.model.Pedido;
import com.exemplo.pedidos.model.Produto;
import com.exemplo.pedidos.service.PedidoService;
import com.exemplo.pedidos.view.PedidoView;

public class PedidoController {
    private final PedidoView view;

    public PedidoController(PedidoView view) {
        this.view = view;
    }

    public PedidoController(PedidoService pedidoService) {
        this.view = new PedidoView();
        //TODO Auto-generated constructor stub
    }

    public void registrarPedido() {
        try {
            String nomeCliente = view.lerNomeCliente();
            boolean clienteVip = view.lerClienteVip();
            Pedido pedido = new Pedido(nomeCliente, clienteVip);

            int quantidadeItens = view.lerQuantidadeItens();
            if (quantidadeItens <= 0) {
                throw new IllegalArgumentException("O pedido deve conter ao menos um item.");
            }

            for (int i = 1; i <= quantidadeItens; i++) {
                String nomeProduto = view.lerNomeProduto(i);
                double precoProduto = view.lerPrecoProduto(i);
                int quantidadeProduto = view.lerQuantidadeProduto(i);

                Produto produto = new Produto(nomeProduto, precoProduto);
                ItemPedido item = new ItemPedido(produto, quantidadeProduto);
                pedido.adicionarItem(item);
            }

            double totalBruto = pedido.calcularTotalBruto();
            double desconto = pedido.calcularDesconto();
            double totalFinal = pedido.calcularTotalFinal();

            view.exibirResumo(nomeCliente, totalBruto, desconto, totalFinal);
        } catch (IllegalArgumentException e) {
            view.exibirMensagem("Erro de negócio: " + e.getMessage());
        } catch (Exception e) {
            view.exibirMensagem("Erro ao registrar pedido: " + e.getMessage());
        }
    }

    public Object registrarPedido(PedidoRequestDTO request) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'registrarPedido'");
    }
}
