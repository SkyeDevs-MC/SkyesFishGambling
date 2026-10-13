package dev.skyedevs;

import java.util.Objects;

public class Main {
    static void main() {
        ConfigManager.createPaths();

        while (true) {
            FishGambling game = new FishGambling();
            int castCount = 10;
            int playerCount = Integer.parseInt(IO.readln("Enter the number of players: "));
            boolean usingGroupPool = false;
            if (playerCount > 1) {
                usingGroupPool = Objects.equals(IO.readln("Would players like to use group bet (y/N): "), "y");
            }
            IO.println();
            for (int i = 1; i <= playerCount; i++) {
                IO.println("Player " + i + ":");
                game.addPlayer();
                IO.println();
            }

            try {
                for (int i = 1; i <= castCount; i++) {
                    IO.println("Cast " + i + ":");
                    IO.println("Current Multiplier: " + game.getMultiplier() + "x");
                    game.addFishedItem();
                    IO.println();
                }

                IO.println("Final Multiplier: " + game.getMultiplier() + "x");
                IO.println(game.getPayCommands(usingGroupPool));
            } catch (Exception e) {
                if (e.getMessage().equals(Utils.interferenceMessage)) {
                    IO.println('\n' + e.getMessage());
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
