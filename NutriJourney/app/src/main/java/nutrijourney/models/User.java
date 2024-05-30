package nutrijourney.models;

import nutrijourney.database.Database;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class User {
    private String username;
    private String password;
    private String fullName;
    private int age;
    private double weight;
    private double height;
    private String gender;
    private double activityLevel;
    private List<Food> foodLog; // List untuk menyimpan konsumsi makanan

    public User(String username, String password, String fullName, int age, double weight, double height, String gender, double activityLevel) {
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.age = age;
        this.weight = weight;
        this.height = height;
        this.gender = gender;
        this.activityLevel = activityLevel;
        this.foodLog = new ArrayList<>();
    }

    // Getter and Setter methods

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getFullName() {
        return fullName;
    }

    public int getAge() {
        return age;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getHeight() {
        return height;
    }

    public String getGender() {
        return gender;
    }

    public double getActivityLevel() {
        return activityLevel;
    }

    public void logFood(Food food, String date, String day) {
        this.foodLog.add(food);
        Database.logFood(this.username, food, date, day);
    }

    public List<Food> getFoodLog() {
        return foodLog;
    }

    public void setFoodLog(List<Food> foodLog) {
        this.foodLog = foodLog;
    }

    public double getTotalCalories() {
        return foodLog.stream().mapToDouble(Food::getCalories).sum();
    }

    public double getTotalProtein() {
        return foodLog.stream().mapToDouble(Food::getProtein).sum();
    }

    public double getTotalFat() {
        return foodLog.stream().mapToDouble(Food::getFat).sum();
    }

    public double getTotalCarbs() {
        return foodLog.stream().mapToDouble(Food::getCarbs).sum();
    }

    public double calculateBMI() {
        return weight / ((height / 100) * (height / 100));
    }

    public String getBMICategory() {
        double bmi = calculateBMI();
        if (bmi < 18.5) {
            return "Underweight";
        } else if (bmi >= 18.5 && bmi < 24.9) {
            return "Normal weight";
        } else if (bmi >= 25 && bmi < 29.9) {
            return "Overweight";
        } else {
            return "Obesity";
        }
    }

    public double calculateBMR() {
        if (gender.equalsIgnoreCase("male")) {
            return 88.362 + (13.397 * weight) + (4.799 * height) - (5.677 * age);
        } else {
            return 447.593 + (9.247 * weight) + (3.098 * height) - (4.330 * age);
        }
    }

    public double calculateTDEE() {
        return calculateBMR() * activityLevel;
    }

    public double calculateDailyCalories() {
        return calculateTDEE();
    }

    public double getDailyCarbs() {
        return calculateDailyCalories() * 0.5 / 4; // 50% of daily calories from carbs, 1g of carbs = 4 kcal
    }

    public double getDailyProtein() {
        return calculateDailyCalories() * 0.2 / 4; // 20% of daily calories from protein, 1g of protein = 4 kcal
    }

    public double getDailyFat() {
        return calculateDailyCalories() * 0.3 / 9; // 30% of daily calories from fat, 1g of fat = 9 kcal
    }

    public double getCaloriesConsumedToday() {
        LocalDate today = LocalDate.now();
        return foodLog.stream()
                .filter(food -> today.equals(LocalDate.parse(food.getDate())))
                .mapToDouble(Food::getCalories)
                .sum();
    }

    public double getProteinConsumedToday() {
        LocalDate today = LocalDate.now();
        return foodLog.stream()
                .filter(food -> today.equals(LocalDate.parse(food.getDate())))
                .mapToDouble(Food::getProtein)
                .sum();
    }

    public double getFatConsumedToday() {
        LocalDate today = LocalDate.now();
        return foodLog.stream()
                .filter(food -> today.equals(LocalDate.parse(food.getDate())))
                .mapToDouble(Food::getFat)
                .sum();
    }

    public double getCarbsConsumedToday() {
        LocalDate today = LocalDate.now();
        return foodLog.stream()
                .filter(food -> today.equals(LocalDate.parse(food.getDate())))
                .mapToDouble(Food::getCarbs)
                .sum();
    }

    public double getWaterConsumedToday() {
        LocalDate today = LocalDate.now();
        return foodLog.stream()
                .filter(food -> today.equals(LocalDate.parse(food.getDate())))
                .mapToDouble(Food::getWater)
                .sum();
    }
}
