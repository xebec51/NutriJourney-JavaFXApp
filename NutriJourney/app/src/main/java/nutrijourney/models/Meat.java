package nutrijourney.models;

public class Meat extends Food {
    public Meat(String name, int calories) {
        super(name, calories);
    }

    @Override
    public void calculateCalories() {
        // Implementasi spesifik untuk menghitung kalori daging
    }
}