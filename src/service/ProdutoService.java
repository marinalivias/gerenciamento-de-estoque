package service;

import model.Categoria;
import model.Produto;
import util.ArquivoService;

import java.util.ArrayList;
import java.util.List;

public class ProdutoService {

    private static ProdutoService instance;

    private final List<Produto> produtos;

    // CONSTRUTOR PRIVADO
    private ProdutoService() {

        List<Produto> lista = ArquivoService.carregar("produtos.dat");

        if (lista == null) {
            lista = new ArrayList<>();
        }

        this.produtos = lista;
    }

    // SINGLETON
    public static ProdutoService getInstance() {
        if (instance == null) {
            instance = new ProdutoService();
        }
        return instance;
    }

    public void criarProduto(String nome, Categoria categoria, String unidade,
                             double estoqueMinimo, double estoqueIdeal) {

        produtos.add(new Produto(nome, categoria, unidade, estoqueMinimo, estoqueIdeal));
        ArquivoService.salvar("produtos.dat", produtos);
    }

    public List<Produto> listarProdutos() {
        return produtos;
    }
}