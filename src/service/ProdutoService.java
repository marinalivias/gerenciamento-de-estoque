package service;

import model.Produto;
import util.ArquivoService;

import java.util.ArrayList;
import java.util.List;

public class ProdutoService {

    private static ProdutoService instance;

    private final List<Produto> produtos;

    private ProdutoService() {

        List<Produto> lista = ArquivoService.carregar("produtos.dat");

        if (lista == null) {
            lista = new ArrayList<>();
        }

        this.produtos = lista;
    }

    public static ProdutoService getInstance() {
        if (instance == null) {
            instance = new ProdutoService();
        }
        return instance;
    }

    public void adicionarProduto(Produto produto) {
        produtos.add(produto);
        ArquivoService.salvar("produtos.dat", produtos);
    }

    public List<Produto> listarProdutos() {
        return produtos;
    }
}