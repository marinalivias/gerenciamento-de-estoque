package view;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import model.Produto;
import service.MovimentacaoService;
import service.ProdutoService;

public class AdicionarView {

    private ProdutoService produtoService = ProdutoService.getInstance();
    private MovimentacaoService movimentacaoService = MovimentacaoService.getInstance();

    public VBox getView() {

        Label titulo = new Label("Novo Produto");
        titulo.getStyleClass().add("titulo");

        TextField nome = new TextField();
        nome.setPromptText("Nome do produto");

        TextField categoria = new TextField();
        categoria.setPromptText("Categoria");

        TextField minimo = new TextField();
        minimo.setPromptText("Estoque mínimo");

        TextField ideal = new TextField();
        ideal.setPromptText("Estoque regulador");

        TextField atual = new TextField();
        atual.setPromptText("Estoque inicial");

        ComboBox<String> controle = new ComboBox<>();
        controle.getItems().addAll("Semanal", "Quinzenal", "Mensal");
        controle.setValue("Mensal");

        Button salvar = new Button("Salvar");

        Label status = new Label();

        salvar.setOnAction(e -> {
            try {

                Produto p = new Produto(
                        nome.getText(),
                        categoria.getText(),
                        Double.parseDouble(minimo.getText()),
                        Double.parseDouble(ideal.getText())
                );

                p.setControle(controle.getValue());

                produtoService.adicionarProduto(p);

                if (!atual.getText().isEmpty()) {
                    double qtd = Double.parseDouble(atual.getText());
                    movimentacaoService.registrarEntrada(p, qtd);
                }

                status.setText("Produto cadastrado!");

                nome.clear();
                categoria.clear();
                minimo.clear();
                ideal.clear();
                atual.clear();
                controle.setValue("Mensal");

            } catch (Exception ex) {
                status.setText("Erro ao cadastrar produto");
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

        form.add(new Label("Estoque Inicial"), 0, 4);
        form.add(atual, 1, 4);

        form.add(new Label("Controle"), 0, 5);
        form.add(controle, 1, 5);

        VBox card = new VBox(15, titulo, form, salvar, status);
        card.getStyleClass().add("card");

        VBox layout = new VBox(card);
        layout.setPadding(new Insets(20));

        return layout;
    }
}