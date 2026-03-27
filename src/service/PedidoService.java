package service;

import model.Pedido;
import model.ItemPedido;
import model.Produto;
import util.ArquivoService;

import java.util.ArrayList;
import java.util.List;

public class PedidoService {

    private List<Pedido> pedidos;

    public PedidoService() {
        pedidos = ArquivoService.carregar("pedidos.dat");
    }

    public Pedido criarPedido(String data, String previsao) {
        Pedido pedido = new Pedido(data, previsao);
        pedidos.add(pedido);
        ArquivoService.salvar("pedidos.dat", pedidos);
        return pedido;
    }

    public void adicionarItem(Pedido pedido, Produto produto, double quantidade, String condicao) {
        pedido.adicionarItem(new ItemPedido(produto, quantidade, condicao));
        ArquivoService.salvar("pedidos.dat", pedidos);
    }

    public List<Pedido> listarPedidos() {
        return pedidos;
    }
}