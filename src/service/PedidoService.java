package service;

import model.ItemPedido;
import model.Pedido;
import model.Produto;
import util.ArquivoService;

import java.util.ArrayList;
import java.util.List;

public class PedidoService {

    private List<Pedido> pedidos;

    public PedidoService() {
        pedidos = ArquivoService.carregar("pedidos.dat");
        if (pedidos == null) pedidos = new ArrayList<>();
    }

    public Pedido criarPedido(String data) {
        Pedido pedido = new Pedido(data);
        pedidos.add(pedido);
        ArquivoService.salvar("pedidos.dat", pedidos);
        return pedido;
    }

    public void adicionarItem(Pedido pedido, Produto produto, double quantidade) {
        pedido.adicionarItem(new ItemPedido(produto, quantidade));
        ArquivoService.salvar("pedidos.dat", pedidos);
    }

    public List<Pedido> listarPedidos() {
        return pedidos;
    }
}