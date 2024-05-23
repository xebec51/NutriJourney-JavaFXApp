package nutrijourney.models;

public class Fruit extends Food {
    public Fruit(String name, int calories) {
        super(name, calories);
    }

    @Override
    public void calculateCalories() {
        // Implementasi spesifik untuk menghitung kalori buah
    }
}