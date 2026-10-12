package dev.skyedevs;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Main {
    public static final String interferenceMessage = "\nGame ended with interference, no one gets any money...";

    public static double getCorrectRounding(double doubleToRound) {
        BigDecimal bd = new BigDecimal(Double.toString(doubleToRound));
        bd = bd.setScale(1, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public static double getCorrectRounding(double doubleToRound, int roundToPlace) {
        BigDecimal bd = new BigDecimal(Double.toString(doubleToRound));
        bd = bd.setScale(roundToPlace, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    static void main() {
        ConfigManager.createPaths();

        while (true) {
            FishingGambling fishingGambling = new FishingGambling();
            int castCount = 10;
            int playerCount = Integer.parseInt(IO.readln("Enter the number of players: "));
            IO.println();
            for (int i = 1; i <= playerCount; i++) {
                IO.println("Player " + i + ":");
                fishingGambling.addPlayer();
                IO.println();
            }

            try {
                for (int i = 1; i <= castCount; i++) {
                    IO.println("Cast " + i + ":");
                    IO.println("Current Multiplier: " + fishingGambling.getMultiplier() + "x");
                    fishingGambling.addFishedItem();
                    IO.println();
                }

                IO.println("Final Multiplier: " + fishingGambling.getMultiplier() + "x");
                IO.println(fishingGambling.getPayCommands());
            } catch (Exception e) {
                if (e.getMessage().equals(interferenceMessage)) {
                    IO.println(e.getMessage());
                } else {
                    System.exit(1);
                }
            }

            String playAgain = IO.readln("Run again (Y/n): ").toLowerCase();
            if (!(playAgain.equals("y") || playAgain.isEmpty())) {
                break;
            } else {
                IO.println();
            }
        }
    }
}
