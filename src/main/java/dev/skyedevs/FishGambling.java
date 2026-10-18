package dev.skyedevs;

import java.util.ArrayList;
import java.util.HashSet;

public class FishGambling {
    private final ArrayList<FishedItem> fishedItems;
    private final ArrayList<Player> players;
    private final HashSet<String> trashItemNames;
    private final HashSet<String> treasureItemNames;
    private final HashSet<String> enchantedItemNames;

    public FishGambling() {
        fishedItems = new ArrayList<>();
        players = new ArrayList<>();
        trashItemNames = ConfigManager.getItemsFromFile(ConfigManager.trashItemsPath);
        treasureItemNames = ConfigManager.getItemsFromFile(ConfigManager.treasureItemsPath);
        enchantedItemNames = ConfigManager.getItemsFromFile(ConfigManager.enchantedItemsPath);
    }

    public void addPlayer(Player player) {
        players.add(player);
    }

    public void addFishedItem() {
        while (true) {
            String itemName = IO.readln("Enter the name of the item (capitalize each word): ");
            if (trashItemNames.contains(itemName)) {
                fishedItems.add(new FishedItem(itemName, -0.2));
                break;
            } else if (treasureItemNames.contains(itemName)) {
                fishedItems.add(new FishedItem(itemName, 0.2));
                break;
            } else if (enchantedItemNames.contains(itemName)) {
                int enchantCount = Integer.parseInt(
                        IO.readln("How many enchants does the item have (put 0 if the item has a curse): "));
                fishedItems.add(new FishedItem(itemName, enchantCount));
                break;
            } else if (itemName.equals("Interference")) {
                throw new RuntimeException(Utils.interferenceMessage);
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

        return Utils.getCorrectRounding(multiplier);
    }

    private boolean isAllSameItem() {
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

    private double calculateTotalBet() {
        double totalBet = 0.0;
        double multiplier = getMultiplier();
        for (Player player : players) {
            totalBet += player.getInitialBet();
        }

        return Utils.getCorrectRounding(totalBet * multiplier, 2);
    }

    public String getPayCommands(boolean usingGroupPool) {
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
            if (usingGroupPool) {
                double totalMoney = calculateTotalBet();
                for (Player player : players) {
                    commands.append("/pay ")
                            .append(player.getUsername())
                            .append(" ")
                            .append(Utils.getCorrectRounding(totalMoney / players.size(), 2));
                    if (player != players.getLast()) {
                        commands.append('\n');
                    }
                }
            } else {
                for (Player player : players) {
                    commands.append("/pay ")
                            .append(player.getUsername())
                            .append(" ")
                            .append(Utils.formatNumber(Utils.getCorrectRounding(player.getInitialBet() * multiplier, 2)));
                    if (player != players.getLast()) {
                        commands.append('\n');
                    }
                }
            }
        }

        return commands.toString();
    }
}
