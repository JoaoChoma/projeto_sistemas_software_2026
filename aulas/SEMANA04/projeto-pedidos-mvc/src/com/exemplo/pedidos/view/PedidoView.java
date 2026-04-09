package com.exemplo.pedidos.view;

import java.util.List;
import java.util.Scanner;

import com.exemplo.pedidos.dto.PedidoRequestDTO;
import com.exemplo.pedidos.model.Produto;

public class PedidoView {
    private final Scanner scanner;

    public PedidoView() {
        this.scanner = new Scanner(System.in);
    }

    public String lerNomeCliente() {
        System.out.print("Informe o nome do cliente: ");
        return scanner.nextLine();
    }

    public boolean lerClienteVip() {
        System.out.print("Cliente VIP? (s/n): ");
        String resposta = scanner.nextLine();
        return resposta.equalsIgnoreCase("s");
    }

    public int lerQuantidadeItens() {
        System.out.print("Quantos itens deseja registrar? ");
        return Integer.parseInt(scanner.nextLine());
    }

    public String lerNomeProduto(int indice) {
        System.out.print("Nome do produto " + indice + ": ");
        return scanner.nextLine();
    }

    public double lerPrecoProduto(int indice) {
        System.out.print("Preço do produto " + indice + ": ");
        return Double.parseDouble(scanner.nextLine());
    }

    public int lerQuantidadeProduto(int indice) {
        System.out.print("Quantidade do produto " + indice + ": ");
        return Integer.parseInt(scanner.nextLine());
    }

    public void exibirMensagem(String mensagem) {
        System.out.println(mensagem);
    }

    public void exibirResumo(String cliente, double totalBruto, double desconto, double totalFinal) {
        System.out.println("\n===== RESUMO DO PEDIDO =====");
        System.out.println("Cliente: " + cliente);
        System.out.printf("Total bruto: R$ %.2f%n", totalBruto);
        System.out.printf("Desconto: R$ %.2f%n", desconto);
        System.out.printf("Total final: R$ %.2f%n", totalFinal);
        System.out.println("Pedido registrado com sucesso.");
    }

    public void mostrarProdutos(List<Produto> listarTodos) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'mostrarProdutos'");
    }

    public PedidoRequestDTO lerDadosPedido() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'lerDadosPedido'");
    }

    public void mostrarResultado(Object registrarPedido) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'mostrarResultado'");
    }

    public void mostrarErro(String message) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'mostrarErro'");
    }
}
