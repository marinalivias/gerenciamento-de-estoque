package view;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import model.Produto;
import service.ProdutoService;

public class AdicionarView {

    private ProdutoService produtoService = ProdutoService.getInstance();

    public VBox getView() {

        Label titulo = new Label("Novo Produto");
        titulo.setStyle("-fx-font-size: 22px; -fx-font-weight: bold;");

        TextField nome = new TextField();
        nome.setPromptText("Nome do produto");

        TextField categoria = new TextField();
        categoria.setPromptText("Categoria");

        TextField minimo = new TextField();
        minimo.setPromptText("Estoque mínimo");

        TextField ideal = new TextField();
        ideal.setPromptText("Estoque regulador");

        TextField atual = new TextField();
        atual.setPromptText("Estoque atual (opcional)");

        ComboBox<String> controle = new ComboBox<>();
        controle.getItems().addAll("Semanal", "Quinzenal", "Mensal");
        controle.setValue("Mensal");

        Button salvar = new Button("Salvar");
        salvar.setStyle("-fx-background-color: #10b981; -fx-text-fill: white;");

        salvar.setOnAction(e -> {
            try {

                Produto p = new Produto(
                        nome.getText(),
                        categoria.getText(),
                        Double.parseDouble(minimo.getText()),
                        Double.parseDouble(ideal.getText())
                );

                if (!atual.getText().isEmpty()) {
                    p.setEstoqueAtual(Double.parseDouble(atual.getText()));
                }

                p.setControle(controle.getValue());

                produtoService.adicionarProduto(p);

                nome.clear();
                categoria.clear();
                minimo.clear();
                ideal.clear();
                atual.clear();
                controle.setValue("Mensal");

            } catch (Exception ex) {
                System.out.println("Erro ao salvar produto");
            }
        });

        GridPane form = new GridPane();
        form.setVgap(10);
        form.setHgap(10);

        form.add(new Label("Nome"), 0, 0);
        form.add(nome, 1, 0);

        form.add(new Label("Categoria"), 0, 1);
        form.add(categoria, 1, 1);

        form.add(new Label("Mínimo"), 0, 2);
        form.add(minimo, 1, 2);

        form.add(new Label("Regulador"), 0, 3);
        form.add(ideal, 1, 3);

        form.add(new Label("Estoque Atual"), 0, 4);
        form.add(atual, 1, 4);

        form.add(new Label("Controle"), 0, 5);
        form.add(controle, 1, 5);

        VBox container = new VBox(20, titulo, form, salvar);
        container.setPadding(new Insets(20));
        container.setStyle("""
            -fx-background-color: white;
            -fx-background-radius: 10;
        """);

        VBox layout = new VBox(container);
        layout.setPadding(new Insets(20));
        layout.setStyle("-fx-background-color: #f3f4f6;");

        return layout;
    }
}