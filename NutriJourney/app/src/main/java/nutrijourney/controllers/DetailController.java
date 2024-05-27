package nutrijourney.controllers;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import nutrijourney.models.Food;

public class DetailController {

    private Stage stage;
    private Food food;
    private VBox detailLayout;
    private Label nameLabel, caloriesLabel, proteinLabel, fatLabel, carbsLabel;

    public DetailController(Stage stage, Food food) {
        this.stage = stage;
        this.food = food;

        initializeDetailLayout();
        displayFoodDetails();

        Scene scene = new Scene(detailLayout, 400, 300);
        stage.setTitle("Food Details");
        stage.setScene(scene);
    }

    private void initializeDetailLayout() {
        detailLayout = new VBox(10);
        detailLayout.setPadding(new Insets(10));

        nameLabel = new Label();
        caloriesLabel = new Label();
        proteinLabel = new Label();
        fatLabel = new Label();
        carbsLabel = new Label();

        Button logButton = new Button("Log this food");
        logButton.setOnAction(e -> logFood());

        detailLayout.getChildren().addAll(nameLabel, caloriesLabel, proteinLabel, fatLabel, carbsLabel, logButton);
    }

    private void displayFoodDetails() {
        nameLabel.setText("Name: " + food.getName());
        caloriesLabel.setText("Calories: " + food.getCalories());
        proteinLabel.setText("Protein: " + food.getProtein());
        fatLabel.setText("Fat: " + food.getFat());
        carbsLabel.setText("Carbs: " + food.getCarbs());
    }

    private void logFood() {
        // Logic to log the food to the user's nutritional log
        showAlert("Food Logged", food.getName() + " has been logged.");
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void show() {
        stage.show();
    }
}