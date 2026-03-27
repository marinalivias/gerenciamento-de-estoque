package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Pedido implements Serializable {

    private String dataPedido;
    private String previsaoEntrega;
    private List<ItemPedido> itens;

    public Pedido(String dataPedido, String previsaoEntrega) {
        this.dataPedido = dataPedido;
        this.previsaoEntrega = previsaoEntrega;
        this.itens = new ArrayList<>();
    }

    public void adicionarItem(ItemPedido item) {
        itens.add(item);
    }

    public void mostrarPedido() {
        System.out.println("Data do pedido: " + dataPedido);
        System.out.println("Previsão de entrega: " + previsaoEntrega);
        System.out.println("Itens:");

        for (ItemPedido item : itens) {
            System.out.println("- " + item.getProduto().getNome()
                    + " | " + item.getQuantidade() + " " + item.getProduto().getUnidade()
                    + " | " + item.getCondicao());
        }
    }

    public String getDataPedido() {
        return dataPedido;
    }

    public String getPrevisaoEntrega() {
        return previsaoEntrega;
    }

    public List<ItemPedido> getItens() {
        return itens;
    }
}