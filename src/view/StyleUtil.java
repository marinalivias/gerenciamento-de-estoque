package view;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class StyleUtil {

    public static void estilizarBotao(Button btn) {
        btn.setStyle("""
            -fx-background-color: #4078ff;
            -fx-text-fill: white;
            -fx-background-radius: 8;
            -fx-font-weight: bold;
        """);
    }

    public static void estilizarCard(VBox card) {
        card.setPadding(new Insets(15));
        card.setStyle("""
            -fx-background-color: #ffffff;
            -fx-background-radius: 12;
            -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.05), 10, 0, 0, 4);
        """);
    }

    public static String background() {
        return "-fx-background-color: #f5f6fa;";
    }
}