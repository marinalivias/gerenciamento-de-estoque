package service;

import model.Movimentacao;
import model.Produto;
import util.ArquivoService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class MovimentacaoService {

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    private List<Movimentacao> movimentacoes;

    public String calcularStatus(Produto produto) {
        double atual = calcularEstoque(produto);

        if (atual <= produto.getEstoqueMinimo()) {
            return "CRÍTICO";
        } else if (atual < produto.getEstoqueIdeal()) {
            return "BAIXO";
        } else {
            return "OK";
        }
    }

    public MovimentacaoService() {
        movimentacoes = ArquivoService.carregar("movimentacoes.dat");

        if (movimentacoes == null) {
            movimentacoes = new java.util.ArrayList<>();
        }
    }

    public void registrarEntrada(Produto produto, double quantidade) {
        String data = LocalDateTime.now().format(formatter);

        movimentacoes.add(new Movimentacao(produto, quantidade, "ENTRADA", data));
        ArquivoService.salvar("movimentacoes.dat", movimentacoes);

        System.out.println("Entrada registrada!");
    }

    public void registrarSaida(Produto produto, double quantidade) {
        String data = LocalDateTime.now().format(formatter);

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

    public double calcularQuantidadeReposicao(Produto produto) {
        double atual = calcularEstoque(produto);
        double ideal = produto.getEstoqueIdeal();

        if (atual >= ideal) return 0;

        return ideal - atual;
    }

    public List<Movimentacao> listarMovimentacoes() {
        return movimentacoes;
    }
}