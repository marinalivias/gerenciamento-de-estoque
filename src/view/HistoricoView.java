package view;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Movimentacao;
import service.MovimentacaoService;

public class HistoricoView {

    private MovimentacaoService movimentacaoService = new MovimentacaoService();

    public void mostrar() {

        Stage stage = new Stage();

        TableView<Movimentacao> tabela = new TableView<>();

        TableColumn<Movimentacao, String> produtoCol = new TableColumn<>("Produto");
        produtoCol.setCellValueFactory(c ->
                new SimpleStringProperty(c.getValue().getProduto().getNome())
        );

        TableColumn<Movimentacao, String> tipoCol = new TableColumn<>("Tipo");
        tipoCol.setCellValueFactory(c ->
                new SimpleStringProperty(c.getValue().getTipo())
        );

        TableColumn<Movimentacao, String> qtdCol = new TableColumn<>("Quantidade");
        qtdCol.setCellValueFactory(c ->
                new SimpleStringProperty(String.valueOf(c.getValue().getQuantidade()))
        );

        TableColumn<Movimentacao, String> dataCol = new TableColumn<>("Data");
        dataCol.setCellValueFactory(c ->
                new SimpleStringProperty(c.getValue().getData())
        );

        tabela.getColumns().addAll(produtoCol, tipoCol, qtdCol, dataCol);

        tabela.setItems(FXCollections.observableArrayList(
                movimentacaoService.listarMovimentacoes()
        ));

        VBox layout = new VBox(tabela);

        stage.setScene(new Scene(layout, 600, 400));
        stage.setTitle("Histórico");
        stage.show();
    }
}