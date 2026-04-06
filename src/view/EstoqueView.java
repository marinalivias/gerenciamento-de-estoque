package view;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import model.Produto;
import service.MovimentacaoService;
import service.ProdutoService;

public class EstoqueView {

    private ProdutoService produtoService = new ProdutoService();
    private MovimentacaoService movimentacaoService = new MovimentacaoService();

    private TableView<Produto> tabela = new TableView<>();

    public void mostrar(Stage stage) {

        BorderPane root = new BorderPane();

        root.setLeft(criarSidebar());
        root.setCenter(criarConteudo());

        Scene scene = new Scene(root, 1200, 600);

        stage.setScene(scene);
        stage.setTitle("Sistema de Estoque");
        stage.show();
    }

    // SIDEBAR
    private VBox criarSidebar() {

        Label titulo = new Label("📦 Sistema");
        titulo.setStyle("-fx-text-fill: white; -fx-font-size: 18px; -fx-font-weight: bold;");

        Button dashboard = criarBotaoMenu("Dashboard");
        Button produtos = criarBotaoMenu("Produtos");
        Button pedidos = criarBotaoMenu("Pedidos");
        Button relatorios = criarBotaoMenu("Relatórios");

        VBox menu = new VBox(15, titulo, dashboard, produtos, pedidos, relatorios);
        menu.setPadding(new Insets(20));
        menu.setStyle("-fx-background-color: #1f2937;");

        return menu;
    }

    private Button criarBotaoMenu(String texto) {
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

        return btn;
    }

    // CONTEÚDO
    private VBox criarConteudo() {

        Label titulo = new Label("Dashboard de Estoque");
        titulo.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        HBox cards = criarCards();

        criarTabela();
        carregarDados();

        VBox conteudo = new VBox(15, titulo, cards, tabela);
        conteudo.setPadding(new Insets(20));
        conteudo.setStyle("-fx-background-color: #f3f4f6;");

        return conteudo;
    }

    // CARDS
    private HBox criarCards() {

        Label totalProdutos = new Label("Produtos: " + produtoService.listarProdutos().size());
        Label baixoEstoque = new Label("Baixo estoque: " +
                produtoService.listarProdutos().stream()
                        .filter(p -> movimentacaoService.precisaRepor(p))
                        .count()
        );

        VBox card1 = criarCard(totalProdutos);
        VBox card2 = criarCard(baixoEstoque);

        return new HBox(20, card1, card2);
    }

    private VBox criarCard(Label texto) {

        texto.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        VBox card = new VBox(texto);
        card.setPadding(new Insets(15));
        card.setStyle("""
            -fx-background-color: white;
            -fx-background-radius: 10;
            -fx-border-radius: 10;
            -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 10,0,0,4);
        """);

        return card;
    }

    //
    // TABELA
    private void criarTabela() {

        TableColumn<Produto, String> nomeCol = new TableColumn<>("Produto");
        nomeCol.setCellValueFactory(c ->
                new SimpleStringProperty(c.getValue().getNome())
        );

        TableColumn<Produto, Double> estoqueCol = new TableColumn<>("Estoque");
        estoqueCol.setCellValueFactory(c ->
                new SimpleDoubleProperty(
                        movimentacaoService.calcularEstoque(c.getValue())
                ).asObject()
        );

        TableColumn<Produto, String> statusCol = new TableColumn<>("Status");
        statusCol.setCellValueFactory(c ->
                new SimpleStringProperty(
                        movimentacaoService.calcularStatus(c.getValue())
                )
        );

        tabela.getColumns().addAll(nomeCol, estoqueCol, statusCol);

        tabela.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tabela.setStyle("-fx-background-color: white;");
    }

    private void carregarDados() {
        tabela.setItems(FXCollections.observableArrayList(produtoService.listarProdutos()));
    }
}