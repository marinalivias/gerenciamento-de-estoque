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

        ComboBox<String> filtroCategoria = new ComboBox<>();
        filtroCategoria.setPromptText("Filtrar por categoria");

        filtroCategoria.getItems().add("Todas");

        produtoService.listarProdutos().stream()
                .map(Produto::getCategoria)
                .distinct()
                .forEach(filtroCategoria.getItems()::add);

        filtroCategoria.setValue("Todas");

        Label titulo = new Label("Estoque");
        titulo.getStyleClass().add("titulo");

        HBox header = new HBox(10, titulo, filtroCategoria);
        header.setAlignment(Pos.CENTER_LEFT);

        criarTabela();
        atualizarTabela(filtroCategoria);

        filtroCategoria.setOnAction(e -> atualizarTabela(filtroCategoria));

        VBox containerTabela = new VBox(tabela);
        containerTabela.getStyleClass().add("card");

        VBox layout = new VBox(15, header, containerTabela);
        layout.setPadding(new Insets(20));

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

        TableColumn<Produto, Double> idealCol = new TableColumn<>("Ideal");
        idealCol.setCellValueFactory(c ->
                new SimpleDoubleProperty(c.getValue().getEstoqueIdeal()).asObject()
        );

        TableColumn<Produto, Double> minCol = new TableColumn<>("Mínimo");
        minCol.setCellValueFactory(c ->
                new SimpleDoubleProperty(c.getValue().getEstoqueMinimo()).asObject()
        );

        TableColumn<Produto, String> statusCol = new TableColumn<>("Status");
        statusCol.setCellValueFactory(c ->
                new SimpleStringProperty(
                        movimentacaoService.calcularStatus(c.getValue())
                )
        );

        TableColumn<Produto, Void> acaoCol = new TableColumn<>("Ações");

        acaoCol.setCellFactory(param -> new TableCell<>() {

            private final Button entrada = new Button("+");
            private final Button saida = new Button("-");

            {
                entrada.getStyleClass().add("button");
                saida.getStyleClass().add("button");

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

                atualizarTabela(null);

            } catch (Exception e) {
                System.out.println("Valor inválido");
            }
        });
    }

    private void atualizarTabela(ComboBox<String> filtro) {

        if (filtro == null || filtro.getValue() == null || filtro.getValue().equals("Todas")) {
            tabela.setItems(FXCollections.observableArrayList(produtoService.listarProdutos()));
        } else {
            tabela.setItems(FXCollections.observableArrayList(
                    produtoService.listarProdutos().stream()
                            .filter(p -> filtro.getValue().equals(p.getCategoria()))
                            .toList()
            ));
        }
    }
}