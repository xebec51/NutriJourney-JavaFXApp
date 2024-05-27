package nutrijourney.models;

public class Meat extends Food { // Pewarisan
    public Meat(String name, double calories, double protein, double fat, double carbs) {
        super(name, calories, protein, fat, carbs);
    }

    @Override
    public void displayInfo() { // Polimorfisme
        System.out.println("Meat: " + getName() + ", Calories: " + getCalories());
    }
}
