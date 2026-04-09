# Parte 1 — Problema e Caso de Uso: Registrar Pedido

## Produto

Um **mini sistema de pedidos de cafeteria**.

---

## 1. Problema

Uma cafeteria deseja informatizar o processo de registro de pedidos. Atualmente, os pedidos são anotados manualmente, o que gera erros de cálculo, dificuldade de conferência e pouca rastreabilidade.

O sistema deverá permitir que o atendente:

- informe os produtos desejados pelo cliente;
- registre a quantidade de cada item;
- calcule automaticamente o valor total do pedido;
- aplique desconto para cliente VIP;
- confirme o pedido ao final do atendimento.

---

## 2. Caso de uso: Registrar pedido

### Nome
Registrar pedido

### Objetivo
Permitir que o atendente registre um novo pedido no sistema, calculando automaticamente o total e eventual desconto.

### Atores
- **Ator principal:** Atendente
- **Interessado secundário:** Cliente

### Pré-condições
- os produtos devem estar previamente cadastrados;
- o atendente deve informar ao menos um item no pedido.

### Pós-condições
- o pedido deve estar registrado em memória no sistema;
- o valor total deve ser exibido;
- o valor com desconto, quando aplicável, deve ser apresentado.

### Fluxo principal
1. O atendente inicia o registro do pedido.
2. O sistema apresenta os produtos disponíveis.
3. O atendente informa o código do produto e a quantidade desejada.
4. O sistema adiciona o item ao pedido.
5. O atendente informa se o cliente é VIP.
6. O sistema calcula o valor total do pedido.
7. O sistema aplica desconto, se o cliente for VIP.
8. O sistema exibe o resumo final do pedido.

### Fluxos alternativos
- **FA1:** se a quantidade informada for menor ou igual a zero, o item não deve ser aceito.
- **FA2:** se nenhum item for informado, o pedido não pode ser finalizado.
- **FA3:** se o código do produto não existir, o sistema deve informar erro.

### Regras de negócio
- RN1: um pedido deve possuir ao menos um item.
- RN2: a quantidade de cada item deve ser maior que zero.
- RN3: cliente VIP recebe 10% de desconto sobre o valor total.
- RN4: o total do pedido corresponde à soma de todos os itens.

---

## 3. Organização em MVC

Uma possível estrutura de arquivos em Java usando MVC seria:

```text
src/
└── com/
    └── exemplo/
        └── pedidos/
            ├── Main.java
            ├── controller/
            │   └── PedidoController.java
            ├── model/
            │   ├── Pedido.java
            │   ├── ItemPedido.java
            │   └── Produto.java
            └── view/
                └── PedidoView.java
```

### Papel de cada parte

#### Model
Representa os dados e parte do comportamento do domínio.
Exemplos:
- `Produto`
- `ItemPedido`
- `Pedido`

#### View
Responsável pela interação com o usuário.
Exemplo:
- `PedidoView`

#### Controller
Recebe as ações do usuário pela view, coordena o processamento e atualiza a saída.
Exemplo:
- `PedidoController`

---

## 4. Limitações do MVC nesse cenário

Embora o MVC seja uma boa porta de entrada para uma arquitetura inicial, ele apresenta limitações quando o sistema cresce.

### 4.1 Controller tende a concentrar regras demais
No início, parece natural colocar no controller:
- cálculo do total;
- aplicação de desconto;
- validação de itens;
- coordenação do fluxo.

Com o crescimento do sistema, isso faz o controller ficar inchado.

### 4.2 Mistura de responsabilidades
O controller passa a acumular:
- controle de entrada e saída;
- regras de negócio;
- decisões do fluxo.

Essa mistura dificulta manutenção e entendimento.

### 4.3 Dificuldade de reutilização
Se a regra de desconto estiver no controller, reaproveitá-la em outro contexto fica mais difícil, como:
- API REST;
- aplicação desktop;
- testes automatizados;
- outro tipo de interface.

### 4.4 Evolução limitada
Quando surgem novas necessidades, como persistência, autenticação, integrações externas e múltiplos casos de uso, o MVC puro tende a ficar insuficiente. Nesse ponto, costuma-se evoluir para **arquitetura em camadas**, separando melhor:
- apresentação;
- aplicação/serviço;
- domínio;
- persistência.

---

## 5. Exemplo de implementação em Java (MVC simplificado)

### Classe `Produto`

```java
package com.exemplo.pedidos.model;

public class Produto {
    private int codigo;
    private String nome;
    private double preco;

    public Produto(int codigo, String nome, double preco) {
        this.codigo = codigo;
        this.nome = nome;
        this.preco = preco;
    }

    public int getCodigo() {
        return codigo;
    }

    public String getNome() {
        return nome;
    }

    public double getPreco() {
        return preco;
    }
}
```

