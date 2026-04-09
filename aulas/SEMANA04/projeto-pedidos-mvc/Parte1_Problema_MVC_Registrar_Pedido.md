# Parte 1 — Problema e Caso de Uso: Registrar Pedido

## Contexto da aula
Nesta aula, o objetivo é apresentar aos estudantes como um **caso de uso** pode ser implementado em código Java a partir de uma **decisão arquitetural**. O foco inicial será o modelo **MVC (Model-View-Controller)**, que eles já conhecem conceitualmente, para depois discutir sua evolução para arquiteturas em camadas.

O produto escolhido para a aula é um **mini sistema de pedidos de cafeteria**. Esse produto é adequado porque possui regras simples, entidades fáceis de compreender e permite demonstrar claramente a separação entre entrada de dados, processamento da lógica e representação das informações.

---

## Problema
Uma cafeteria deseja informatizar o processo de registro de pedidos. Atualmente, os pedidos são anotados manualmente, o que gera erros no cálculo do valor total, dificuldade para aplicar descontos e pouca rastreabilidade no atendimento.

O sistema deverá permitir que um atendente registre um pedido a partir da seleção de produtos e quantidades, calcule o valor total, aplique regras de desconto quando necessário e apresente um resumo final do pedido.

A intenção pedagógica deste exemplo é mostrar que a implementação de um caso de uso não deve ser pensada apenas como “escrever código que funciona”, mas como uma solução estruturada em torno de responsabilidades arquiteturais.

---

## Caso de uso: Registrar pedido

### Objetivo
Permitir que um atendente registre um novo pedido para um cliente.

### Atores envolvidos
- **Atendente**: responsável por informar os dados do pedido.
- **Sistema**: responsável por validar os dados, calcular o total, aplicar desconto e apresentar o resultado.

### Gatilho
O atendente inicia o processo de registro de um novo pedido.

### Pré-condições
- Os produtos disponíveis devem estar cadastrados no sistema.
- O atendente deve informar pelo menos um item no pedido.

### Fluxo principal
1. O atendente informa o nome do cliente.
2. O atendente escolhe um ou mais produtos.
3. O atendente informa a quantidade desejada para cada produto.
4. O sistema calcula o subtotal de cada item.
5. O sistema calcula o valor total do pedido.
6. O sistema verifica se o cliente é VIP.
7. Caso o cliente seja VIP, o sistema aplica 10% de desconto.
8. O sistema apresenta um resumo com os dados do pedido e o valor final.
9. O pedido é registrado com sucesso.

### Fluxos alternativos
- **FA1 — Quantidade inválida**: se a quantidade informada para um item for menor ou igual a zero, o sistema deve rejeitar o item e informar o erro.
- **FA2 — Pedido sem itens**: se nenhum item for informado, o sistema não permite concluir o pedido.
- **FA3 — Produto inexistente**: se um produto informado não estiver cadastrado, o sistema deve interromper o registro e informar a inconsistência.

### Pós-condições
- O pedido deve estar representado no sistema com cliente, itens, total bruto, desconto e total final.
- O sistema deve apresentar uma mensagem confirmando o registro.

---

## Regras de negócio
- Um pedido deve conter **ao menos um item**.
- A quantidade de cada item deve ser **maior que zero**.
- O valor total do pedido corresponde à soma dos subtotais dos itens.
- Clientes VIP recebem **10% de desconto** sobre o valor total.
- O sistema deve apresentar o valor bruto e o valor final do pedido.

---

## Entidades envolvidas
- **Produto**: nome e preço.
- **ItemPedido**: produto, quantidade e subtotal.
- **Pedido**: cliente, lista de itens, valor bruto, desconto e valor final.

---

## Exemplo de execução

### Entrada
- Cliente: Maria
- Cliente VIP: Sim
- Itens:
  - Café expresso — 2 unidades — R$ 5,00 cada
  - Pão de queijo — 3 unidades — R$ 4,00 cada

### Processamento
- Subtotal café expresso: 2 × 5,00 = R$ 10,00
- Subtotal pão de queijo: 3 × 4,00 = R$ 12,00
- Total bruto: R$ 22,00
- Desconto VIP (10%): R$ 2,20
- Total final: R$ 19,80

### Saída esperada
- Cliente: Maria
- Total bruto: R$ 22,00
- Desconto: R$ 2,20
- Total final: R$ 19,80
- Status: Pedido registrado com sucesso

---

# Organização em MVC

## Visão geral
No modelo **MVC**, a aplicação pode ser organizada em três responsabilidades centrais:

