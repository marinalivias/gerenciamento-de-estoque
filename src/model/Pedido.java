package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Pedido implements Serializable {

    private final String dataPedido;
    private final List<ItemPedido> itens;

    public Pedido(String dataPedido) {
        this.dataPedido = dataPedido;
        this.itens = new ArrayList<>();
    }

    public void adicionarItem(ItemPedido item) {
        itens.add(item);
    }

    public void mostrarPedido() {
        System.out.println("Data do pedido: " + dataPedido);
        System.out.println("Itens:");

        for (ItemPedido item : itens) {
            System.out.println("- " +
                    item.getProduto().getNome() +
                    " | Categoria: " + item.getProduto().getCategoria().getNome() +
                    " | " + item.getQuantidade() +
                    " " + item.getProduto().getUnidade());
        }
    }

    public String getDataPedido() {
        return dataPedido;
    }

    public List<ItemPedido> getItens() {
        return itens;
    }
}