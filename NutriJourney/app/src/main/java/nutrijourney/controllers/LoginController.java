package nutrijourney.controllers;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
// import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import nutrijourney.models.User;
import nutrijourney.database.Database;

public class LoginController {

    private StackPane stackPane;
    private VBox view;
    private TextField usernameField;
    private PasswordField passwordField;

    public LoginController(Stage primaryStage) {
        initializeLoginView(primaryStage);
    }

    private void initializeLoginView(Stage primaryStage) {
        stackPane = new StackPane();

        ImageView background = new ImageView(new Image(getClass().getResource("/images/background.png").toExternalForm()));
        background.setFitWidth(800);
        background.setFitHeight(600);
        stackPane.getChildren().add(background);
        
        view = new VBox(10);
        view.setPadding(new Insets(20));
        view.setStyle("-fx-background-color: transparent; -fx-border-radius: 10; -fx-background-radius: 10;");

        ImageView frameImage = new ImageView(new Image(getClass().getResource("/images/Frame 23.png").toExternalForm()));
        frameImage.setFitWidth(400);
        frameImage.setFitHeight(300);

        Label titleLabel = new Label("Sign In");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: white;");

        // Username section
        Label usernameLabel = new Label("Username:");
        usernameLabel.setStyle("-fx-text-fill: white;");
        usernameField = new TextField();
        usernameField.setStyle("-fx-background-color: white; -fx-text-fill: black;");
        VBox usernameBox = new VBox(usernameLabel, usernameField);
        usernameBox.setAlignment(Pos.CENTER_LEFT);
        usernameBox.setSpacing(5);

        // Password section
        Label passwordLabel = new Label("Password:");
        passwordLabel.setStyle("-fx-text-fill: white;");
        passwordField = new PasswordField();
        passwordField.setStyle("-fx-background-color: white; -fx-text-fill: black;");
        VBox passwordBox = new VBox(passwordLabel, passwordField);
        passwordBox.setAlignment(Pos.CENTER_LEFT);
        passwordBox.setSpacing(5);

        Button loginButton = new Button("Log In");
        loginButton.setStyle("-fx-background-color: white; -fx-text-fill: black; -fx-border-color: transparent; -fx-font-weight: bold;");
        loginButton.setOnAction(e -> handleLogin(primaryStage));

        Hyperlink createAccountLink = new Hyperlink("Sign Up.");
        createAccountLink.setStyle("-fx-text-fill: lightgreen;");
        createAccountLink.setOnAction(e -> showRegisterPage(primaryStage));

        Label createAccountLabel = new Label("Don't have an account yet? ");
        createAccountLabel.setStyle("-fx-text-fill: white;");
        HBox createAccountBox = new HBox(createAccountLabel, createAccountLink);
        createAccountBox.setAlignment(Pos.CENTER);

        view.getChildren().addAll(titleLabel, usernameBox, passwordBox, loginButton, createAccountBox);
        view.setAlignment(Pos.CENTER);
        view.setMaxWidth(300); // Set width of login form

        stackPane.getChildren().addAll(frameImage, view);
        StackPane.setAlignment(view, Pos.CENTER);
    }

    private void handleLogin(Stage primaryStage) {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
            showAlert("Login Failed", "Username or password cannot be empty.");
            return;
        }

        User user = Database.getUser(username);
        if (user != null && user.getPassword().equals(password)) {
            MainController mainController = new MainController(primaryStage, user);
            primaryStage.getScene().setRoot(mainController.getView());
        } else {
            showAlert("Login Failed", "Invalid username or password.");
        }
    }

    private void showRegisterPage(Stage primaryStage) {
        RegisterController registerController = new RegisterController(primaryStage);
        registerController.show();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public StackPane getView() {
        return stackPane;
    }
}
