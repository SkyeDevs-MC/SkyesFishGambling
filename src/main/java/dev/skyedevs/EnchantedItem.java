package dev.skyedevs;

public class EnchantedItem extends FishedItem{
    public EnchantedItem(String itemName, int enchantCount) {
        this.itemName = itemName;
        if (enchantCount == 0) {
            modifier = -1.0;
        } else {
            modifier = 0.2 * enchantCount;
        }
    }
}
