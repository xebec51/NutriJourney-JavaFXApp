package nutrijourney.models;

public abstract class Food {
    protected String name;
    protected int calories;

    public Food(String name, int calories) {
        this.name = name;
        this.calories = calories;
    }

    public String getName() {
        return name;
    }

    public int getCalories() {
        return calories;
    }

    public abstract void calculateCalories();
}