package nutrijourney.models;

public class Fruit extends Food { // Pewarisan
    public Fruit(String name, double calories, double protein, double fat, double carbs) {
        super(name, calories, protein, fat, carbs);
    }

    @Override
    public void displayInfo() { // Polimorfisme
        System.out.println("Fruit: " + getName() + ", Calories: " + getCalories());
    }
}
