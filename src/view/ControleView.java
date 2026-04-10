package view;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.VBox;

public class ControleView {

    public VBox getView() {

        MovimentacaoView movimentacaoView = new MovimentacaoView();
        HistoricoView historicoView = new HistoricoView();

        VBox layout = new VBox(15,
                new Label("Controle de Estoque"),
                movimentacaoView.getView(),
                new Separator(),
                historicoView.getView()
        );

        layout.setPadding(new Insets(20));

        return layout;
    }
}