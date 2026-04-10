package view;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import model.Produto;
import service.MovimentacaoService;
import service.ProdutoService;

public class MovimentacaoView {

    private ProdutoService produtoService = ProdutoService.getInstance();
    private MovimentacaoService movimentacaoService = MovimentacaoService.getInstance();

    public VBox getView() {

        ComboBox<Produto> produto = new ComboBox<>();
        produto.setItems(FXCollections.observableArrayList(produtoService.listarProdutos()));
        produto.setPromptText("Selecione o produto");

        TextField quantidade = new TextField();
        quantidade.setPromptText("Quantidade");

        ComboBox<String> tipo = new ComboBox<>();
        tipo.setItems(FXCollections.observableArrayList("ENTRADA", "SAIDA"));
        tipo.setPromptText("Tipo");

        Button salvar = new Button("Registrar");

        Label status = new Label();

        salvar.setOnAction(e -> {
            try {
                if (produto.getValue() == null || tipo.getValue() == null || quantidade.getText().isEmpty()) {
                    status.setText("Preencha todos os campos!");
                    return;
                }

                double qtd = Double.parseDouble(quantidade.getText());

                if (tipo.getValue().equals("ENTRADA")) {

                    movimentacaoService.registrarEntrada(produto.getValue(), qtd);
                    status.setText("Entrada registrada!");

                } else {

                    boolean sucesso = movimentacaoService.registrarSaida(produto.getValue(), qtd);

                    if (!sucesso) {
                        status.setText("Erro: estoque insuficiente!");
                        return;
                    }

                    status.setText("Saída registrada!");
                }

                quantidade.clear();

            } catch (NumberFormatException ex) {
                status.setText("Quantidade inválida!");
            } catch (Exception ex) {
                status.setText("Erro na movimentação");
            }
        });

        VBox layout = new VBox(10,
                new Label("Movimentação de Estoque"),
                produto,
                quantidade,
                tipo,
                salvar,
                status
        );

        layout.setPadding(new Insets(20));

        return layout;
    }
}