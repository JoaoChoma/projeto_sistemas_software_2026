# Parte 2 — Evolução para Arquitetura em Camadas: Registrar Pedido

## Objetivo

Esta segunda versão mantém o mesmo problema e o mesmo caso de uso da versão em MVC, mas reorganiza a solução em **arquitetura em camadas**. Quando o sistema cresce, torna-se importante separar melhor as responsabilidades da aplicação.

A proposta é evidenciar a evolução de uma solução inicialmente organizada em **Model–View–Controller** para uma solução com camadas mais explícitas, aproximando o exemplo do que os estudantes encontrarão em frameworks como **Spring Boot** no back-end.

---

## 1. Problema mantido

Uma cafeteria deseja informatizar o processo de registro de pedidos. O sistema deve permitir que o atendente:

- informe os produtos desejados pelo cliente;
- registre a quantidade de cada item;
- calcule automaticamente o valor total do pedido;
- aplique desconto para cliente VIP;
- confirme o pedido ao final do atendimento.

O caso de uso central continua sendo **Registrar pedido**.

---

## 2. Por que evoluir de MVC para camadas?

Na versão em MVC, parte importante da lógica acabou concentrada no controller. Isso funciona em exemplos pequenos, mas passa a gerar problemas conforme o sistema evolui.

Na arquitetura em camadas, buscamos separar melhor as responsabilidades:

- **Controller**: recebe a entrada e devolve a saída;
- **Service**: coordena o caso de uso e aplica regras de negócio;
- **Repository**: encapsula o acesso aos dados;
- **Model**: representa os conceitos do domínio;
- **DTO**: transporta dados entre camadas sem expor diretamente as entidades.

Essa separação melhora legibilidade, manutenção, reuso e testabilidade.

---

## 3. Estrutura de arquivos em arquitetura em camadas

Uma organização possível em Java seria:

```text
src/
└── com/
    └── exemplo/
        └── pedidos/
            ├── Main.java
            ├── controller/
            │   └── PedidoController.java
            ├── service/
            │   └── PedidoService.java
            ├── repository/
            │   ├── PedidoRepository.java
            │   └── ProdutoRepository.java
            ├── model/
            │   ├── Pedido.java
            │   ├── ItemPedido.java
            │   └── Produto.java
            ├── dto/
            │   ├── ItemPedidoRequestDTO.java
            │   ├── PedidoRequestDTO.java
            │   └── PedidoResponseDTO.java
            └── view/
                └── PedidoView.java
```

---

## 4. Papel de cada camada

### 4.1 Camada de apresentação
Responsável pela interação com o usuário.

No exemplo:
- `PedidoView`
- `PedidoController`

A **view** coleta os dados e apresenta resultados.
O **controller** recebe os dados da view, chama o serviço e devolve a resposta.

### 4.2 Camada de serviço
Responsável por implementar o caso de uso.

No exemplo:
- `PedidoService`

Essa camada aplica regras como:
- validar se existe ao menos um item;
- validar se a quantidade é maior que zero;
- calcular total;
- aplicar desconto para cliente VIP.

### 4.3 Camada de persistência
Responsável por isolar o acesso aos dados.

No exemplo:
- `ProdutoRepository`
- `PedidoRepository`

Mesmo que inicialmente os dados estejam apenas em memória, essa separação prepara a aplicação para futuras evoluções, como banco de dados.

### 4.4 Camada de domínio
Representa os conceitos centrais do problema.

No exemplo:
- `Produto`
- `ItemPedido`
- `Pedido`

### 4.5 DTOs
Servem para transportar dados entre camadas.

No exemplo:
- `PedidoRequestDTO`
- `ItemPedidoRequestDTO`
- `PedidoResponseDTO`

Eles evitam que a camada externa manipule diretamente as entidades do domínio.

---

## 5. Diferença principal em relação ao MVC

Na versão MVC, o controller acumulava boa parte do fluxo e da lógica de negócio.

Na versão em camadas:

- o **controller** apenas recebe a entrada e delega;
- o **service** executa o caso de uso;
- o **repository** fornece e armazena dados;
- o **model** representa o domínio.

Assim, o caso de uso **Registrar pedido** atravessa várias camadas de forma organizada.

---

## 6. Vantagens da arquitetura em camadas nesse exemplo

### 6.1 Melhor separação de responsabilidades
Cada classe possui um papel mais claro.

### 6.2 Controller mais simples
O controller deixa de concentrar regras de negócio.

### 6.3 Maior facilidade para testar
É possível testar a lógica principal diretamente no service.

