package com.exemplo.pedidos.service;

import com.exemplo.pedidos.dto.ItemPedidoRequestDTO;
import com.exemplo.pedidos.dto.PedidoRequestDTO;
import com.exemplo.pedidos.dto.PedidoResponseDTO;
import com.exemplo.pedidos.model.ItemPedido;
import com.exemplo.pedidos.model.Pedido;
import com.exemplo.pedidos.model.Produto;
import com.exemplo.pedidos.repository.PedidoRepository;
import com.exemplo.pedidos.repository.ProdutoRepository;

public class PedidoService {
    private final ProdutoRepository produtoRepository;
    private final PedidoRepository pedidoRepository;

    public PedidoService(ProdutoRepository produtoRepository, PedidoRepository pedidoRepository) {
        this.produtoRepository = produtoRepository;
        this.pedidoRepository = pedidoRepository;
    }

    public PedidoResponseDTO registrarPedido(PedidoRequestDTO request) {
        if (request.getItens() == null || request.getItens().isEmpty()) {
            throw new IllegalArgumentException("O pedido deve possuir ao menos um item.");
        }

        Pedido pedido = new Pedido();
        pedido.setClienteVip(request.isClienteVip());

        for (ItemPedidoRequestDTO itemDTO : request.getItens()) {
            if (itemDTO.getQuantidade() <= 0) {
                throw new IllegalArgumentException("Quantidade inválida para o item.");
            }

            Produto produto = produtoRepository.buscarPorCodigo(itemDTO.getCodigoProduto());
            if (produto == null) {
                throw new IllegalArgumentException("Produto não encontrado: código " + itemDTO.getCodigoProduto());
            }

            pedido.adicionarItem(new ItemPedido(produto, itemDTO.getQuantidade()));
        }

        pedidoRepository.salvar(pedido);

        return new PedidoResponseDTO(
            pedido.calcularTotal(),
            pedido.calcularTotalComDesconto(),
            "Pedido registrado com sucesso."
        );
    }
}
