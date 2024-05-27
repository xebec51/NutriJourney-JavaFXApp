package nutrijourney.controllers;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import nutrijourney.models.User;
import nutrijourney.models.Food;
import nutrijourney.models.Fruit;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;

public class MainController {

    private VBox profileSection, logSection, recipeSection, nutritionSection;
    private Label bmiLabel, dailyCaloriesLabel;
    private ListView<Food> recipeListView;
    private VBox view;
    private Stage primaryStage;
    private User user;

    public MainController(Stage primaryStage, User user) {
        this.primaryStage = primaryStage;
        this.user = user;
        initializeProfileSection(user);
        initializeLogSection();
        initializeRecipeSection();
        initializeNutritionSection();

        view = new VBox(10, profileSection, recipeSection, logSection, nutritionSection);
        view.setPadding(new Insets(10));

        Scene scene = new Scene(view, 800, 600);
        primaryStage.setScene(scene);
    }

    private void initializeProfileSection(User user) {
        profileSection = new VBox(10);
        profileSection.setPadding(new Insets(10));
        profileSection.setStyle("-fx-border-color: black; -fx-border-width: 1;");
        profileSection.getChildren().add(new Label("User Profile"));

        Label nameLabel = new Label("Full Name: " + user.getFullName());
        Label ageLabel = new Label("Age: " + user.getAge());
        Label weightLabel = new Label("Weight: " + user.getWeight() + " kg");
        Label heightLabel = new Label("Height: " + user.getHeight() + " cm");

        bmiLabel = new Label("BMI: " + String.format("%.2f", user.calculateBMI()));
        dailyCaloriesLabel = new Label("Daily Caloric Needs: " + String.format("%.0f", user.calculateDailyCalories()) + " kcal");

        profileSection.getChildren().addAll(nameLabel, ageLabel, weightLabel, heightLabel, bmiLabel, dailyCaloriesLabel);
    }

    private void initializeLogSection() {
        logSection = new VBox(10);
        logSection.setPadding(new Insets(10));
        logSection.setStyle("-fx-border-color: black; -fx-border-width: 1;");
        logSection.getChildren().add(new Label("Nutritional Log"));

        TextField caloriesField = new TextField();
        caloriesField.setPromptText("Calories Consumed");

        Button logButton = new Button("Log Calories");
        logButton.setOnAction(e -> {
            try {
                double calories = Double.parseDouble(caloriesField.getText());
                Food food = new Food("Logged Food", calories, 0, 0, 0) {
                    @Override
                    public void displayInfo() {
                        // No implementation needed for this example
                    }
                };

                LocalDate today = LocalDate.now();
                String date = today.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                String day = today.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.getDefault());

                user.logFood(food, date, day);
                showAlert("Calories Logged", "You have logged " + calories + " calories.");
            } catch (NumberFormatException ex) {
                showAlert("Invalid input", "Please enter a valid number for calories.");
            }
        });

        logSection.getChildren().addAll(new Label("Calories Consumed:"), caloriesField, logButton);
    }

    private void initializeRecipeSection() {
        recipeSection = new VBox(10);
        recipeSection.setPadding(new Insets(10));
        recipeSection.setStyle("-fx-border-color: black; -fx-border-width: 1;");
        recipeSection.getChildren().add(new Label("Recipes"));

        recipeListView = new ListView<>();
        recipeListView.getItems().addAll(
                new Fruit("Apple", 52, 0.3, 0.2, 14),
                new Fruit("Banana", 89, 1.1, 0.3, 23),
                new Fruit("Orange", 43, 1.0, 0.2, 10)
        );

        recipeListView.setCellFactory(param -> new ListCell<Food>() {
            @Override
            protected void updateItem(Food item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getName());
                }
            }
        });

        recipeListView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                Food selectedFood = recipeListView.getSelectionModel().getSelectedItem();
                if (selectedFood != null) {
                    Stage detailStage = new Stage();
                    DetailController detailController = new DetailController(detailStage, selectedFood);
                    detailController.show();

                    LocalDate today = LocalDate.now();
                    String date = today.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                    String day = today.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.getDefault());

                    user.logFood(selectedFood, date, day);
                }
            }
        });
        recipeSection.getChildren().add(recipeListView);
    }

    private void initializeNutritionSection() {
        nutritionSection = new VBox(10);
        nutritionSection.setPadding(new Insets(10));
        nutritionSection.setStyle("-fx-border-color: black; -fx-border-width: 1;");
        nutritionSection.getChildren().add(new Label("Nutrition Details"));

        Button viewNutritionButton = new Button("View Nutrition Details");
        viewNutritionButton.setOnAction(e -> {
            NutritionDetailController nutritionDetailController = new NutritionDetailController(primaryStage, user);
            nutritionDetailController.show();
        });

        Button logoutButton = new Button("Logout");
        logoutButton.setOnAction(e -> logout());

        nutritionSection.getChildren().addAll(viewNutritionButton, logoutButton);
    }

    private void logout() {
        LoginController loginController = new LoginController(primaryStage);
        primaryStage.getScene().setRoot(loginController.getView());
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public VBox getView() {
        return view;
    }
}
