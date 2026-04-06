package service;

import model.Movimentacao;
import model.Produto;
import util.ArquivoService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class MovimentacaoService {

    private static MovimentacaoService instance;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    private List<Movimentacao> movimentacoes;

    // construtor privado
    private MovimentacaoService() {
        movimentacoes = ArquivoService.carregar("movimentacoes.dat");
        if (movimentacoes == null) {
            movimentacoes = new ArrayList<>();
        }
    }

    // singleton
    public static MovimentacaoService getInstance() {
        if (instance == null) {
            instance = new MovimentacaoService();
        }
        return instance;
    }

    public void registrarEntrada(Produto produto, double quantidade) {
        String data = LocalDateTime.now().format(formatter);
        movimentacoes.add(new Movimentacao(produto, quantidade, "ENTRADA", data));
        ArquivoService.salvar("movimentacoes.dat", movimentacoes);
    }

    public void registrarSaida(Produto produto, double quantidade) {
        String data = LocalDateTime.now().format(formatter);
        movimentacoes.add(new Movimentacao(produto, quantidade, "SAIDA", data));
        ArquivoService.salvar("movimentacoes.dat", movimentacoes);
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

    public String calcularStatus(Produto produto) {
        double atual = calcularEstoque(produto);

        if (atual <= produto.getEstoqueMinimo()) return "CRÍTICO";
        else if (atual < produto.getEstoqueIdeal()) return "BAIXO";
        else return "OK";
    }
}