package nutrijourney;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import nutrijourney.controllers.LoginController;
import nutrijourney.database.Database;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) {

        primaryStage.setResizable(false);

        Database.initialize();

        BorderPane root = new BorderPane();

        LoginController loginController = new LoginController(primaryStage);
        root.setCenter(loginController.getView());

        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("NutriJourney");
        primaryStage.show();

        primaryStage.setOnCloseRequest(event -> {
            Database.closeConnection();
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
