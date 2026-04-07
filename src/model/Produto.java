package model;

import java.io.Serializable;

public class Produto implements Serializable {

    private String nome;
    private String categoria;

    private double estoqueMinimo;
    private double estoqueIdeal;
    private double estoqueAtual;

    private String controle;
    private double quantidadeGastaMes;

    public Produto(String nome, String categoria, double estoqueMinimo, double estoqueIdeal) {
        this.nome = nome;
        this.categoria = categoria;
        this.estoqueMinimo = estoqueMinimo;
        this.estoqueIdeal = estoqueIdeal;
        this.estoqueAtual = 0;
        this.controle = "Mensal";
        this.quantidadeGastaMes = 0;
    }

    public String getNome() { return nome; }
    public String getCategoria() { return categoria; }
    public double getEstoqueMinimo() { return estoqueMinimo; }
    public double getEstoqueIdeal() { return estoqueIdeal; }
    public double getEstoqueAtual() { return estoqueAtual; }
    public String getControle() { return controle; }
    public double getQuantidadeGastaMes() { return quantidadeGastaMes; }

    public void setNome(String nome) { this.nome = nome; }
    public void setCategoria(String categoria) { this.categoria = categoria; }
    public void setEstoqueAtual(double estoqueAtual) { this.estoqueAtual = estoqueAtual; }
    public void setControle(String controle) { this.controle = controle; }
    public void setQuantidadeGastaMes(double quantidadeGastaMes) {
        this.quantidadeGastaMes = quantidadeGastaMes;
    }
}