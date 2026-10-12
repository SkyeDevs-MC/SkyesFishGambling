package dev.skyedevs;

public class Player {
    private final String username;
    private final double initialBet;

    public Player(String username, double initialBet) {
        this.username = username;
        this.initialBet = initialBet;
    }

    String getUsername() {
        return username;
    }

    double getInitialBet() {
        return Main.getCorrectRounding(initialBet, 2);
    }

    public String toString() {
        return getUsername();
    }
}
