package service;

import model.Produto;
import model.Categoria;
import util.ArquivoService;

import java.util.ArrayList;
import java.util.List;

public class ProdutoService {

    private List<Produto> produtos;

    public ProdutoService() {
        produtos = ArquivoService.carregar("produtos.dat");
    }

    public void criarProduto(String nome, Categoria categoria, String unidade, double estoqueMinimo, double estoqueIdeal) {
        produtos.add(new Produto(nome, categoria, unidade, estoqueMinimo, estoqueIdeal));
        ArquivoService.salvar("produtos.dat", produtos);
        System.out.println("Produto criado!");
    }

    public List<Produto> listarProdutos() {
        return produtos;
    }
}