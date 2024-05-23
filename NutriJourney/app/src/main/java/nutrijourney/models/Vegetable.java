package nutrijourney.models;

public class Vegetable extends Food {
    public Vegetable(String name, int calories) {
        super(name, calories);
    }

    @Override
    public void calculateCalories() {
        // Implementasi spesifik untuk menghitung kalori sayuran
    }
}