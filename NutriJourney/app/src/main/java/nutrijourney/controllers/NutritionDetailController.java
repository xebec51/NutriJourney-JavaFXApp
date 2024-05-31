package nutrijourney.controllers;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.HBox;
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
    private HBox mainLayout;
    private VBox summaryBox;
    private VBox foodListBox;
    private MenuBar menuBar;
    private Map<String, List<Food>> foodsByDay;

    public NutritionDetailController(Stage stage, User user) {
        this.stage = stage;
        this.user = user;

        loadFoodsByDay();
        createMenuBar();
        initializeNutritionLayout();

        Scene scene = new Scene(new VBox(menuBar, mainLayout), 800, 600); // Ukuran scene
        stage.setTitle("Nutrition Details");
        stage.setScene(scene);
    }

    private void initializeNutritionLayout() {
        mainLayout = new HBox(20); // Meningkatkan jarak antar elemen
        mainLayout.setPadding(new Insets(20)); // Meningkatkan padding

        summaryBox = new VBox(10); // Kotak untuk ringkasan nutrisi
        summaryBox.setPadding(new Insets(10));
        summaryBox.setStyle("-fx-border-color: lightgray; -fx-border-width: 1px; -fx-background-color: #f9f9f9;");

        foodListBox = new VBox(10); // Kotak untuk daftar makanan/minuman
        foodListBox.setPadding(new Insets(10));
        foodListBox.setStyle("-fx-border-color: lightgray; -fx-border-width: 1px; -fx-background-color: #f9f9f9;");

        HBox.setHgrow(summaryBox, Priority.ALWAYS);
        HBox.setHgrow(foodListBox, Priority.ALWAYS);

        mainLayout.getChildren().addAll(summaryBox, foodListBox);

        updateNutritionDetails(null); // Initialize with no date selected
    }

    private void createMenuBar() {
        menuBar = new MenuBar();
        Menu dateMenu = new Menu("Select Date");

        // Add MenuItems for each date
        for (String date : foodsByDay.keySet()) {
            MenuItem dateItem = new MenuItem(date);
            dateItem.setOnAction(e -> updateNutritionDetails(date));
            dateMenu.getItems().add(dateItem);
        }

        menuBar.getMenus().add(dateMenu);
    }

    private void loadFoodsByDay() {
        List<Food> allFoods = Database.getAllLoggedFoods(user.getUsername());
        foodsByDay = allFoods.stream()
                .collect(Collectors.groupingBy(Food::getDate));
    }

    private void updateNutritionSummary(VBox summaryBox, String date) {
        summaryBox.getChildren().clear();

        Label headerLabel = new Label("Nutrition Summary");
        headerLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        summaryBox.getChildren().add(headerLabel);

        if (date != null) {
            List<Food> foods = foodsByDay.get(date);
            int totalCalories = (int) foods.stream().mapToDouble(Food::getCalories).sum();
            int totalProtein = (int) foods.stream().mapToDouble(Food::getProtein).sum();
            int totalFat = (int) foods.stream().mapToDouble(Food::getFat).sum();
            int totalCarbs = (int) foods.stream().mapToDouble(Food::getCarbs).sum();
            int totalWater = (int) foods.stream().mapToDouble(Food::getWater).sum();

            summaryBox.getChildren().addAll(
                    createSummaryLabel("Total Calories: ", totalCalories + " kcal"),
                    createSummaryLabel("Total Protein: ", totalProtein + " g"),
                    createSummaryLabel("Total Fat: ", totalFat + " g"),
                    createSummaryLabel("Total Carbs: ", totalCarbs + " g"),
                    createSummaryLabel("Total Water: ", totalWater + " ml")
            );
        } else {
            summaryBox.getChildren().add(new Label("Select a date to view summary."));
        }

        Button backButton = new Button("Back to Main Menu");
        backButton.setOnAction(e -> showMainMenu());
        summaryBox.getChildren().add(backButton);

        VBox.setVgrow(summaryBox, Priority.ALWAYS); // Memastikan summaryBox mengisi ruang yang ada
    }

    public void updateNutritionDetails(String date) {
        updateNutritionSummary(summaryBox, date);
        foodListBox.getChildren().clear();

        Label headerLabel = new Label("Consumed Food/Drink");
        headerLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        foodListBox.getChildren().add(headerLabel);

        if (date != null) {
            List<Food> foods = foodsByDay.get(date);

            // Create titled panes for each day
            TitledPane dayPane = createDayPane(date, foods);
            foodListBox.getChildren().add(dayPane);
        } else {
            foodListBox.getChildren().add(new Label("Select a date to view details."));
        }

        VBox.setVgrow(foodListBox, Priority.ALWAYS); // Memastikan foodListBox mengisi ruang yang ada
    }

    private Label createSummaryLabel(String text, String value) {
        Label label = new Label(text + value);
        label.setStyle("-fx-font-size: 14px;");
        return label;
    }

    private TitledPane createDayPane(String date, List<Food> foods) {
        ListView<Food> dayListView = new ListView<>();
        dayListView.setCellFactory(param -> new ListCell<Food>() {
            @Override
            protected void updateItem(Food item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else if (item.getName().equals("Water")) {
                    setText(item.getName() + " - " + item.getWater() + " ml");
                } else {
                    setText(item.getName() + " - " + item.getCalories() + " kcal");
                }
            }
        });
        dayListView.getItems().setAll(foods);

        TitledPane dayPane = new TitledPane(date, dayListView);
        dayPane.setCollapsible(true); // Enable collapsing
        dayPane.setStyle("-fx-font-size: 14px;");
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
