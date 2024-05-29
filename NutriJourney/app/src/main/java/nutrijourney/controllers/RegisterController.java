package nutrijourney.controllers;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import nutrijourney.database.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RegisterController {
    private Stage stage;
    private VBox registerLayout;
    private GridPane formGrid;
    private TextField usernameField, fullNameField, ageField, weightField, heightField;
    private PasswordField passwordField;
    private ComboBox<String> genderComboBox;
    private ComboBox<String> activityLevelComboBox;
    private Label messageLabel;
    private StackPane stackPane;

    public RegisterController(Stage stage) {
        this.stage = stage;
        initializeRegisterLayout();

        Scene scene = new Scene(stackPane, 800, 600); // Menyamakaan ukuran scene
        stage.setTitle("Register");
        stage.setScene(scene);
    }

    private void initializeRegisterLayout() {
        stackPane = new StackPane();
        
        // Tambahkan background image
        ImageView background = new ImageView(new Image(getClass().getResource("/images/background.png").toExternalForm()));
        background.setFitWidth(800);
        background.setFitHeight(600);
        stackPane.getChildren().add(background);

        registerLayout = new VBox(10); // Menambah jarak antar elemen
        registerLayout.setPadding(new Insets(10));
        registerLayout.setStyle("-fx-background-color: transparent; -fx-border-radius: 10; -fx-background-radius: 10;");
        
        ImageView frameImage = new ImageView(new Image(getClass().getResource("/images/Rectangle 67.png").toExternalForm()));
        frameImage.setFitWidth(465);
        frameImage.setFitHeight(365);
        
        Label titleLabel = new Label("Sign Up");
        titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: white;"); // Adjusted font size

        formGrid = new GridPane();
        formGrid.setHgap(10); // Menambah jarak horizontal antar kolom
        formGrid.setVgap(5); // Menambah jarak vertikal antar baris
        formGrid.setAlignment(Pos.CENTER);
        formGrid.setPadding(new Insets(10, 20, 10, 20));

        // Ukuran textfield dan combobox dikurangi
        double fieldWidth = 150;

        Label usernameLabel = new Label("Username:");
        usernameLabel.setStyle("-fx-text-fill: white;");
        usernameField = new TextField();
        usernameField.setStyle("-fx-background-color: white; -fx-text-fill: black;");
        usernameField.setPrefWidth(fieldWidth);

        Label passwordLabel = new Label("Password:");
        passwordLabel.setStyle("-fx-text-fill: white;");
        passwordField = new PasswordField();
        passwordField.setStyle("-fx-background-color: white; -fx-text-fill: black;");
        passwordField.setPrefWidth(fieldWidth);

        Label fullNameLabel = new Label("Full Name:");
        fullNameLabel.setStyle("-fx-text-fill: white;");
        fullNameField = new TextField();
        fullNameField.setStyle("-fx-background-color: white; -fx-text-fill: black;");
        fullNameField.setPrefWidth(fieldWidth);

        Label ageLabel = new Label("Age:");
        ageLabel.setStyle("-fx-text-fill: white;");
        ageField = new TextField();
        ageField.setStyle("-fx-background-color: white; -fx-text-fill: black;");
        ageField.setPrefWidth(fieldWidth);

        Label weightLabel = new Label("Weight (kg):");
        weightLabel.setStyle("-fx-text-fill: white;");
        weightField = new TextField();
        weightField.setStyle("-fx-background-color: white; -fx-text-fill: black;");
        weightField.setPrefWidth(fieldWidth);

        Label heightLabel = new Label("Height (cm):");
        heightLabel.setStyle("-fx-text-fill: white;");
        heightField = new TextField();
        heightField.setStyle("-fx-background-color: white; -fx-text-fill: black;");
        heightField.setPrefWidth(fieldWidth);

        Label genderLabel = new Label("Gender:");
        genderLabel.setStyle("-fx-text-fill: white;");
        genderComboBox = new ComboBox<>();
        genderComboBox.getItems().addAll("Male", "Female");
        genderComboBox.setStyle("-fx-background-color: white; -fx-text-fill: black;");
        genderComboBox.setPrefWidth(fieldWidth);

        Label activityLabel = new Label("Activity Level:");
        activityLabel.setStyle("-fx-text-fill: white;");
        activityLevelComboBox = new ComboBox<>();
        activityLevelComboBox.getItems().addAll(
                "Sedentary (little or no exercise)", 
                "Lightly active (light exercise/sports 1-3 days/week)", 
                "Moderately active (moderate exercise/sports 3-5 days/week)", 
                "Very active (hard exercise/sports 6-7 days a week)", 
                "Super active (very hard exercise/sports & physical job)"
        );
        activityLevelComboBox.setStyle("-fx-background-color: white; -fx-text-fill: black;");
        activityLevelComboBox.setPrefWidth(fieldWidth);

        formGrid.add(usernameLabel, 0, 0);
        formGrid.add(usernameField, 0, 1);

        formGrid.add(passwordLabel, 0, 2);
        formGrid.add(passwordField, 0, 3);

        formGrid.add(fullNameLabel, 0, 4);
        formGrid.add(fullNameField, 0, 5);

        formGrid.add(ageLabel, 0, 6);
        formGrid.add(ageField, 0, 7);

        formGrid.add(weightLabel, 1, 0);
        formGrid.add(weightField, 1, 1);

        formGrid.add(heightLabel, 1, 2);
        formGrid.add(heightField, 1, 3);

        formGrid.add(genderLabel, 1, 4);
        formGrid.add(genderComboBox, 1, 5);

        formGrid.add(activityLabel, 1, 6);
        formGrid.add(activityLevelComboBox, 1, 7);

        Button registerButton = new Button("Register");
        registerButton.setStyle("-fx-background-color: white; -fx-text-fill: black; -fx-border-color: transparent; -fx-font-weight: bold;");
        registerButton.setOnAction(e -> handleRegister());

        Hyperlink backLink = new Hyperlink("Sign in.");
        backLink.setStyle("-fx-text-fill: lightgreen;");
        backLink.setOnAction(e -> showLoginPage());

        Label backLabel = new Label("Already existing?");
        backLabel.setStyle("-fx-text-fill: white;");
        HBox backBox = new HBox(backLabel, backLink);
        backBox.setAlignment(Pos.CENTER);

        messageLabel = new Label();
        messageLabel.setStyle("-fx-text-fill: white;");

        // Menambahkan titleLabel ke dalam VBox sebelum formGrid
        VBox titleBox = new VBox(titleLabel);
        titleBox.setAlignment(Pos.CENTER);
        titleBox.setPadding(new Insets(20, 0, 0, 0)); // Menambah padding antara title dan form

        registerLayout.getChildren().addAll(titleBox, formGrid, registerButton, backBox, messageLabel);
        registerLayout.setAlignment(Pos.CENTER);
        registerLayout.setMaxWidth(465); // Set width of register form to fit inside the frame image

        stackPane.getChildren().addAll(frameImage, registerLayout);
        StackPane.setAlignment(registerLayout, Pos.CENTER);
    }

    private void handleRegister() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        String fullName = fullNameField.getText();
        int age;
        double weight;
        double height;

        // Add checks to ensure fields are not empty and convert values appropriately
        try {
            age = Integer.parseInt(ageField.getText());
            weight = Double.parseDouble(weightField.getText());
            height = Double.parseDouble(heightField.getText());
        } catch (NumberFormatException e) {
            messageLabel.setText("Please enter valid numerical values for age, weight, and height.");
            return;
        }

        String gender = genderComboBox.getValue();
        String activityLevelString = activityLevelComboBox.getValue();
        double activityLevel = getActivityLevel(activityLevelString);

        try (Connection conn = Database.getConnection()) {
            // Check if username already exists
            String checkUserQuery = "SELECT COUNT(*) FROM users WHERE username = ?";
            try (PreparedStatement checkStmt = conn.prepareStatement(checkUserQuery)) {
                checkStmt.setString(1, username);
                ResultSet rs = checkStmt.executeQuery();
                if (rs.next() && rs.getInt(1) > 0) {
                    messageLabel.setText("Username already exists. Please choose a different username.");
                    return;
                }
            }

            // Insert new user
            String query = "INSERT INTO users (username, password, full_name, age, weight, height, gender, activity_level) VALUES (?,?,?,?,?,?,?,?)";
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setString(1, username);
                pstmt.setString(2, password);
                pstmt.setString(3, fullName);
                pstmt.setInt(4, age);
                pstmt.setDouble(5, weight);
                pstmt.setDouble(6, height);
                pstmt.setString(7, gender);
                pstmt.setDouble(8, activityLevel);
                pstmt.executeUpdate();
            }

            // Show success alert
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Registration Successful");
            alert.setHeaderText(null);
            alert.setContentText("Account created successfully!");
            alert.showAndWait();

            // Clear fields after successful registration
            usernameField.clear();
            passwordField.clear();
            fullNameField.clear();
            ageField.clear();
            weightField.clear();
            heightField.clear();
            genderComboBox.getSelectionModel().clearSelection();
            activityLevelComboBox.getSelectionModel().clearSelection();
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
