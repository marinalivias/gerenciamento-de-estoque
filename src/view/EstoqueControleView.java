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

        // 🔽 Seleção de produto
        ComboBox<Produto> produtoBox = new ComboBox<>();
        produtoBox.setItems(FXCollections.observableArrayList(produtoService.listarProdutos()));
        produtoBox.setPromptText("Selecione o produto");

        // 🔽 Tipo (Entrada/Saída)
        ComboBox<String> tipoBox = new ComboBox<>();
        tipoBox.setItems(FXCollections.observableArrayList("ENTRADA", "SAIDA"));
        tipoBox.setPromptText("Tipo de movimentação");

        // 🔽 Quantidade
        TextField quantidadeField = new TextField();
        quantidadeField.setPromptText("Quantidade");

        // 🔽 Botão
        Button registrarBtn = new Button("Registrar");

        Label status = new Label();

        // 🔽 Ação
        registrarBtn.setOnAction(e -> {
            try {
                Produto produto = produtoBox.getValue();
                String tipo = tipoBox.getValue();
                double qtd = Double.parseDouble(quantidadeField.getText());

                if (produto == null || tipo == null) {
                    status.setText("Preencha todos os campos!");
                    return;
                }

                if (tipo.equals("ENTRADA")) {
                    movimentacaoService.registrarEntrada(produto, qtd);
                } else {
                    movimentacaoService.registrarSaida(produto, qtd);
                }

                quantidadeField.clear();
                status.setText("Movimentação registrada!");

            } catch (Exception ex) {
                status.setText("Erro ao registrar!");
            }
        });

        // 🔽 Histórico
        ListView<String> historicoList = new ListView<>();

        Button atualizarHistorico = new Button("Atualizar Histórico");

        atualizarHistorico.setOnAction(e -> {
            historicoList.getItems().clear();

            movimentacaoService.listarMovimentacoes().forEach(m -> {
                historicoList.getItems().add(
                        m.getData() + " - " +
                                m.getProduto().getNome() + " - " +
                                m.getTipo() + " - " +
                                m.getQuantidade()
                );
            });
        });

        VBox layout = new VBox(10,
                new Label("Controle de Estoque"),
                produtoBox,
                tipoBox,
                quantidadeField,
                registrarBtn,
                status,
                new Separator(),
                new Label("Histórico"),
                atualizarHistorico,
                historicoList
        );

        layout.setPadding(new Insets(20));

        return layout;
    }
}