package model;

import java.io.Serializable;

public class Movimentacao implements Serializable {

    private Produto produto;
    private double quantidade;
    private String tipo; // "ENTRADA" ou "SAIDA"
    private String data;

    public Movimentacao(Produto produto, double quantidade, String tipo, String data) {
        this.produto = produto;
        this.quantidade = quantidade;
        this.tipo = tipo;
        this.data = data;
    }

    public Produto getProduto() {
        return produto;
    }

    public double getQuantidade() {
        return quantidade;
    }

    public String getTipo() {
        return tipo;
    }

    public String getData() {
        return data;
    }
}