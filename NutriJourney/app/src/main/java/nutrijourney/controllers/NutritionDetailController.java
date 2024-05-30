package nutrijourney.controllers;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import nutrijourney.models.Food;
import nutrijourney.models.User;
import nutrijourney.database.Database;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class NutritionDetailController {

    private Stage stage;
    private User user;
    private VBox nutritionLayout;

    public NutritionDetailController(Stage stage, User user) {
        this.stage = stage;
        this.user = user;

        initializeNutritionLayout();

        Scene scene = new Scene(new ScrollPane(nutritionLayout), 800, 600); // Ukuran scene sama dengan Main scene
        stage.setTitle("Nutrition Details");
        stage.setScene(scene);
    }

    private void initializeNutritionLayout() {
        nutritionLayout = new VBox(10);
        nutritionLayout.setPadding(new Insets(10));

        updateNutritionDetails();

        Button backButton = new Button("Back to Main Menu");
        backButton.setOnAction(e -> showMainMenu());

        VBox.setVgrow(nutritionLayout, Priority.ALWAYS);
        nutritionLayout.getChildren().add(backButton);
    }

    public void updateNutritionDetails() {
        nutritionLayout.getChildren().clear();

        Label totalCaloriesLabel = new Label("Total Calories: " + user.getTotalCalories());
        Label totalProteinLabel = new Label("Total Protein: " + user.getTotalProtein() + " g");
        Label totalFatLabel = new Label("Total Fat: " + user.getTotalFat() + " g");
        Label totalCarbsLabel = new Label("Total Carbs: " + user.getTotalCarbs() + " g");
        Label totalWaterLabel = new Label("Total Water: " + user.getWaterConsumedToday() + " ml");

        nutritionLayout.getChildren().addAll(totalCaloriesLabel, totalProteinLabel, totalFatLabel, totalCarbsLabel, totalWaterLabel);

        List<Food> allFoods = Database.getAllLoggedFoods(user.getUsername());

        // Create titled panes for each day
        Map<String, List<Food>> foodsByDay = allFoods.stream()
                .collect(Collectors.groupingBy(Food::getDate));

        foodsByDay.forEach((date, foods) -> {
            TitledPane dayPane = createDayPane(date, foods);
            nutritionLayout.getChildren().add(dayPane);
        });

        Button backButton = new Button("Back to Main Menu");
        backButton.setOnAction(e -> showMainMenu());
        nutritionLayout.getChildren().add(backButton);
    }

    private TitledPane createDayPane(String date, List<Food> foods) {
        ListView<Food> dayListView = new ListView<>();
        dayListView.setCellFactory(param -> new ListCell<Food>() {
            @Override
            protected void updateItem(Food item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getName() + " - " + item.getCalories() + " kcal");
                }
            }
        });
        dayListView.getItems().setAll(foods);

        TitledPane dayPane = new TitledPane(date, dayListView);
        dayPane.setCollapsible(true);
        return dayPane;
    }

    private void showMainMenu() {
        MainController mainController = new MainController(stage, user);
        stage.getScene().setRoot(mainController.getView());
    }

    public void show() {
        stage.show();
    }
}
