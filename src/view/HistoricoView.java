package view;

import javafx.scene.control.Label;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import model.Movimentacao;
import service.MovimentacaoService;

public class HistoricoView {

    private MovimentacaoService movimentacaoService = MovimentacaoService.getInstance();

    public VBox getView() {

        ListView<String> lista = new ListView<>();

        movimentacaoService.listarMovimentacoes().forEach(m -> {
            lista.getItems().add(
                    m.getData() + " | " +
                            m.getProduto().getNome() + " | " +
                            m.getTipo() + " | " +
                            m.getQuantidade()
            );
        });

        VBox card = new VBox(10,
                new Label("Histórico"),
                lista
        );

        estilizarCard(card);

        VBox layout = new VBox(card);
        layout.setPadding(new Insets(20));
        layout.setStyle("-fx-background-color: #f5f6fa;");

        return layout;
    }

    private void estilizarCard(VBox card) {
        card.setPadding(new Insets(15));
        card.setStyle("""
        -fx-background-color: white;
        -fx-background-radius: 12;
        -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.05), 10, 0, 0, 4);
    """);
    }

}