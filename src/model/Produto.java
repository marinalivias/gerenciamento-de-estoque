package model;

public class Produto {

    private String nome;
    private Categoria categoria;
    private String unidade;
    private double estoqueMinimo;
    private double estoqueIdeal;

    public Produto(String nome, Categoria categoria, String unidade, double estoqueMinimo, double estoqueIdeal) {
        this.nome = nome;
        this.categoria = categoria;
        this.unidade = unidade;
        this.estoqueMinimo = estoqueMinimo;
        this.estoqueIdeal = estoqueIdeal;
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

    public double getEstoqueMinimo() {
        return estoqueMinimo;
    }

    public double getEstoqueIdeal() {
        return estoqueIdeal;
    }
}