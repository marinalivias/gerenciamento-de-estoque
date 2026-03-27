package service;

import model.Produto;
import model.Categoria;

import java.util.ArrayList;
import java.util.List;

public class ProdutoService {

    private List<Produto> produtos = new ArrayList<>();

    public void criarProduto(String nome, Categoria categoria, String unidade, double estoqueMinimo, double estoqueIdeal) {
        Produto produto = new Produto(nome, categoria, unidade, estoqueMinimo, estoqueIdeal);
        produtos.add(produto);
        System.out.println("Produto criado!");
    }

    public List<Produto> listarProdutos() {
        return produtos;
    }
}