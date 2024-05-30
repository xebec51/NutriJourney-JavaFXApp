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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import nutrijourney.models.User;
import nutrijourney.models.Food;
import nutrijourney.database.Database;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class MainController {

    private VBox profileSection, weightUpdateSection, waterIntakeSection, nutritionLogSection, recipeSection, nutritionSection;
    private Label bmiLabel, dailyCaloriesLabel, waterIntakeLabel, bmiCategoryLabel, dailyCarbsLabel, dailyProteinLabel, dailyFatLabel;
    private Label caloriesTodayLabel, proteinTodayLabel, fatTodayLabel, carbsTodayLabel, waterTodayLabel;
    private Label nameLabel, ageLabel, weightLabel, heightLabel;
    private ListView<Food> recipeListView;
    private TextField searchField; // Tambahkan TextField untuk pencarian
    private BorderPane mainLayout;
    private Stage primaryStage;
    private User user;
    private int waterIntake; // Jumlah air yang dikonsumsi dalam ml

    public MainController(Stage primaryStage, User user) {
        this.primaryStage = primaryStage;
        this.user = user;
        this.waterIntake = (int) user.getWaterConsumedToday(); // Memuat asupan air dari log makanan pengguna
        initializeProfileSection(user);
        initializeWeightUpdateSection();
        initializeWaterIntakeSection();
        initializeNutritionLogSection();
        initializeRecipeSection();
        initializeNutritionSection();

        mainLayout = new BorderPane();
        mainLayout.setPadding(new Insets(10)); // Menambahkan padding di setiap sisi scene

        VBox centerBox = new VBox(0, profileSection, nutritionLogSection);
        VBox rightBox = new VBox(0, recipeSection, new HBox(0, weightUpdateSection, waterIntakeSection), nutritionSection);

        // Memastikan setiap bagian mengisi ruang yang ada
        VBox.setVgrow(profileSection, Priority.ALWAYS);
        VBox.setVgrow(nutritionLogSection, Priority.ALWAYS);
        VBox.setVgrow(recipeSection, Priority.ALWAYS);
        VBox.setVgrow(weightUpdateSection, Priority.ALWAYS);
        VBox.setVgrow(waterIntakeSection, Priority.ALWAYS);
        VBox.setVgrow(nutritionSection, Priority.ALWAYS);

        // Memastikan HBox sections mengisi ruang yang ada
        HBox.setHgrow(weightUpdateSection, Priority.ALWAYS);
        HBox.setHgrow(waterIntakeSection, Priority.ALWAYS);

        mainLayout.setLeft(centerBox);
        mainLayout.setCenter(rightBox);

        Scene scene = new Scene(mainLayout, 800, 600);
        primaryStage.setScene(scene);
    }

    private void initializeProfileSection(User user) {
        profileSection = new VBox(10);
        profileSection.setPadding(new Insets(10));
        profileSection.setStyle("-fx-border-color: black; -fx-border-width: 1; -fx-border-insets: 0; -fx-border-style: solid;");
        profileSection.getChildren().add(new Label("User Profile"));

        nameLabel = new Label("Full Name: " + user.getFullName());
        ageLabel = new Label("Age: " + user.getAge());
        weightLabel = new Label("Weight: " + user.getWeight() + " kg");
        heightLabel = new Label("Height: " + user.getHeight() + " cm");

        bmiLabel = new Label("BMI: " + String.format("%.2f", user.calculateBMI()));
        bmiCategoryLabel = new Label("BMI Category: " + user.getBMICategory());
        dailyCaloriesLabel = new Label("Daily Caloric Needs: " + String.format("%.0f", user.calculateDailyCalories()) + " kcal");
        dailyCarbsLabel = new Label("Daily Carbs: " + String.format("%.0f", user.getDailyCarbs()) + " g");
        dailyProteinLabel = new Label("Daily Protein: " + String.format("%.0f", user.getDailyProtein()) + " g");
        dailyFatLabel = new Label("Daily Fat: " + String.format("%.0f", user.getDailyFat()) + " g");

        profileSection.getChildren().addAll(nameLabel, ageLabel, weightLabel, heightLabel, bmiLabel, bmiCategoryLabel, dailyCaloriesLabel, dailyCarbsLabel, dailyProteinLabel, dailyFatLabel);
    }

    private void initializeWaterIntakeSection() {
        waterIntakeSection = new VBox(10);
        waterIntakeSection.setPadding(new Insets(10));
        waterIntakeSection.setStyle("-fx-border-color: black; -fx-border-width: 1; -fx-border-insets: 0; -fx-border-style: solid;");
        waterIntakeSection.getChildren().add(new Label("Water Intake"));

        waterIntakeLabel = new Label("Water Intake: " + waterIntake + " ml");
        Button addWaterButton = new Button("Add 250 ml");
        addWaterButton.setOnAction(e -> {
            waterIntake += 250;
            waterIntakeLabel.setText("Water Intake: " + waterIntake + " ml");

            // Log water intake
            Food water = new Food("Water", 0, 0, 0, 0, 250) {
                @Override
                public void displayInfo() {
                    // No implementation needed for this example
                }
            };
            LocalDate today = LocalDate.now();
            String date = today.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            String time = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
            water.setDate(date);
            water.setDay(time);
            Database.logFood(user.getUsername(), water, date, time);

            // Update the user's food log without showing the Nutrition Details view
            user.logFood(water, date, time);
            updateTodayNutrients();
        });

        waterIntakeSection.getChildren().addAll(waterIntakeLabel, addWaterButton);
    }

    private void initializeNutritionLogSection() {
        nutritionLogSection = new VBox(10);
        nutritionLogSection.setPadding(new Insets(10));
        nutritionLogSection.setStyle("-fx-border-color: black; -fx-border-width: 1; -fx-border-insets: 0; -fx-border-style: solid;");
        nutritionLogSection.getChildren().add(new Label("Daily Nutrition Log"));

        // Initialize labels for today's nutrient intake
        caloriesTodayLabel = new Label("Calories consumed today: " + user.getCaloriesConsumedToday() + " kcal");
        proteinTodayLabel = new Label("Protein consumed today: " + user.getProteinConsumedToday() + " g");
        fatTodayLabel = new Label("Fat consumed today: " + user.getFatConsumedToday() + " g");
        carbsTodayLabel = new Label("Carbs consumed today: " + user.getCarbsConsumedToday() + " g");
        waterTodayLabel = new Label("Water consumed today: " + user.getWaterConsumedToday() + " ml");

        nutritionLogSection.getChildren().addAll(caloriesTodayLabel, proteinTodayLabel, fatTodayLabel, carbsTodayLabel, waterTodayLabel);
    }

    private void updateTodayNutrients() {
        caloriesTodayLabel.setText("Calories consumed today: " + user.getCaloriesConsumedToday() + " kcal");
        proteinTodayLabel.setText("Protein consumed today: " + user.getProteinConsumedToday() + " g");
        fatTodayLabel.setText("Fat consumed today: " + user.getFatConsumedToday() + " g");
        carbsTodayLabel.setText("Carbs consumed today: " + user.getCarbsConsumedToday() + " g");
        waterTodayLabel.setText("Water consumed today: " + user.getWaterConsumedToday() + " ml");
    }

    private void initializeWeightUpdateSection() {
        weightUpdateSection = new VBox(10);
        weightUpdateSection.setPadding(new Insets(10));
        weightUpdateSection.setStyle("-fx-border-color: black; -fx-border-width: 1; -fx-border-insets: 0; -fx-border-style: solid;");
        weightUpdateSection.getChildren().add(new Label("Update Weight"));

        TextField weightField = new TextField();
        weightField.setPromptText("Enter new weight (kg)");

        Button updateWeightButton = new Button("Update Weight");
        updateWeightButton.setOnAction(e -> {
            try {
                double newWeight = Double.parseDouble(weightField.getText());
                user.setWeight(newWeight);

                // Update weight in the database
                Database.updateUserWeight(user.getUsername(), newWeight);

                // Update the profile section with the new weight and BMI
                weightLabel.setText("Weight: " + user.getWeight() + " kg");
                bmiLabel.setText("BMI: " + String.format("%.2f", user.calculateBMI()));
                bmiCategoryLabel.setText("BMI Category: " + user.getBMICategory());

                // Update daily nutrition needs
                dailyCaloriesLabel.setText("Daily Caloric Needs: " + String.format("%.0f", user.calculateDailyCalories()) + " kcal");
                dailyCarbsLabel.setText("Daily Carbs: " + String.format("%.0f", user.getDailyCarbs()) + " g");
                dailyProteinLabel.setText("Daily Protein: " + String.format("%.0f", user.getDailyProtein()) + " g");
                dailyFatLabel.setText("Daily Fat: " + String.format("%.0f", user.getDailyFat()) + " g");

                showAlert("Weight Updated", "Your weight has been updated to " + newWeight + " kg.");
                updateTodayNutrients();
            } catch (NumberFormatException ex) {
                showAlert("Invalid input", "Please enter a valid number for weight.");
            }
        });

        weightUpdateSection.getChildren().addAll(new Label("New Weight:"), weightField, updateWeightButton);
    }

    private void initializeRecipeSection() {
        recipeSection = new VBox(10);
        recipeSection.setPadding(new Insets(10));
        recipeSection.setStyle("-fx-border-color: black; -fx-border-width: 1; -fx-border-insets: 0; -fx-border-style: solid;");
        recipeSection.getChildren().add(new Label("Recipes"));

        searchField = new TextField();
        searchField.setPromptText("Search recipes...");
        searchField.textProperty().addListener((observable, oldValue, newValue) -> filterRecipes(newValue));

        recipeListView = new ListView<>();
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

        recipeListView.setOnMouseClicked(this::handleRecipeClick);

        loadRecipes();

        recipeSection.getChildren().addAll(searchField, recipeListView);
    }

    private void loadRecipes() {
        List<Food> recipes = Database.getAllFoods();
        recipeListView.getItems().setAll(recipes);
    }

    private void filterRecipes(String keyword) {
        if (keyword == null || keyword.isEmpty()) {
            loadRecipes();
        } else {
            List<Food> filteredRecipes = Database.getAllFoods().stream()
                .filter(food -> food.getName().toLowerCase().contains(keyword.toLowerCase()))
                .collect(Collectors.toList());
            recipeListView.getItems().setAll(filteredRecipes);
        }
    }

    private void handleRecipeClick(MouseEvent event) {
        if (event.getClickCount() == 2) {
            Food selectedFood = recipeListView.getSelectionModel().getSelectedItem();
            if (selectedFood != null) {
                logSelectedFood(selectedFood);
            }
        }
    }

    private void logSelectedFood(Food selectedFood) {
        Stage logStage = new Stage();
        VBox logLayout = new VBox(10);
        logLayout.setPadding(new Insets(10));

        Label foodNameLabel = new Label("Food: " + selectedFood.getName());
        Label caloriesLabel = new Label("Calories: " + selectedFood.getCalories());
        Label proteinLabel = new Label("Protein: " + selectedFood.getProtein());
        Label fatLabel = new Label("Fat: " + selectedFood.getFat());
        Label carbsLabel = new Label("Carbs: " + selectedFood.getCarbs());

        Button logButton = new Button("Log This Food");
        logButton.setOnAction(e -> {
            LocalDate today = LocalDate.now();
            String date = today.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            String time = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
            selectedFood.setDate(date);
            selectedFood.setDay(time);
            Database.logFood(user.getUsername(), selectedFood, date, time);

            // Update the user's food log
            user.logFood(selectedFood, date, time);
            updateTodayNutrients();
            logStage.close();
        });

        logLayout.getChildren().addAll(foodNameLabel, caloriesLabel, proteinLabel, fatLabel, carbsLabel, logButton);

        Scene logScene = new Scene(logLayout, 300, 200);
        logStage.setScene(logScene);
        logStage.setTitle("Log Food");
        logStage.show();
    }

    private void initializeNutritionSection() {
        nutritionSection = new VBox(10);
        nutritionSection.setPadding(new Insets(10));
        nutritionSection.setStyle("-fx-border-color: black; -fx-border-width: 1; -fx-border-insets: 0; -fx-border-style: solid;");
        nutritionSection.getChildren().add(new Label("Nutrition Details"));

        Button viewNutritionButton = new Button("View Nutrition Details");
        viewNutritionButton.setOnAction(e -> {
            NutritionDetailController nutritionDetailController = new NutritionDetailController(primaryStage, user);
            nutritionDetailController.updateNutritionDetails(); // Ensure data is updated before showing
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

    public BorderPane getView() {
        return mainLayout;
    }
}
