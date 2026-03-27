package service;

import model.Categoria;
import java.util.ArrayList;
import java.util.List;

public class CategoriaService {

    private List<Categoria> categorias = new ArrayList<>();

    public void criarCategoria(String nome) {
        Categoria categoria = new Categoria(nome);
        categorias.add(categoria);
        System.out.println("Categoria criada!");
    }

    public List<Categoria> listarCategorias() {
        return categorias;
    }
}