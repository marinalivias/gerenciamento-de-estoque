package view;

import javafx.animation.FadeTransition;
import javafx.geometry.Insets;
import javafx.scene.chart.*;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.*;
import javafx.util.Duration;
import service.MovimentacaoService;
import service.ProdutoService;

import java.util.HashMap;
import java.util.Map;

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

        // 🎨 CARDS
        HBox cards = new HBox(15,
                criarCard("Total de Produtos", String.valueOf(total), "#3b82f6"),
                criarCard("Produtos Críticos", String.valueOf(criticos), "#ef4444"),
                criarCard("Produtos Baixos", String.valueOf(baixos), "#f59e0b")
        );

        PieChart pieChart = new PieChart();
        pieChart.getData().addAll(
                new PieChart.Data("OK", ok),
                new PieChart.Data("Baixo", baixos),
                new PieChart.Data("Crítico", criticos)
        );
        pieChart.setTitle("Status do Estoque");
        pieChart.setPrefSize(350, 300);

        VBox pieCard = new VBox(pieChart);
        pieCard.getStyleClass().add("card");

        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();

        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
        barChart.setTitle("Mais movimentados");
        barChart.setPrefSize(450, 300);

        XYChart.Series<String, Number> series = new XYChart.Series<>();

        Map<String, Double> mapa = new HashMap<>();

        movimentacaoService.listarMovimentacoes().forEach(m -> {
            String nome = m.getProduto().getNome();
            mapa.put(nome, mapa.getOrDefault(nome, 0.0) + m.getQuantidade());
        });

        mapa.entrySet().stream()
                .sorted((a, b) -> Double.compare(b.getValue(), a.getValue()))
                .limit(5)
                .forEach(e ->
                        series.getData().add(new XYChart.Data<>(e.getKey(), e.getValue()))
                );

        barChart.getData().add(series);

        VBox barCard = new VBox(barChart);
        barCard.getStyleClass().add("card");

        HBox graficos = new HBox(30, pieCard, barCard);
        graficos.setPadding(new Insets(10));
        graficos.setStyle("-fx-alignment: center;");

        HBox.setHgrow(pieCard, Priority.ALWAYS);
        HBox.setHgrow(barCard, Priority.ALWAYS);

        pieCard.setMaxWidth(Double.MAX_VALUE);
        barCard.setMaxWidth(Double.MAX_VALUE);

        animarEntrada(pieCard);
        animarEntrada(barCard);

        ListView<String> historicoList = new ListView<>();

        movimentacaoService.listarMovimentacoes()
                .stream()
                .skip(Math.max(0,
                        movimentacaoService.listarMovimentacoes().size() - 5))
                .forEach(m -> historicoList.getItems().add(
                        m.getData() + " | " +
                                m.getProduto().getNome() + " | " +
                                m.getTipo() + " | " +
                                m.getQuantidade()
                ));

        historicoList.setPrefHeight(230);
        historicoList.setFocusTraversable(false);

        VBox historicoCard = new VBox(
                new Label("Últimas movimentações"),
                historicoList
        );
        historicoCard.setSpacing(10);
        historicoCard.getStyleClass().add("card");

        animarEntrada(historicoCard);

        Label titulo = new Label("Dashboard");
        titulo.getStyleClass().add("titulo");

        VBox layout = new VBox(20,
                titulo,
                cards,
                graficos,
                historicoCard
        );

        layout.setPadding(new Insets(20));

        return layout;
    }

    private VBox criarCard(String titulo, String valor, String cor) {

        Label l1 = new Label(titulo);
        Label l2 = new Label(valor);

        l2.setStyle("-fx-font-size: 26px; -fx-font-weight: bold;");

        VBox card = new VBox(5, l1, l2);
        card.setPadding(new Insets(15));

        card.setStyle(
                "-fx-background-color: white;" +
                        "-fx-background-radius: 12;" +
                        "-fx-border-radius: 12;" +
                        "-fx-border-color: " + cor + ";" +
                        "-fx-border-width: 0 0 0 5;"
        );

        animarEntrada(card);

        return card;
    }

    private void animarEntrada(VBox node) {
        FadeTransition fade = new FadeTransition(Duration.millis(500), node);
        fade.setFromValue(0);
        fade.setToValue(1);
        fade.play();
    }
}