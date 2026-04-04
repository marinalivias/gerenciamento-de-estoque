package model;

import java.io.Serializable;

public class ItemPedido implements Serializable {

    private final Produto produto;
    private final double quantidade;

    public ItemPedido(Produto produto, double quantidade) {
        this.produto = produto;
        this.quantidade = quantidade;
    }

    public Produto getProduto() { return produto; }
    public double getQuantidade() { return quantidade; }
}