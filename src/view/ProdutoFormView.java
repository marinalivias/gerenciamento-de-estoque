package view;

import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Categoria;
import service.CategoriaService;
import service.ProdutoService;

public class ProdutoFormView {

    private ProdutoService produtoService = new ProdutoService();
    private CategoriaService categoriaService = new CategoriaService();

    public void mostrar() {

        Stage stage = new Stage();

        TextField nome = new TextField();
        nome.setPromptText("Nome");

        TextField unidade = new TextField();
        unidade.setPromptText("Unidade");

        TextField minimo = new TextField();
        minimo.setPromptText("Estoque Mínimo");

        TextField ideal = new TextField();
        ideal.setPromptText("Estoque Regulador");

        ComboBox<Categoria> categoria = new ComboBox<>();
        categoria.setItems(FXCollections.observableArrayList(categoriaService.listarCategorias()));

        Button salvar = new Button("Salvar");

        salvar.setOnAction(e -> {
            try {
                produtoService.criarProduto(
                        nome.getText(),
                        categoria.getValue(),
                        unidade.getText(),
                        Double.parseDouble(minimo.getText()),
                        Double.parseDouble(ideal.getText())
                );
                stage.close();
            } catch (Exception ex) {
                System.out.println("Erro ao cadastrar produto");
            }
        });

        VBox layout = new VBox(10, nome, unidade, minimo, ideal, categoria, salvar);

        stage.setScene(new Scene(layout, 300, 300));
        stage.setTitle("Novo Produto");
        stage.show();
    }
}