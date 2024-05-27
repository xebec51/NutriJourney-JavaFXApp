package nutrijourney.controllers;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import nutrijourney.models.Food;
import nutrijourney.models.User;

public class NutritionDetailController {
    private Stage stage;
    private User user;
    private VBox nutritionLayout;

    public NutritionDetailController(Stage stage, User user) {
        this.stage = stage;
        this.user = user;

        initializeNutritionLayout();

        Scene scene = new Scene(nutritionLayout, 400, 300);
        stage.setTitle("Nutrition Details");
        stage.setScene(scene);
    }

    private void initializeNutritionLayout() {
        nutritionLayout = new VBox(10);
        nutritionLayout.setPadding(new Insets(10));

        Label totalCaloriesLabel = new Label("Total Calories: " + user.getTotalCalories());
        Label totalProteinLabel = new Label("Total Protein: " + user.getTotalProtein() + " g");
        Label totalFatLabel = new Label("Total Fat: " + user.getTotalFat() + " g");
        Label totalCarbsLabel = new Label("Total Carbs: " + user.getTotalCarbs() + " g");

        VBox foodLogLayout = new VBox(5);
        foodLogLayout.getChildren().add(new Label("Food Log:"));
        for (Food food : user.getFoodLog()) {
            Label foodLabel = new Label(food.getName() + ": " + food.getCalories() + " kcal - " + food.getDate() + " (" + food.getDay() + ")");
            foodLogLayout.getChildren().add(foodLabel);
        }

        Button backButton = new Button("Back to Main Menu");
        backButton.setOnAction(e -> showMainMenu());

        nutritionLayout.getChildren().addAll(totalCaloriesLabel, totalProteinLabel, totalFatLabel, totalCarbsLabel, foodLogLayout, backButton);
    }

    private void showMainMenu() {
        MainController mainController = new MainController(stage, user);
        stage.getScene().setRoot(mainController.getView());
    }

    public void show() {
        stage.show();
    }
}
