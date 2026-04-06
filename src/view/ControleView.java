package view;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class ControleView {

    public VBox getView() {
        return new VBox(new Label("Controle de Estoque + Histórico"));
    }
}