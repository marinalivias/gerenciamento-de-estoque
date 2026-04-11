package view;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

public class EstoqueView {

    private BorderPane root = new BorderPane();
    private Button botaoAtivo;

    private boolean colapsado = false;
    private VBox sidebar;

    private List<Button> botoesMenu = new ArrayList<>();

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

        Button toggle = new Button("☰");
        toggle.getStyleClass().add("toggle-button");

        Button dashboard = criarBotao("Dashboard", "/icons/dashboard.png", () ->
                trocarTela(new DashboardView().getView())
        );

        Button estoque = criarBotao("Estoque", "/icons/home.png", () ->
                trocarTela(new ProdutoView().getView())
        );

        Button adicionar = criarBotao("Adicionar", "/icons/add.png", () ->
                trocarTela(new AdicionarView().getView())
        );

        Button controle = criarBotao("Controle", "/icons/board.png", () ->
                trocarTela(new EstoqueControleView().getView())
        );

        Button pedidos = criarBotao("Pedidos", "/icons/note.png", () ->
                trocarTela(new PedidoView().getView())
        );

        Button darkMode = criarBotao("", "/icons/moon.png", () -> {
            if (root.getStyleClass().contains("dark")) {
                root.getStyleClass().remove("dark");
            } else {
                root.getStyleClass().add("dark");
            }
        });

        darkMode.getStyleClass().add("dark-button");

        botoesMenu.add(dashboard);
        botoesMenu.add(estoque);
        botoesMenu.add(adicionar);
        botoesMenu.add(controle);
        botoesMenu.add(pedidos);

        dashboard.getStyleClass().add("active");
        botaoAtivo = dashboard;

        VBox menuTop = new VBox(10,
                toggle,
                dashboard,
                estoque,
                adicionar,
                controle,
                pedidos
        );

        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);

        sidebar = new VBox(10, menuTop, spacer, darkMode);
        sidebar.setPadding(new Insets(20));
        sidebar.getStyleClass().add("sidebar");
        sidebar.setPrefWidth(230);

        toggle.setOnAction(e -> alternarSidebar());

        return sidebar;
    }

    private Button criarBotao(String texto, String caminhoIcone, Runnable acao) {

        Button btn = new Button(texto);
        btn.setMaxWidth(Double.MAX_VALUE);
        btn.setAlignment(Pos.CENTER_LEFT);
        btn.setMinHeight(42);

        btn.getStyleClass().add("menu-button");

        try {
            ImageView icon = new ImageView(
                    new Image(getClass().getResourceAsStream(caminhoIcone))
            );

            icon.setFitWidth(22);
            icon.setFitHeight(22);

            btn.setGraphic(icon);

            btn.setGraphicTextGap(14);

        } catch (Exception e) {
            System.out.println("Erro ao carregar ícone: " + caminhoIcone);
        }

        btn.setOnAction(e -> {

            if (botaoAtivo != null) {
                botaoAtivo.getStyleClass().remove("active");
            }

            btn.getStyleClass().add("active");
            botaoAtivo = btn;

            acao.run();
        });

        return btn;
    }

    private void alternarSidebar() {

        colapsado = !colapsado;

        double larguraAtual = sidebar.getWidth();
        double novaLargura = colapsado ? 70 : 230;

        Timeline animacao = new Timeline(
                new KeyFrame(Duration.millis(250),
                        new KeyValue(sidebar.prefWidthProperty(), novaLargura)
                )
        );

        animacao.play();

        for (Button btn : botoesMenu) {

            if (colapsado) {
                btn.setText("");
                Tooltip.install(btn, new Tooltip(getNomeBotao(btn)));
            } else {
                btn.setText(getNomeBotao(btn));
                Tooltip.uninstall(btn, null);
            }
        }
    }

    private String getNomeBotao(Button btn) {

        if (btn == botoesMenu.get(0)) return "Dashboard";
        if (btn == botoesMenu.get(1)) return "Estoque";
        if (btn == botoesMenu.get(2)) return "Adicionar";
        if (btn == botoesMenu.get(3)) return "Controle";
        if (btn == botoesMenu.get(4)) return "Pedidos";

        return "";
    }

    private void trocarTela(Pane novaTela) {

        Pane telaAtual = (Pane) root.getCenter();

        if (telaAtual != null) {
            javafx.animation.FadeTransition fadeOut =
                    new javafx.animation.FadeTransition(Duration.millis(200), telaAtual);

            fadeOut.setFromValue(1);
            fadeOut.setToValue(0);

            fadeOut.setOnFinished(e -> {

                root.setCenter(novaTela);

                javafx.animation.FadeTransition fadeIn =
                        new javafx.animation.FadeTransition(Duration.millis(200), novaTela);

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