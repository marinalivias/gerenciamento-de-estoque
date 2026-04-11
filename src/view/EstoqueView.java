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
        root.setCenter(new DashboardView().getView());

        Scene scene = new Scene(root, 1200, 600);

        scene.getStylesheets().add(
                getClass().getResource("/style.css").toExternalForm()
        );

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

        Button controle = criarBotao("Controle", () ->
                trocarTela(new EstoqueControleView().getView())
        );

        Button pedidos = criarBotao("Pedidos", () ->
                trocarTela(new PedidoView().getView())
        );

        Button darkMode = new Button("🌙");
        darkMode.setOnAction(e -> {
            if (root.getStyleClass().contains("dark")) {
                root.getStyleClass().remove("dark");
            } else {
                root.getStyleClass().add("dark");
            }
        });

        VBox menu = new VBox(15, dashboard, estoque, adicionar, controle, pedidos, darkMode);
        menu.setPadding(new Insets(20));
        menu.getStyleClass().add("sidebar");

        return menu;
    }

    private Button criarBotao(String texto, Runnable acao) {
        Button btn = new Button(texto);
        btn.setMaxWidth(Double.MAX_VALUE);

        btn.getStyleClass().add("menu-button");
        btn.setOnAction(e -> acao.run());

        return btn;
    }

    private void trocarTela(Pane novaTela) {

        Pane telaAtual = (Pane) root.getCenter();

        if (telaAtual != null) {
            javafx.animation.FadeTransition fadeOut =
                    new javafx.animation.FadeTransition(javafx.util.Duration.millis(200), telaAtual);

            fadeOut.setFromValue(1);
            fadeOut.setToValue(0);

            fadeOut.setOnFinished(e -> {

                root.setCenter(novaTela);

                javafx.animation.FadeTransition fadeIn =
                        new javafx.animation.FadeTransition(javafx.util.Duration.millis(200), novaTela);

                fadeIn.setFromValue(0);
                fadeIn.setToValue(1);
                fadeIn.play();
            });

            fadeOut.play();

        } else {
            root.setCenter(novaTela);
        }
    }
}