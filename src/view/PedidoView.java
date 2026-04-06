package view;

import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
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
    private PedidoService pedidoService = new PedidoService(); // pode manter por enquanto
    private MovimentacaoService movimentacaoService = MovimentacaoService.getInstance();

    public void mostrar() {

        Stage stage = new Stage();

        ComboBox<Produto> produto = new ComboBox<>();
        produto.setItems(FXCollections.observableArrayList(produtoService.listarProdutos()));

        TextField quantidade = new TextField();
        quantidade.setPromptText("Quantidade");

        Button adicionar = new Button("Adicionar");

        String data = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));

        Pedido pedido = pedidoService.criarPedido(data);

        adicionar.setOnAction(e -> {
            try {
                if (produto.getValue() == null) {
                    System.out.println("Selecione um produto");
                    return;
                }

                double qtd = Double.parseDouble(quantidade.getText());

                pedidoService.adicionarItem(
                        pedido,
                        produto.getValue(),
                        qtd
                );

                quantidade.clear();

            } catch (Exception ex) {
                System.out.println("Erro ao adicionar item");
            }
        });

        Button gerar = new Button("Gerar PDF");

        gerar.setOnAction(e -> PdfService.gerarPdfPedido(pedido));

        VBox layout = new VBox(10, produto, quantidade, adicionar, gerar);

        stage.setScene(new Scene(layout, 300, 300));
        stage.setTitle("Pedido Manual");
        stage.show();
    }
}