### 6.4 Melhor reuso
As regras do caso de uso podem ser reaproveitadas por outros tipos de interface.

### 6.5 Maior facilidade de evolução
A troca de persistência em memória por banco de dados se torna mais simples.

---

## 7. Código Java — versão em camadas

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

### Classe `ItemPedidoRequestDTO`

```java
package com.exemplo.pedidos.dto;

public class ItemPedidoRequestDTO {
    private int codigoProduto;
    private int quantidade;

    public ItemPedidoRequestDTO(int codigoProduto, int quantidade) {
        this.codigoProduto = codigoProduto;
        this.quantidade = quantidade;
    }

    public int getCodigoProduto() {
        return codigoProduto;
    }

    public int getQuantidade() {
        return quantidade;
    }
}
```

### Classe `PedidoRequestDTO`

```java
package com.exemplo.pedidos.dto;

import java.util.List;

public class PedidoRequestDTO {
    private List<ItemPedidoRequestDTO> itens;
    private boolean clienteVip;

    public PedidoRequestDTO(List<ItemPedidoRequestDTO> itens, boolean clienteVip) {
        this.itens = itens;
        this.clienteVip = clienteVip;
    }

    public List<ItemPedidoRequestDTO> getItens() {
        return itens;
    }

    public boolean isClienteVip() {
        return clienteVip;
    }
}
```

### Classe `PedidoResponseDTO`

```java
package com.exemplo.pedidos.dto;

public class PedidoResponseDTO {
    private double total;
    private double totalComDesconto;
    private String mensagem;

    public PedidoResponseDTO(double total, double totalComDesconto, String mensagem) {
        this.total = total;
        this.totalComDesconto = totalComDesconto;
        this.mensagem = mensagem;
    }

    public double getTotal() {
        return total;
    }

    public double getTotalComDesconto() {
        return totalComDesconto;
    }

    public String getMensagem() {
        return mensagem;
    }
}
```

### Classe `ProdutoRepository`

```java
package com.exemplo.pedidos.repository;

import java.util.ArrayList;
import java.util.List;
import com.exemplo.pedidos.model.Produto;

public class ProdutoRepository {
    private List<Produto> produtos = new ArrayList<>();

    public ProdutoRepository() {
        produtos.add(new Produto(1, "Café Expresso", 5.00));
        produtos.add(new Produto(2, "Cappuccino", 8.50));
        produtos.add(new Produto(3, "Pão de Queijo", 6.00));
    }

    public List<Produto> listarTodos() {
        return produtos;
    }

    public Produto buscarPorCodigo(int codigo) {
        for (Produto produto : produtos) {
            if (produto.getCodigo() == codigo) {
                return produto;
            }
        }
        return null;
    }
}
```

### Classe `PedidoRepository`

```java
package com.exemplo.pedidos.repository;

import java.util.ArrayList;
import java.util.List;
import com.exemplo.pedidos.model.Pedido;

public class PedidoRepository {
    private List<Pedido> pedidos = new ArrayList<>();

    public void salvar(Pedido pedido) {
        pedidos.add(pedido);
    }

    public List<Pedido> listarTodos() {
        return pedidos;
    }
}
```

### Classe `PedidoService`

```java
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
    private ProdutoRepository produtoRepository;
    private PedidoRepository pedidoRepository;

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
```

### Classe `PedidoController`

```java
package com.exemplo.pedidos.controller;

import com.exemplo.pedidos.dto.PedidoRequestDTO;
import com.exemplo.pedidos.dto.PedidoResponseDTO;
import com.exemplo.pedidos.service.PedidoService;

public class PedidoController {
    private PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    public PedidoResponseDTO registrarPedido(PedidoRequestDTO request) {
        return pedidoService.registrarPedido(request);
    }
}
```

### Classe `PedidoView`

```java
package com.exemplo.pedidos.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import com.exemplo.pedidos.dto.ItemPedidoRequestDTO;
import com.exemplo.pedidos.dto.PedidoRequestDTO;
import com.exemplo.pedidos.dto.PedidoResponseDTO;
import com.exemplo.pedidos.model.Produto;

public class PedidoView {
    private Scanner scanner = new Scanner(System.in);

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
```

### Classe `Main`

```java
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
```

---

## 8. Decisões de projeto

Evolução arquitetural:

- Onde está a regra de negócio em cada versão?
- Qual controller está mais simples?
- Em qual estrutura ficaria mais fácil trocar a persistência em memória por banco de dados?
- Em qual estrutura seria mais fácil testar o caso de uso?