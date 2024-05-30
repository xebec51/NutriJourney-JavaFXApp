package nutrijourney.models;

public class Fruit extends Food {
    public Fruit(String name, double calories, double protein, double fat, double carbs, double water) {
        super(name, calories, protein, fat, carbs, water);
    }

    @Override
    public void displayInfo() {
        System.out.println("Fruit: " + getName());
    }
}
