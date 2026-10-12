package dev.skyedevs;

public abstract class FishedItem {
    protected String itemName;
    protected double modifier;

    public double getModifier() {
        return Main.getCorrectRounding(modifier);
    }

    public String toString() {
        return itemName;
    }
}
