package service;

import model.Movimentacao;
import model.Produto;
import util.ArquivoService;

import java.util.ArrayList;
import java.util.List;

public class MovimentacaoService {

    private List<Movimentacao> movimentacoes;

    public double calcularQuantidadeReposicao(Produto produto) {
        double atual = calcularEstoque(produto);
        double ideal = produto.getEstoqueIdeal();

        if (atual >= ideal) return 0;

        return ideal - atual;
    }

    public MovimentacaoService() {
        movimentacoes = ArquivoService.carregar("movimentacoes.dat");
    }

    public void registrarEntrada(Produto produto, double quantidade, String data) {
        movimentacoes.add(new Movimentacao(produto, quantidade, "ENTRADA", data));
        ArquivoService.salvar("movimentacoes.dat", movimentacoes);
        System.out.println("Entrada registrada!");
    }

    public void registrarSaida(Produto produto, double quantidade, String data) {
        movimentacoes.add(new Movimentacao(produto, quantidade, "SAIDA", data));
        ArquivoService.salvar("movimentacoes.dat", movimentacoes);
        System.out.println("Saída registrada!");
    }

    public double calcularEstoque(Produto produto) {
        double total = 0;

        for (Movimentacao m : movimentacoes) {
            if (m.getProduto().equals(produto)) {
                if (m.getTipo().equals("ENTRADA")) {
                    total += m.getQuantidade();
                } else {
                    total -= m.getQuantidade();
                }
            }
        }

        return total;
    }

    public boolean precisaRepor(Produto produto) {
        return calcularEstoque(produto) <= produto.getEstoqueMinimo();
    }

    public List<Movimentacao> listarMovimentacoes() {
        return movimentacoes;
    }
}