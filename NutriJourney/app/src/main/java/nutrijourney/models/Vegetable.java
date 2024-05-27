package nutrijourney.models;

public class Vegetable extends Food { // Pewarisan
    public Vegetable(String name, double calories, double protein, double fat, double carbs) {
        super(name, calories, protein, fat, carbs);
    }

    @Override
    public void displayInfo() { // Polimorfisme
        System.out.println("Vegetable: " + getName() + ", Calories: " + getCalories());
    }
}
