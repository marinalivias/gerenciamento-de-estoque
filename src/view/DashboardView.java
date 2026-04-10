package view;

import javafx.geometry.Insets;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import service.MovimentacaoService;
import service.ProdutoService;

public class DashboardView {

    private ProdutoService produtoService = ProdutoService.getInstance();
    private MovimentacaoService movimentacaoService = MovimentacaoService.getInstance();

    public VBox getView() {

        int total = produtoService.listarProdutos().size();

        long criticos = produtoService.listarProdutos().stream()
                .filter(p -> movimentacaoService.calcularStatus(p).equals("CRÍTICO"))
                .count();

        long baixos = produtoService.listarProdutos().stream()
                .filter(p -> movimentacaoService.calcularStatus(p).equals("BAIXO"))
                .count();

        long ok = total - criticos - baixos;

        VBox card1 = criarCard("Total", String.valueOf(total));
        VBox card2 = criarCard("Críticos", String.valueOf(criticos));
        VBox card3 = criarCard("Baixos", String.valueOf(baixos));

        HBox cards = new HBox(15, card1, card2, card3);

        PieChart chart = new PieChart();
        chart.getData().addAll(
                new PieChart.Data("OK", ok),
                new PieChart.Data("Baixo", baixos),
                new PieChart.Data("Crítico", criticos)
        );

        Label titulo = new Label("Dashboard");
        titulo.getStyleClass().add("titulo");

        VBox layout = new VBox(20, titulo, cards, chart);
        layout.setPadding(new Insets(20));

        return layout;
    }

    private VBox criarCard(String t, String v) {
        Label l1 = new Label(t);
        Label l2 = new Label(v);
        l2.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        VBox card = new VBox(l1, l2);
        card.getStyleClass().add("card");

        return card;
    }
}