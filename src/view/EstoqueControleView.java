package view;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import model.Produto;
import service.MovimentacaoService;
import service.ProdutoService;

public class EstoqueControleView {

    private ProdutoService produtoService = ProdutoService.getInstance();
    private MovimentacaoService movimentacaoService = MovimentacaoService.getInstance();

    public VBox getView() {

        ComboBox<Produto> produto = new ComboBox<>();
        produto.setItems(FXCollections.observableArrayList(produtoService.listarProdutos()));

        ComboBox<String> tipo = new ComboBox<>();
        tipo.setItems(FXCollections.observableArrayList("ENTRADA", "SAIDA"));

        TextField qtd = new TextField();
        qtd.setPromptText("Quantidade");

        Button registrar = new Button("Registrar");
        Button atualizar = new Button("Atualizar Histórico");

        Label status = new Label();

        registrar.setOnAction(e -> {
            try {
                double q = Double.parseDouble(qtd.getText());

                if (tipo.getValue().equals("ENTRADA")) {
                    movimentacaoService.registrarEntrada(produto.getValue(), q);
                } else {
                    if (!movimentacaoService.registrarSaida(produto.getValue(), q)) {
                        status.setText("Estoque insuficiente");
                        return;
                    }
                }

                status.setText("Sucesso!");
                qtd.clear();

            } catch (Exception ex) {
                status.setText("Erro");
            }
        });

        ListView<String> lista = new ListView<>();

        atualizar.setOnAction(e -> {
            lista.getItems().clear();
            movimentacaoService.listarMovimentacoes().forEach(m ->
                    lista.getItems().add(
                            m.getData() + " - " +
                                    m.getProduto().getNome() + " - " +
                                    m.getTipo() + " - " +
                                    m.getQuantidade()
                    )
            );
        });

        VBox form = new VBox(produto, tipo, qtd, registrar, status);
        form.getStyleClass().add("card");

        VBox hist = new VBox(atualizar, lista);
        hist.getStyleClass().add("card");

        Label titulo = new Label("Controle de Estoque");
        titulo.getStyleClass().add("titulo");

        VBox layout = new VBox(20, titulo, form, hist);
        layout.setPadding(new Insets(20));

        return layout;
    }
}