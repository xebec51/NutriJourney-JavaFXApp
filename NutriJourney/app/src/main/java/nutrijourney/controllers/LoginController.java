package nutrijourney.controllers;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import nutrijourney.models.User;
import nutrijourney.database.Database;

public class LoginController {

    private VBox view;
    private TextField usernameField;
    private PasswordField passwordField;

    public LoginController(Stage primaryStage) {
        initializeLoginView(primaryStage);
    }

    private void initializeLoginView(Stage primaryStage) {
        view = new VBox(10);
        view.setPadding(new Insets(10));

        Label usernameLabel = new Label("Username:");
        usernameField = new TextField();

        Label passwordLabel = new Label("Password:");
        passwordField = new PasswordField();

        Button loginButton = new Button("Login");
        loginButton.setOnAction(e -> handleLogin(primaryStage));

        Button createAccountButton = new Button("Create Account");
        createAccountButton.setOnAction(e -> showRegisterPage(primaryStage));

        view.getChildren().addAll(usernameLabel, usernameField, passwordLabel, passwordField, loginButton, createAccountButton);
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

    public VBox getView() {
        return view;
    }
}
