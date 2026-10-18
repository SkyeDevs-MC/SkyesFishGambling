package dev.skyedevs;

public class Main {
    static void main() {
        ConfigManager.createPaths();

        while (true) {
            FishGambling game = new FishGambling();
            int castCount = 10;
            int playerCount = 0;
            boolean usingGroupPool = false;
            while (true) {
                IO.println("Player " + (playerCount + 1) + ":");
                String name;
                while (true) {
                    name = IO.readln("Enter the player's username (empty exits): ");
                    if (name.isEmpty() && playerCount == 0) {
                        IO.println("You have not entered a player. Please enter a player.");
                    } else {
                        break;
                    }
                }
                if (name.isEmpty()) {
                    break;
                }
                double initialBet = Utils.parseFormattedNumber(IO.readln("Enter the player's initial bet: "));
                game.addPlayer(new Player(name, initialBet));
                playerCount++;
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
