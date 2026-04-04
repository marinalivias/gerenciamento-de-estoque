package model;

import java.io.Serializable;

public class Produto implements Serializable {

    private final String nome;
    private final Categoria categoria;
    private final String unidade;
    private final double estoqueMinimo;
    private final double estoqueIdeal;

    private int id;
    private String status;
    private double quantidadeGastaMes;

    public Produto(String nome, Categoria categoria, String unidade, double estoqueMinimo, double estoqueIdeal) {
        this.nome = nome;
        this.categoria = categoria;
        this.unidade = unidade;
        this.estoqueMinimo = estoqueMinimo;
        this.estoqueIdeal = estoqueIdeal;
        this.quantidadeGastaMes = 0;
    }

    public String getNome() { return nome; }
    public Categoria getCategoria() { return categoria; }
    public String getUnidade() { return unidade; }
    public double getEstoqueMinimo() { return estoqueMinimo; }
    public double getEstoqueIdeal() { return estoqueIdeal; }
    public int getId() { return id; }
    public String getStatus() { return status; }
    public double getQuantidadeGastaMes() { return quantidadeGastaMes; }

    public void setQuantidadeGastaMes(double quantidadeGastaMes) {
        this.quantidadeGastaMes = quantidadeGastaMes;
    }
}