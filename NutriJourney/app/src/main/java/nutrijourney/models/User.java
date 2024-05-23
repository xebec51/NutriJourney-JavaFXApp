package nutrijourney.models;

public class User {
    private String username;
    private String password;
    private int nutritionalIntake;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.nutritionalIntake = 0;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getNutritionalIntake() {
        return nutritionalIntake;
    }

    public void setNutritionalIntake(int nutritionalIntake) {
        this.nutritionalIntake = nutritionalIntake;
    }
}