package nutrijourney.controllers;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import nutrijourney.database.Database;
// import nutrijourney.models.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RegisterController {
    private Stage stage;
    private VBox registerLayout;
    private TextField usernameField, fullNameField, ageField, weightField, heightField;
    private PasswordField passwordField;
    private ComboBox<String> genderComboBox;
    private ComboBox<String> activityLevelComboBox;
    private Label messageLabel;

    public RegisterController(Stage stage) {
        this.stage = stage;
        initializeRegisterLayout();

        Scene scene = new Scene(registerLayout, 800, 600); // Mengatur ukuran scene sama dengan lainnya
        stage.setTitle("Register");
        stage.setScene(scene);
    }

    private void initializeRegisterLayout() {
        registerLayout = new VBox(10);
        registerLayout.setPadding(new Insets(10));

        usernameField = new TextField();
        usernameField.setPromptText("Username");

        passwordField = new PasswordField();
        passwordField.setPromptText("Password");

        fullNameField = new TextField();
        fullNameField.setPromptText("Full Name");

        ageField = new TextField();
        ageField.setPromptText("Age");

        weightField = new TextField();
        weightField.setPromptText("Weight (kg)");

        heightField = new TextField();
        heightField.setPromptText("Height (cm)");

        genderComboBox = new ComboBox<>();
        genderComboBox.getItems().addAll("Male", "Female");
        genderComboBox.setPromptText("Gender");

        activityLevelComboBox = new ComboBox<>();
        activityLevelComboBox.getItems().addAll(
                "Sedentary (little or no exercise)", 
                "Lightly active (light exercise/sports 1-3 days/week)", 
                "Moderately active (moderate exercise/sports 3-5 days/week)", 
                "Very active (hard exercise/sports 6-7 days a week)", 
                "Super active (very hard exercise/sports & physical job)"
        );
        activityLevelComboBox.setPromptText("Activity Level");

        Button registerButton = new Button("Register");
        registerButton.setOnAction(e -> handleRegister());

        Button backButton = new Button("Back to Login");
        backButton.setOnAction(e -> showLoginPage());

        messageLabel = new Label();

        registerLayout.getChildren().addAll(new Label("Username:"), usernameField,
                new Label("Password:"), passwordField,
                new Label("Full Name:"), fullNameField,
                new Label("Age:"), ageField,
                new Label("Weight (kg):"), weightField,
                new Label("Height (cm):"), heightField,
                new Label("Gender:"), genderComboBox,
                new Label("Activity Level:"), activityLevelComboBox,
                registerButton, backButton, messageLabel);
    }

    private void handleRegister() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        String fullName = fullNameField.getText();
        int age = Integer.parseInt(ageField.getText());
        double weight = Double.parseDouble(weightField.getText());
        double height = Double.parseDouble(heightField.getText());
        String gender = genderComboBox.getValue();
        double activityLevel = getActivityLevel(activityLevelComboBox.getValue());

        try (Connection conn = Database.getConnection()) {
            String query = "INSERT INTO users (username, password, full_name, age, weight, height, gender, activity_level) VALUES (?,?,?,?,?,?,?,?)";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.setString(3, fullName);
            pstmt.setInt(4, age);
            pstmt.setDouble(5, weight);
            pstmt.setDouble(6, height);
            pstmt.setString(7, gender);
            pstmt.setDouble(8, activityLevel);

            pstmt.executeUpdate();
            messageLabel.setText("Account created successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
            messageLabel.setText("Failed to create account.");
        }
    }

    private double getActivityLevel(String activity) {
        switch (activity) {
            case "Sedentary (little or no exercise)":
                return 1.2;
            case "Lightly active (light exercise/sports 1-3 days/week)":
                return 1.375;
            case "Moderately active (moderate exercise/sports 3-5 days/week)":
                return 1.55;
            case "Very active (hard exercise/sports 6-7 days a week)":
                return 1.725;
            case "Super active (very hard exercise/sports & physical job)":
                return 1.9;
            default:
                return 1.2;
        }
    }

    private void showLoginPage() {
        LoginController loginController = new LoginController(stage);
        stage.getScene().setRoot(loginController.getView());
    }

    public void show() {
        stage.show();
    }
}
