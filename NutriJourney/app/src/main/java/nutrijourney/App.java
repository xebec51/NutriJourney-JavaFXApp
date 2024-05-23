package nutrijourney;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import nutrijourney.controllers.MainController;

public class App extends Application {
    @Override
    public void start(Stage stage) {
        stage.setTitle("NutriJourney App");
        stage.setScene(new Scene(new MainController().getView(), 800, 600));
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
