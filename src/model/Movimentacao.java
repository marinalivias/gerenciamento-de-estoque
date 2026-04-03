package model;

import java.io.Serializable;

public class Movimentacao implements Serializable {

    private final Produto produto;
    private final double quantidade;
    private final String tipo; // "ENTRADA" ou "SAIDA"
    private final String data;

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