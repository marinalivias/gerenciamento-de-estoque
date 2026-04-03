package service;

import model.Categoria;
import util.ArquivoService;

import java.util.List;

public class CategoriaService {

    private final List<Categoria> categorias;

    public CategoriaService() {
        categorias = ArquivoService.carregar("categorias.dat");
    }

    public void criarCategoria(String nome) {
        categorias.add(new Categoria(nome));
        ArquivoService.salvar("categorias.dat", categorias);
        System.out.println("Categoria criada!");
    }

    public List<Categoria> listarCategorias() {
        return categorias;
    }
}