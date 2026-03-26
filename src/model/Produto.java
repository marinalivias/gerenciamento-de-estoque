package model;

public class Produto {
    private String nome;
    private Categoria categoria;
    private String unidade;

    public Produto(String nome, Categoria categoria, String unidade) {
        this.nome = nome;
        this.categoria = categoria;
        this.unidade = unidade;
    }

    public String getNome() {
        return nome;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public String getUnidade() {
        return unidade;
    }
}
