package view;

import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Produto;
import service.MovimentacaoService;
import service.ProdutoService;

public class MovimentacaoView {

    private ProdutoService produtoService = ProdutoService.getInstance();
    private MovimentacaoService movimentacaoService = MovimentacaoService.getInstance();

    public void mostrar() {

        Stage stage = new Stage();

        ComboBox<Produto> produto = new ComboBox<>();
        produto.setItems(FXCollections.observableArrayList(produtoService.listarProdutos()));

        TextField quantidade = new TextField();
        quantidade.setPromptText("Quantidade");

        ComboBox<String> tipo = new ComboBox<>();
        tipo.setItems(FXCollections.observableArrayList("ENTRADA", "SAIDA"));

        Button salvar = new Button("Registrar");

        salvar.setOnAction(e -> {
            try {
                if (produto.getValue() == null || tipo.getValue() == null) {
                    System.out.println("Preencha todos os campos");
                    return;
                }

                double qtd = Double.parseDouble(quantidade.getText());

                if (tipo.getValue().equals("ENTRADA")) {
                    movimentacaoService.registrarEntrada(produto.getValue(), qtd);
                } else {
                    movimentacaoService.registrarSaida(produto.getValue(), qtd);
                }

                stage.close();

            } catch (Exception ex) {
                System.out.println("Erro na movimentação");
            }
        });

        VBox layout = new VBox(10, produto, quantidade, tipo, salvar);

        stage.setScene(new Scene(layout, 300, 250));
        stage.setTitle("Movimentação");
        stage.show();
    }
}