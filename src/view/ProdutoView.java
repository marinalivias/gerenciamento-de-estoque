package view;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import model.Produto;
import service.MovimentacaoService;
import service.ProdutoService;

public class ProdutoView {

    private ProdutoService produtoService = ProdutoService.getInstance();
    private MovimentacaoService movimentacaoService = MovimentacaoService.getInstance();

    private TableView<Produto> tabela = new TableView<>();

    public VBox getView() {

        Label titulo = new Label("Controle de Estoque");
        titulo.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        criarTabela();
        atualizarTabela();

        VBox layout = new VBox(15, titulo, tabela);
        layout.setPadding(new Insets(20));
        layout.setStyle("-fx-background-color: #f3f4f6;");

        return layout;
    }

    private void criarTabela() {

        tabela.getColumns().clear();

        // Nome
        TableColumn<Produto, String> nomeCol = new TableColumn<>("Nome");
        nomeCol.setCellValueFactory(c ->
                new SimpleStringProperty(c.getValue().getNome())
        );

        // Unidade
        TableColumn<Produto, String> unidadeCol = new TableColumn<>("Unidade");
        unidadeCol.setCellValueFactory(c ->
                new SimpleStringProperty(c.getValue().getUnidade())
        );

        // Estoque mínimo
        TableColumn<Produto, Double> minCol = new TableColumn<>("Mínimo");
        minCol.setCellValueFactory(c ->
                new SimpleDoubleProperty(c.getValue().getEstoqueMinimo()).asObject()
        );

        // Estoque ideal
        TableColumn<Produto, Double> idealCol = new TableColumn<>("Regulador");
        idealCol.setCellValueFactory(c ->
                new SimpleDoubleProperty(c.getValue().getEstoqueIdeal()).asObject()
        );

        // Estoque atual
        TableColumn<Produto, Double> atualCol = new TableColumn<>("Atual");
        atualCol.setCellValueFactory(c ->
                new SimpleDoubleProperty(
                        movimentacaoService.calcularEstoque(c.getValue())
                ).asObject()
        );

        // Status
        TableColumn<Produto, String> statusCol = new TableColumn<>("Status");
        statusCol.setCellValueFactory(c ->
                new SimpleStringProperty(
                        movimentacaoService.calcularStatus(c.getValue())
                )
        );

        // Consumo mensal
        TableColumn<Produto, Double> consumoCol = new TableColumn<>("Gasto/Mês");
        consumoCol.setCellValueFactory(c ->
                new SimpleDoubleProperty(c.getValue().getQuantidadeGastaMes()).asObject()
        );

        // 🔥 AÇÕES
        TableColumn<Produto, Void> acaoCol = new TableColumn<>("Controle");

        acaoCol.setCellFactory(param -> new TableCell<>() {

            private final Button entrada = new Button("+");
            private final Button saida = new Button("-");

            {
                entrada.setOnAction(e -> {
                    Produto p = getTableView().getItems().get(getIndex());
                    abrirPopup(p, true);
                });

                saida.setOnAction(e -> {
                    Produto p = getTableView().getItems().get(getIndex());
                    abrirPopup(p, false);
                });

                entrada.setStyle("-fx-background-color: #10b981; -fx-text-fill: white;");
                saida.setStyle("-fx-background-color: #ef4444; -fx-text-fill: white;");
            }

            private final HBox box = new HBox(5, entrada, saida);

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : box);
            }
        });

        tabela.getColumns().addAll(
                nomeCol, unidadeCol, minCol, idealCol,
                atualCol, statusCol, consumoCol, acaoCol
        );

        tabela.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    private void abrirPopup(Produto produto, boolean entrada) {

        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle(entrada ? "Entrada" : "Saída");
        dialog.setHeaderText(produto.getNome());
        dialog.setContentText("Quantidade:");

        dialog.showAndWait().ifPresent(valor -> {
            try {
                double qtd = Double.parseDouble(valor);

                if (entrada) {
                    movimentacaoService.registrarEntrada(produto, qtd);
                } else {
                    movimentacaoService.registrarSaida(produto, qtd);
                }

                atualizarTabela();

            } catch (Exception e) {
                System.out.println("Valor inválido");
            }
        });
    }

    private void atualizarTabela() {
        tabela.getItems().clear();
        tabela.getItems().addAll(produtoService.listarProdutos());
    }
}