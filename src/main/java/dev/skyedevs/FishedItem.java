package dev.skyedevs;

public class FishedItem {
    private final String itemName;
    private final double modifier;

    public FishedItem(String itemName, double modifier) {
        this.itemName = itemName;
        this.modifier = modifier;
    }

    public FishedItem(String itemName, int enchantAmount) {
        this.itemName = itemName;
        if (enchantAmount == 0) {
            modifier = -1.0;
        } else {
            modifier = 0.2 * enchantAmount;
        }
    }

    public double getModifier() {
        return Utils.getCorrectRounding(modifier);
    }

    public String toString() {
        return itemName;
    }
}
