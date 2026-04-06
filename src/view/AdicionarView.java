package view;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class AdicionarView {

    public VBox getView() {
        return new VBox(new Label("Adicionar Produto / Categoria"));
    }
}