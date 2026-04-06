package view;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import service.CategoriaService;

public class CategoriaView {

    private CategoriaService categoriaService = new CategoriaService();

    public void mostrar() {

        Stage stage = new Stage();

        TextField nome = new TextField();
        nome.setPromptText("Nome da Categoria");

        Button salvar = new Button("Salvar");

        salvar.setOnAction(e -> {
            categoriaService.criarCategoria(nome.getText());
            nome.clear();
        });

        VBox layout = new VBox(10, nome, salvar);

        stage.setScene(new Scene(layout, 250, 150));
        stage.setTitle("Categorias");
        stage.show();
    }
}