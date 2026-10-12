package dev.skyedevs;

import java.util.ArrayList;

public class FishingGambling {
    private final ArrayList<FishedItem> fishedItems;
    private final ArrayList<Player> players;
    private final ArrayList<String> trashItemNames;
    private final ArrayList<String> treasureItemNames;
    private final ArrayList<String> enchantedItemNames;

    public FishingGambling() {
        fishedItems = new ArrayList<>();
        players = new ArrayList<>();
        trashItemNames = ConfigManager.getItemsFromFile(ConfigManager.trashItemsPath);
        treasureItemNames = ConfigManager.getItemsFromFile(ConfigManager.treasureItemsPath);
        enchantedItemNames = ConfigManager.getItemsFromFile(ConfigManager.enchantedItemsPath);
    }

    public void addPlayer() {
        String username = IO.readln("Enter the player's username: ");
        double initialBet = Double.parseDouble(IO.readln("Enter the player's initial bet: "));
        players.add(new Player(username, initialBet));
    }

    public void addFishedItem() {
        while (true) {
            String itemName = IO.readln("Enter the name of the item (capitalize each word): ");
            if (trashItemNames.contains(itemName)) {
                fishedItems.add(new TrashItem(itemName));
                break;
            } else if (treasureItemNames.contains(itemName)) {
                fishedItems.add(new TreasureItem(itemName));
                break;
            } else if (enchantedItemNames.contains(itemName)) {
                int enchantCount = Integer.parseInt(
                        IO.readln("How many enchants does the item have (put 0 if the item has a curse): "));
                fishedItems.add(new EnchantedItem(itemName, enchantCount));
                break;
            } else if (itemName.equals("Interference")) {
                throw new RuntimeException(Main.interferenceMessage);
            } else {
                IO.println("Invalid input... Please try again.");
            }
        }
    }

    public double getMultiplier() {
        double multiplier = 1.0;
        for (FishedItem fishedItem : fishedItems) {
            if (multiplier + fishedItem.getModifier() < 0) {
                multiplier = 0.0;
            } else {
                multiplier += fishedItem.getModifier();
            }
        }

        return Main.getCorrectRounding(multiplier);
    }

    public boolean isAllSameItem() {
        if (getMultiplier() == 0.0) {
            boolean sameName = true;
            String name = fishedItems.getFirst().toString();
            for (FishedItem item : fishedItems) {
                if (!name.equals(item.toString())) {
                    sameName = false;
                    break;
                }
            }

            return sameName;
        }

        return false;
    }

    public String getPayCommands() {
        StringBuilder commands = new StringBuilder();
        double multiplier = getMultiplier();
        if (multiplier == 0.0) {
            if (isAllSameItem()) {
                commands.append("Players won a full shulker of alcohol!");
            } else {

                commands.append("No payment back. Multiplier was 0.0x");
            }
        }

        if (commands.isEmpty()) {
            double totalMoney = 0.0;
            for (Player player : players) {
                totalMoney += Main.getCorrectRounding(player.getInitialBet() * multiplier, 2);
            }
            for (Player player : players) {
                commands.append("/pay ")
                        .append(player.getUsername())
                        .append(" ")
                        .append(Main.getCorrectRounding(totalMoney / players.size(), 2));
                if (player != players.getLast()) {
                    commands.append('\n');
                }
            }
        }

        return commands.toString();
    }
}
