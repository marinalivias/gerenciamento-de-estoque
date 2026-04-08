package view;

import javafx.geometry.Insets;
import javafx.scene.layout.VBox;

public class PedidoView2 {

    public VBox getView() {

        PedidoView pedidoView = new PedidoView();

        VBox layout = new VBox(pedidoView.getView());
        layout.setPadding(new Insets(20));

        return layout;
    }
}