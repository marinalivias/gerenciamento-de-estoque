package view;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class EstoqueView {

    private BorderPane root = new BorderPane();

    public void mostrar(Stage stage) {

        root.setLeft(criarSidebar());
        root.setCenter(new DashboardView().getView()); // tela inicial

        Scene scene = new Scene(root, 1200, 600);

        stage.setScene(scene);
        stage.setTitle("Sistema de Estoque");
        stage.show();
    }

    private VBox criarSidebar() {

        Button dashboard = criarBotao("Dashboard", () ->
                trocarTela(new DashboardView().getView())
        );

        Button estoque = criarBotao("Estoque", () ->
                trocarTela(new ProdutoView().getView())
        );

        Button adicionar = criarBotao("Adicionar", () ->
                trocarTela(new AdicionarView().getView())
        );

        Button controle = criarBotao("Controle de Estoque", () ->
                trocarTela(new ControleView().getView())
        );

        Button pedidos = criarBotao("Pedidos", () ->
                trocarTela(new PedidoView2().getView())
        );

        VBox menu = new VBox(15, dashboard, estoque, adicionar, controle, pedidos);
        menu.setPadding(new Insets(20));
        menu.setStyle("-fx-background-color: #1f2937;");

        return menu;
    }

    private Button criarBotao(String texto, Runnable acao) {
        Button btn = new Button(texto);
        btn.setMaxWidth(Double.MAX_VALUE);

        btn.setStyle("""
            -fx-background-color: transparent;
            -fx-text-fill: white;
            -fx-font-size: 14px;
        """);

        btn.setOnMouseEntered(e ->
                btn.setStyle("-fx-background-color: #374151; -fx-text-fill: white;")
        );

        btn.setOnMouseExited(e ->
                btn.setStyle("-fx-background-color: transparent; -fx-text-fill: white;")
        );

        btn.setOnAction(e -> acao.run());

        return btn;
    }

    private void trocarTela(Pane tela) {
        root.setCenter(tela);
    }
}