package model;

import java.io.Serializable;

public class ItemPedido implements Serializable {
    private final Produto produto;
    private final double quantidade;
    private final String condicao;

    public ItemPedido(Produto produto, double quantidade, String condicao) {
        this.produto = produto;
        this.quantidade = quantidade;
        this.condicao = condicao;
    }

    public Produto getProduto() {
        return produto;
    }

    public double getQuantidade() {
        return quantidade;
    }

    public String getCondicao() {
        return condicao;
    }
}