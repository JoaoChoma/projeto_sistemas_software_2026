package com.exemplo.pedidos.repository;

import java.util.ArrayList;
import java.util.List;
import com.exemplo.pedidos.model.Produto;

public class ProdutoRepository {
    private final List<Produto> produtos = new ArrayList<>();

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
