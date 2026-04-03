package service;

import model.Categoria;
import model.Produto;
import util.ArquivoService;

import java.util.List;

public class ProdutoService {

    private final List<Produto> produtos;

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