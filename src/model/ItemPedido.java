package model;

public class ItemPedido {
    private Produto produto;
    private double quantidade;
    private String condicao;

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