### Classe `ItemPedido`

```java
package com.exemplo.pedidos.model;

public class ItemPedido {
    private Produto produto;
    private int quantidade;

    public ItemPedido(Produto produto, int quantidade) {
        this.produto = produto;
        this.quantidade = quantidade;
    }

    public Produto getProduto() {
        return produto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public double calcularSubtotal() {
        return produto.getPreco() * quantidade;
    }
}
```

### Classe `Pedido`

```java
package com.exemplo.pedidos.model;

import java.util.ArrayList;
import java.util.List;

public class Pedido {
    private List<ItemPedido> itens;
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
            total += item.calcularSubtotal();
        }
        return total;
    }

    public double calcularTotalComDesconto() {
        double total = calcularTotal();
        if (clienteVip) {
            return total * 0.9;
        }
        return total;
    }
}
```

### Classe `PedidoView`

```java
package com.exemplo.pedidos.view;

import java.util.List;
import java.util.Scanner;
import com.exemplo.pedidos.model.Produto;

public class PedidoView {
    private Scanner scanner = new Scanner(System.in);

    public void mostrarProdutos(List<Produto> produtos) {
        System.out.println("=== PRODUTOS DISPONÍVEIS ===");
        for (Produto produto : produtos) {
            System.out.println(produto.getCodigo() + " - " + produto.getNome() + " - R$ " + produto.getPreco());
        }
    }

    public int lerCodigoProduto() {
        System.out.print("Informe o código do produto: ");
        return scanner.nextInt();
    }

    public int lerQuantidade() {
        System.out.print("Informe a quantidade: ");
        return scanner.nextInt();
    }

    public boolean perguntarSeContinua() {
        System.out.print("Deseja adicionar mais itens? (s/n): ");
        String resposta = scanner.next();
        return resposta.equalsIgnoreCase("s");
    }

    public boolean lerClienteVip() {
        System.out.print("Cliente VIP? (s/n): ");
        String resposta = scanner.next();
        return resposta.equalsIgnoreCase("s");
    }

    public void mostrarMensagem(String mensagem) {
        System.out.println(mensagem);
    }

    public void mostrarResumo(double total, double totalComDesconto) {
        System.out.println("Total do pedido: R$ " + total);
        System.out.println("Total com desconto: R$ " + totalComDesconto);
    }
}
```

### Classe `PedidoController`

```java
package com.exemplo.pedidos.controller;

import java.util.ArrayList;
import java.util.List;
import com.exemplo.pedidos.model.ItemPedido;
import com.exemplo.pedidos.model.Pedido;
import com.exemplo.pedidos.model.Produto;
import com.exemplo.pedidos.view.PedidoView;

public class PedidoController {
    private List<Produto> produtos;
    private PedidoView view;

    public PedidoController(PedidoView view) {
        this.view = view;
        this.produtos = new ArrayList<>();
        carregarProdutos();
    }

    private void carregarProdutos() {
        produtos.add(new Produto(1, "Café Expresso", 5.00));
        produtos.add(new Produto(2, "Cappuccino", 8.50));
        produtos.add(new Produto(3, "Pão de Queijo", 6.00));
    }

    public void registrarPedido() {
        Pedido pedido = new Pedido();
        boolean continuar = true;

        view.mostrarProdutos(produtos);

        while (continuar) {
            int codigo = view.lerCodigoProduto();
            Produto produto = buscarProdutoPorCodigo(codigo);

            if (produto == null) {
                view.mostrarMensagem("Produto não encontrado.");
                continue;
            }

            int quantidade = view.lerQuantidade();

            if (quantidade <= 0) {
                view.mostrarMensagem("Quantidade inválida.");
                continue;
            }

            pedido.adicionarItem(new ItemPedido(produto, quantidade));
            continuar = view.perguntarSeContinua();
        }

        if (pedido.getItens().isEmpty()) {
            view.mostrarMensagem("O pedido deve possuir ao menos um item.");
            return;
        }

        pedido.setClienteVip(view.lerClienteVip());

        double total = pedido.calcularTotal();
        double totalComDesconto = pedido.calcularTotalComDesconto();

        view.mostrarResumo(total, totalComDesconto);
    }

    private Produto buscarProdutoPorCodigo(int codigo) {
        for (Produto produto : produtos) {
            if (produto.getCodigo() == codigo) {
                return produto;
            }
        }
        return null;
    }
}
```

### Classe `Main`

```java
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
```