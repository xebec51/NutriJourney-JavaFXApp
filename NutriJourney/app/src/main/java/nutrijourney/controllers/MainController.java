package nutrijourney.controllers;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
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
    private TextField searchField;
    private BorderPane mainLayout;
    private Stage primaryStage;
    private User user;
    private int waterIntake;

    public MainController(Stage primaryStage, User user) {
        this.primaryStage = primaryStage;
        this.user = user;
        this.waterIntake = (int) user.getWaterConsumedToday();
        initializeProfileSection(user);
        initializeWeightUpdateSection();
        initializeWaterIntakeSection();
        initializeNutritionLogSection();
        initializeRecipeSection();
        initializeNutritionSection();

        mainLayout = new BorderPane();
        mainLayout.setPadding(new Insets(0)); // Mengatur padding menjadi 0

        VBox centerBox = new VBox(0, profileSection, nutritionLogSection);
        VBox rightBox = new VBox(0, recipeSection, new HBox(0, weightUpdateSection, waterIntakeSection), nutritionSection);

        VBox.setVgrow(profileSection, Priority.ALWAYS);
        VBox.setVgrow(nutritionLogSection, Priority.ALWAYS);
        VBox.setVgrow(recipeSection, Priority.ALWAYS);
        VBox.setVgrow(weightUpdateSection, Priority.ALWAYS);
        VBox.setVgrow(waterIntakeSection, Priority.ALWAYS);
        VBox.setVgrow(nutritionSection, Priority.ALWAYS);

        HBox.setHgrow(weightUpdateSection, Priority.ALWAYS);
        HBox.setHgrow(waterIntakeSection, Priority.ALWAYS);

        mainLayout.setLeft(centerBox);
        mainLayout.setCenter(rightBox);
    }

    public HBox createHeader() {
        HBox header = new HBox();
        header.setPadding(new Insets(0));
        header.setSpacing(0);

        ImageView headerImage = new ImageView(new Image(getClass().getResource("/images/header.png").toExternalForm()));
        headerImage.setFitHeight(110);
        headerImage.setPreserveRatio(true);

        HBox.setHgrow(headerImage, Priority.ALWAYS);
        header.getChildren().add(headerImage);

        return header;
    }

    private void logout() {
        LoginController loginController = new LoginController(primaryStage);
        primaryStage.getScene().setRoot(loginController.getView());
    }

    private void initializeProfileSection(User user) {
        profileSection = new VBox(10);
        profileSection.setPadding(new Insets(10));
        profileSection.setStyle("-fx-border-color: lightgray; -fx-border-width: 1; -fx-border-style: solid;");
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
        waterIntakeSection.setStyle("-fx-border-color: lightgray; -fx-border-width: 1; -fx-border-style: solid;");
        waterIntakeSection.getChildren().add(new Label("Water Intake"));

        waterIntakeLabel = new Label("Water Intake: " + waterIntake + " ml");
        Button addWaterButton = new Button("Add 250 ml");
        addWaterButton.setStyle("-fx-background-color: #006400; -fx-text-fill: #ffffff;");
        addWaterButton.setOnAction(e -> {
            waterIntake += 250;
            waterIntakeLabel.setText("Water Intake: " + waterIntake + " ml");

            Food water = new Food("Water", 0, 0, 0, 0, 250) {
                @Override
                public void displayInfo() {
                }
            };
            LocalDate today = LocalDate.now();
            String date = today.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            String time = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
            water.setDate(date);
            water.setDay(time);
            Database.logFood(user.getUsername(), water, date, time);

            user.logFood(water, date, time);
            updateTodayNutrients();
        });

        waterIntakeSection.getChildren().addAll(waterIntakeLabel, addWaterButton);
    }

    private void initializeNutritionLogSection() {
        nutritionLogSection = new VBox(10);
        nutritionLogSection.setPadding(new Insets(10));
        nutritionLogSection.setStyle("-fx-border-color: lightgray; -fx-border-width: 1; -fx-border-style: solid;");
        nutritionLogSection.getChildren().add(new Label("Daily Nutrition Log"));

        caloriesTodayLabel = new Label("Calories consumed today: " + String.format("%.1f", user.getCaloriesConsumedToday()) + " kcal");
        proteinTodayLabel = new Label("Protein consumed today: " + String.format("%.1f", user.getProteinConsumedToday()) + " g");
        fatTodayLabel = new Label("Fat consumed today: " + String.format("%.1f", user.getFatConsumedToday()) + " g");
        carbsTodayLabel = new Label("Carbs consumed today: " + String.format("%.1f", user.getCarbsConsumedToday()) + " g");
        waterTodayLabel = new Label("Water consumed today: " + String.format("%.1f", user.getWaterConsumedToday()) + " ml");

        nutritionLogSection.getChildren().addAll(caloriesTodayLabel, proteinTodayLabel, fatTodayLabel, carbsTodayLabel, waterTodayLabel);
    }

    private void updateTodayNutrients() {
        caloriesTodayLabel.setText("Calories consumed today: " + String.format("%.1f", user.getCaloriesConsumedToday()) + " kcal");
        proteinTodayLabel.setText("Protein consumed today: " + String.format("%.1f", user.getProteinConsumedToday()) + " g");
        fatTodayLabel.setText("Fat consumed today: " + String.format("%.1f", user.getFatConsumedToday()) + " g");
        carbsTodayLabel.setText("Carbs consumed today: " + String.format("%.1f", user.getCarbsConsumedToday()) + " g");
        waterTodayLabel.setText("Water consumed today: " + String.format("%.1f", user.getWaterConsumedToday()) + " ml");
    }

    private void initializeWeightUpdateSection() {
        weightUpdateSection = new VBox(10);
        weightUpdateSection.setPadding(new Insets(10));
        weightUpdateSection.setStyle("-fx-border-color: lightgray; -fx-border-width: 1; -fx-border-style: solid;");
        weightUpdateSection.getChildren().add(new Label("Update Weight"));

        TextField weightField = new TextField();
        weightField.setPromptText("Enter new weight (kg)");

        Button updateWeightButton = new Button("Update Weight");
        updateWeightButton.setStyle("-fx-background-color: #006400; -fx-text-fill: #ffffff;");
        updateWeightButton.setOnAction(e -> {
            try {
                double newWeight = Double.parseDouble(weightField.getText());
                user.setWeight(newWeight);

                Database.updateUserWeight(user.getUsername(), newWeight);

                weightLabel.setText("Weight: " + user.getWeight() + " kg");
                bmiLabel.setText("BMI: " + String.format("%.2f", user.calculateBMI()));
                bmiCategoryLabel.setText("BMI Category: " + user.getBMICategory());

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
        recipeSection.setStyle("-fx-border-color: lightgray; -fx-border-width: 1; -fx-border-style: solid;");
        recipeSection.getChildren().add(new Label("Recipes"));

        searchField = new TextField();
        searchField.setPromptText("Search recipes...");
        searchField.textProperty().addListener((observable, oldValue, newValue) -> filterRecipes(newValue));

        recipeListView = new ListView<>();
        recipeListView.setMaxHeight(200); // Membatasi tinggi list view agar tidak terlalu besar
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

        recipeListView.setOnMouseClicked(event -> handleRecipeClick(event));

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
                showFoodDetails(selectedFood);
            }
        }
    }

    private void showFoodDetails(Food food) {
        Stage detailStage = new Stage();
        VBox detailLayout = new VBox(10);
        detailLayout.setPadding(new Insets(10));

        Label foodNameLabel = new Label("Food: " + food.getName());
        Label caloriesLabel = new Label("Calories: " + food.getCalories());
        Label proteinLabel = new Label("Protein: " + food.getProtein());
        Label fatLabel = new Label("Fat: " + food.getFat());
        Label carbsLabel = new Label("Carbs: " + food.getCarbs());

        Button addButton = new Button("Add to Consumed");
        addButton.setStyle("-fx-background-color: #006400; -fx-text-fill: #ffffff;");
        addButton.setOnAction(e -> {
            LocalDate today = LocalDate.now();
            String date = today.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            String time = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
            food.setDate(date);
            food.setDay(time);
            user.logFood(food, date, time);
            updateTodayNutrients();
            detailStage.close();
            showAlert("Food Logged", food.getName() + " has been added to your consumed list.");
        });

        Button editButton = new Button("Edit");
        editButton.setStyle("-fx-background-color: #fda50f; -fx-text-fill: #ffffff;");
        editButton.setOnAction(e -> showEditFoodForm(food, detailStage));

        Button deleteButton = new Button("Delete");
        deleteButton.setStyle("-fx-background-color: #8B0000; -fx-text-fill: #ffffff;");
        deleteButton.setOnAction(e -> {
            Database.deleteFoodLog(user.getUsername(), food.getName(), food.getDate(), food.getDay());
            user.getFoodLog().remove(food);
            updateTodayNutrients();
            detailStage.close();
        });

        detailLayout.getChildren().addAll(foodNameLabel, caloriesLabel, proteinLabel, fatLabel, carbsLabel, addButton, editButton, deleteButton);

        Scene detailScene = new Scene(detailLayout, 300, 300);
        detailStage.setScene(detailScene);
        detailStage.setTitle("Food Details");
        detailStage.show();
    }

    private void showEditFoodForm(Food food, Stage detailStage) {
        Stage editStage = new Stage();
        VBox editLayout = new VBox(10);
        editLayout.setPadding(new Insets(10));

        TextField foodNameField = new TextField(food.getName());
        TextField caloriesField = new TextField(Double.toString(food.getCalories()));
        TextField proteinField = new TextField(Double.toString(food.getProtein()));
        TextField fatField = new TextField(Double.toString(food.getFat()));
        TextField carbsField = new TextField(Double.toString(food.getCarbs()));

        Button saveButton = new Button("Save");
        saveButton.setStyle("-fx-background-color: #006400; -fx-text-fill: #ffffff;");
        saveButton.setOnAction(e -> {
            food.setName(foodNameField.getText());
            food.setCalories(Double.parseDouble(caloriesField.getText()));
            food.setProtein(Double.parseDouble(proteinField.getText()));
            food.setFat(Double.parseDouble(fatField.getText()));
            food.setCarbs(Double.parseDouble(carbsField.getText()));

            Database.updateFoodLog(user.getUsername(), food, food.getDate(), food.getDay());
            updateTodayNutrients();
            editStage.close();
            detailStage.close();
        });

        editLayout.getChildren().addAll(new Label("Edit Food"), foodNameField, caloriesField, proteinField, fatField, carbsField, saveButton);

        Scene editScene = new Scene(editLayout, 300, 250);
        editStage.setScene(editScene);
        editStage.setTitle("Edit Food");
        editStage.show();
    }

    private void initializeNutritionSection() {
        nutritionSection = new VBox(10);
        nutritionSection.setPadding(new Insets(10));
        nutritionSection.setStyle("-fx-border-color: lightgray; -fx-border-width: 1; -fx-border-style: solid;");
        nutritionSection.getChildren().add(new Label("Nutrition Details"));

        Button viewNutritionButton = new Button("View Nutrition Details");
        viewNutritionButton.setStyle("-fx-background-color: #006400; -fx-text-fill: #ffffff;");
        viewNutritionButton.setOnAction(e -> {
            NutritionDetailController nutritionDetailController = new NutritionDetailController(primaryStage, user);
            Scene nutritionScene = nutritionDetailController.getScene();
            VBox root = new VBox(createHeader(), nutritionScene.getRoot()); // Tambahkan header di sini
            nutritionScene.setRoot(root);
            primaryStage.setScene(nutritionScene);
        });

        Button logoutButton = new Button("Logout");
        logoutButton.setStyle("-fx-background-color: #006400; -fx-text-fill: #ffffff;");
        logoutButton.setOnAction(e -> logout());

        nutritionSection.getChildren().addAll(viewNutritionButton, logoutButton);
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public BorderPane getView() {
        return mainLayout;
    }
}