- **Model**: representa os dados e regras essenciais do problema.
- **View**: responsável pela interação com o usuário.
- **Controller**: recebe as ações da interface, coordena a execução e aciona o modelo.

Para esse exemplo, a implementação pode ser feita inicialmente em Java puro, com interface por terminal, apenas para tornar a arquitetura visível aos estudantes.

---

## Estrutura sugerida de arquivos em MVC

```text
projeto-pedidos-mvc/
├── src/
│   └── com/
│       └── exemplo/
│           └── pedidos/
│               ├── controller/
│               │   └── PedidoController.java
│               ├── model/
│               │   ├── Produto.java
│               │   ├── ItemPedido.java
│               │   └── Pedido.java
│               ├── view/
│               │   └── PedidoView.java
│               └── Main.java
└── README.md
```

---

## Papel de cada elemento

### Model
Contém as classes que representam o domínio do problema:
- `Produto`: representa o item vendido.
- `ItemPedido`: representa um produto adicionado ao pedido com quantidade.
- `Pedido`: representa o pedido completo e seus cálculos.

### View
Contém a interação com o usuário:
- `PedidoView`: exibe mensagens, lê dados e apresenta o resultado.

### Controller
Coordena a execução do caso de uso:
- `PedidoController`: recebe os dados da `View`, instancia e manipula os objetos do `Model`, solicita cálculos e devolve a resposta à interface.

### Main
Ponto de entrada da aplicação:
- cria a `View`
- cria o `Controller`
- inicia a execução do caso de uso

---

# Limitações do MVC neste exemplo

Embora o MVC seja excelente para introduzir separação de responsabilidades, ele começa a apresentar limitações quando o sistema cresce.

## 1. Controller tende a ficar sobrecarregado
No início, o controller apenas recebe dados e aciona o modelo. Porém, em aplicações reais, ele frequentemente passa a concentrar validações, regras de negócio, chamadas a banco de dados e orquestrações complexas.

Neste exemplo, seria comum cair no erro de colocar no `PedidoController`:
- cálculo de total
- aplicação de desconto
- validação de itens
- busca de produtos

Com isso, o controller deixa de apenas controlar o fluxo e passa a acumular responsabilidades.

## 2. Dificuldade de evolução para aplicações maiores
Quando surgem novas necessidades, como:
- diferentes regras de desconto
- integração com banco de dados
- autenticação de usuários
- API REST
- testes automatizados mais isolados

O MVC simples já não organiza tão bem essas preocupações.

## 3. Regras de negócio ficam espalhadas
Sem uma camada de serviço, parte da lógica pode ir para o controller e outra parte para o model, dificultando a manutenção.

## 4. Baixo reaproveitamento da lógica
Se a lógica de negócio estiver presa à interface ou ao controller, reaproveitá-la em outro contexto, como API web, aplicação desktop ou testes, torna-se mais trabalhoso.

## 5. Transição para frameworks modernos exige refinamento
Frameworks como **Spring Boot** geralmente expandem o MVC para uma organização mais robusta, com camadas como:
- controller
- service
- repository
- domain/model

Ou seja, o MVC continua existindo, mas é refinado dentro de uma arquitetura em camadas.

---

# Exemplo de código Java em MVC

## Main.java
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

## Produto.java
```java
package com.exemplo.pedidos.model;

public class Produto {
    private String nome;
    private double preco;

    public Produto(String nome, double preco) {
        this.nome = nome;
        this.preco = preco;
    }

    public String getNome() {
        return nome;
    }

    public double getPreco() {
        return preco;
    }
}
```

## ItemPedido.java
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

    public double getSubtotal() {
        return produto.getPreco() * quantidade;
    }
}
```

## Pedido.java
```java
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
}
```

## PedidoView.java
```java
package com.exemplo.pedidos.view;

import java.util.Scanner;

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
}
```

## PedidoController.java
```java
package com.exemplo.pedidos.controller;

import com.exemplo.pedidos.model.ItemPedido;
import com.exemplo.pedidos.model.Pedido;
import com.exemplo.pedidos.model.Produto;
import com.exemplo.pedidos.view.PedidoView;

public class PedidoController {
    private final PedidoView view;

    public PedidoController(PedidoView view) {
        this.view = view;
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
}
```

---

# Observação importante
Este exemplo é propositalmente simples para introduzir o raciocínio arquitetural. Em uma continuação da aula, ele pode ser refatorado para uma arquitetura em camadas, separando melhor as responsabilidades em classes como:
- `PedidoController`
- `PedidoService`
- `PedidoRepository`
- `PedidoDTO`