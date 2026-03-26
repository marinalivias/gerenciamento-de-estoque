package service;

import model.Pedido;
import model.ItemPedido;
import model.Produto;

import java.util.ArrayList;
import java.util.List;

public class PedidoService {

    private List<Pedido> pedidos = new ArrayList<>();

    public Pedido criarPedido(String data, String previsao) {
        Pedido pedido = new Pedido(data, previsao);
        pedidos.add(pedido);
        return pedido;
    }

    public void adicionarItem(Pedido pedido, Produto produto, double quantidade, String condicao) {
        ItemPedido item = new ItemPedido(produto, quantidade, condicao);
        pedido.adicionarItem(item);
    }

    public List<Pedido> listarPedidos() {
        return pedidos;
    }
}
