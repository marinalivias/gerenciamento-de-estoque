package view;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import model.Pedido;
import model.Produto;
import service.PedidoService;
import service.ProdutoService;
import util.PdfService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PedidoView {

    private ProdutoService produtoService = ProdutoService.getInstance();
    private PedidoService pedidoService = new PedidoService();

    public VBox getView() {

        ComboBox<Produto> produto = new ComboBox<>();
        produto.setItems(FXCollections.observableArrayList(produtoService.listarProdutos()));

        TextField qtd = new TextField();

        Button add = new Button("Adicionar");
        Button pdf = new Button("Gerar PDF");

        StyleUtil.estilizarBotao(add);
        StyleUtil.estilizarBotao(pdf);

        Pedido pedido = pedidoService.criarPedido(
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))
        );

        Label status = new Label();

        add.setOnAction(e -> {
            pedidoService.adicionarItem(pedido, produto.getValue(), Double.parseDouble(qtd.getText()));
            status.setText("Item adicionado");
        });

        pdf.setOnAction(e -> {
            PdfService.gerarPdfPedido(pedido);
            status.setText("PDF gerado");
        });

        VBox card = new VBox(10, produto, qtd, add, pdf, status);
        StyleUtil.estilizarCard(card);

        Label titulo = new Label("Pedidos");
        titulo.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        VBox layout = new VBox(20, titulo, card);
        layout.setPadding(new Insets(20));
        layout.setStyle(StyleUtil.background());

        return layout;
    }
}