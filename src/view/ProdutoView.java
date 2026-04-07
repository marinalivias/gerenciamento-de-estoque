package view;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
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
        titulo.setStyle("-fx-font-size: 22px; -fx-font-weight: bold;");

        HBox header = new HBox(titulo);
        header.setAlignment(Pos.CENTER_LEFT);
        header.setPadding(new Insets(0, 0, 10, 0));

        criarTabela();
        atualizarTabela();

        VBox containerTabela = new VBox(tabela);
        containerTabela.setStyle("""
            -fx-background-color: white;
            -fx-background-radius: 10;
            -fx-padding: 10;
        """);

        VBox layout = new VBox(15, header, containerTabela);
        layout.setPadding(new Insets(20));
        layout.setStyle("-fx-background-color: #f3f4f6;");

        return layout;
    }

    private void criarTabela() {

        tabela.getColumns().clear();

        TableColumn<Produto, String> nomeCol = new TableColumn<>("Produto");
        nomeCol.setCellValueFactory(c ->
                new SimpleStringProperty(c.getValue().getNome())
        );

        TableColumn<Produto, String> categoriaCol = new TableColumn<>("Categoria");
        categoriaCol.setCellValueFactory(c ->
                new SimpleStringProperty(
                        c.getValue().getCategoria() != null
                                ? c.getValue().getCategoria()
                                : "Sem categoria"
                )
        );

        TableColumn<Produto, Double> atualCol = new TableColumn<>("Estoque Atual");
        atualCol.setCellValueFactory(c ->
                new SimpleDoubleProperty(
                        movimentacaoService.calcularEstoque(c.getValue())
                ).asObject()
        );

        TableColumn<Produto, Double> idealCol = new TableColumn<>("Estoque Regulador");
        idealCol.setCellValueFactory(c ->
                new SimpleDoubleProperty(c.getValue().getEstoqueIdeal()).asObject()
        );

        TableColumn<Produto, Double> minCol = new TableColumn<>("Estoque Mínimo");
        minCol.setCellValueFactory(c ->
                new SimpleDoubleProperty(c.getValue().getEstoqueMinimo()).asObject()
        );

        TableColumn<Produto, String> statusCol = new TableColumn<>("Status");
        statusCol.setCellValueFactory(c ->
                new SimpleStringProperty(
                        movimentacaoService.calcularStatus(c.getValue())
                )
        );

        statusCol.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(String status, boolean empty) {
                super.updateItem(status, empty);

                if (empty || status == null) {
                    setText(null);
                    setStyle("");
                    return;
                }

                setText(status);

                switch (status) {
                    case "BAIXO" -> setStyle("-fx-text-fill: #ef4444; -fx-font-weight: bold;");
                    case "OK" -> setStyle("-fx-text-fill: #10b981; -fx-font-weight: bold;");
                    case "ALTO" -> setStyle("-fx-text-fill: #f59e0b; -fx-font-weight: bold;");
                }
            }
        });

        TableColumn<Produto, Double> consumoCol = new TableColumn<>("Gasto/Mês");
        consumoCol.setCellValueFactory(c ->
                new SimpleDoubleProperty(c.getValue().getQuantidadeGastaMes()).asObject()
        );

        TableColumn<Produto, Void> acaoCol = new TableColumn<>("Controle");

        acaoCol.setCellFactory(param -> new TableCell<>() {

            private final Button entrada = new Button("+");
            private final Button saida = new Button("-");

            {
                entrada.setStyle("-fx-background-color: #10b981; -fx-text-fill: white;");
                saida.setStyle("-fx-background-color: #ef4444; -fx-text-fill: white;");

                entrada.setOnAction(e -> {
                    Produto p = getTableView().getItems().get(getIndex());
                    abrirPopup(p, true);
                });

                saida.setOnAction(e -> {
                    Produto p = getTableView().getItems().get(getIndex());
                    abrirPopup(p, false);
                });
            }

            private final HBox box = new HBox(5, entrada, saida);

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : box);
            }
        });

        tabela.getColumns().addAll(
                nomeCol,
                categoriaCol,
                atualCol,
                idealCol,
                minCol,
                statusCol,
                consumoCol,
                acaoCol
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
        tabela.setItems(FXCollections.observableArrayList(produtoService.listarProdutos()));
    }
}