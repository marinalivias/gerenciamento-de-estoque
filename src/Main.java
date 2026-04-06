import javafx.application.Application;
import javafx.stage.Stage;
import view.EstoqueView;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
        new EstoqueView().mostrar(stage);
    }

    public static void main(String[] args) {
        launch();
    }
}