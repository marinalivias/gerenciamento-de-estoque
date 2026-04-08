package view;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import model.Pedido;
import model.Produto;
import service.MovimentacaoService;
import service.PedidoService;
import service.ProdutoService;
import util.PdfService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PedidoView {

    private ProdutoService produtoService = ProdutoService.getInstance();
    private PedidoService pedidoService = new PedidoService();
    private MovimentacaoService movimentacaoService = MovimentacaoService.getInstance();

    public VBox getView() {

        ComboBox<Produto> produto = new ComboBox<>();
        produto.setItems(FXCollections.observableArrayList(produtoService.listarProdutos()));
        produto.setPromptText("Selecione um produto");

        TextField quantidade = new TextField();
        quantidade.setPromptText("Quantidade");

        Button adicionar = new Button("Adicionar Item");

        String data = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));

        Pedido pedido = pedidoService.criarPedido(data);

        Label status = new Label();

        adicionar.setOnAction(e -> {
            try {
                if (produto.getValue() == null) {
                    status.setText("Selecione um produto");
                    return;
                }

                double qtd = Double.parseDouble(quantidade.getText());

                pedidoService.adicionarItem(pedido, produto.getValue(), qtd);

                quantidade.clear();
                status.setText("Item adicionado!");

            } catch (Exception ex) {
                status.setText("Erro ao adicionar item");
            }
        });

        Button gerar = new Button("Gerar PDF");

        gerar.setOnAction(e -> {
            PdfService.gerarPdfPedido(pedido);
            status.setText("PDF gerado!");
        });

        VBox layout = new VBox(10,
                produto,
                quantidade,
                adicionar,
                gerar,
                status
        );

        layout.setPadding(new Insets(20));

        return layout;
    }
}