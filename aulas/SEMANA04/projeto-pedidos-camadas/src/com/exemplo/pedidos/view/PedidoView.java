package com.exemplo.pedidos.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import com.exemplo.pedidos.dto.ItemPedidoRequestDTO;
import com.exemplo.pedidos.dto.PedidoRequestDTO;
import com.exemplo.pedidos.dto.PedidoResponseDTO;
import com.exemplo.pedidos.model.Produto;

public class PedidoView {
    private final Scanner scanner = new Scanner(System.in);

    public void mostrarProdutos(List<Produto> produtos) {
        System.out.println("=== PRODUTOS DISPONÍVEIS ===");
        for (Produto produto : produtos) {
            System.out.println(produto.getCodigo() + " - " + produto.getNome() + " - R$ " + produto.getPreco());
        }
    }

    public PedidoRequestDTO lerDadosPedido() {
        List<ItemPedidoRequestDTO> itens = new ArrayList<>();
        boolean continuar = true;

        while (continuar) {
            System.out.print("Informe o código do produto: ");
            int codigo = scanner.nextInt();

            System.out.print("Informe a quantidade: ");
            int quantidade = scanner.nextInt();

            itens.add(new ItemPedidoRequestDTO(codigo, quantidade));

            System.out.print("Deseja adicionar mais itens? (s/n): ");
            String resposta = scanner.next();
            continuar = resposta.equalsIgnoreCase("s");
        }

        System.out.print("Cliente VIP? (s/n): ");
        boolean clienteVip = scanner.next().equalsIgnoreCase("s");

        return new PedidoRequestDTO(itens, clienteVip);
    }

    public void mostrarResultado(PedidoResponseDTO response) {
        System.out.println(response.getMensagem());
        System.out.println("Total do pedido: R$ " + response.getTotal());
        System.out.println("Total com desconto: R$ " + response.getTotalComDesconto());
    }

    public void mostrarErro(String mensagem) {
        System.out.println("Erro: " + mensagem);
    }
}
