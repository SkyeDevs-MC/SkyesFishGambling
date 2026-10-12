package dev.skyedevs;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class ConfigManager {
    public static final Path trashItemsPath = (getUserConfigFolder("FishGambling/trashItems.txt"));
    public static final Path treasureItemsPath = (getUserConfigFolder("FishGambling/treasureItems.txt"));
    public static final Path enchantedItemsPath = (getUserConfigFolder("FishGambling/enchantedItems.txt"));

    private static Path getUserConfigFolder(String appName) {
        String os = System.getProperty("os.name").toLowerCase();
        Path configPath;

        if (os.contains("win")) {
            String appData = System.getenv("APPDATA");
            if (appData != null) {
                configPath = Paths.get(appData);
            } else {
                configPath = Paths.get(System.getProperty("user.home"), "AppData", "Roaming");
            }
        } else if (os.contains("mac")) {
            configPath = Paths.get(System.getProperty("user.home"), "Library", "Application Support");
        } else {
            String xdgConfig = System.getenv("XDG_CONFIG_HOME");
            if (xdgConfig != null && !xdgConfig.isEmpty()) {
                configPath = Paths.get(xdgConfig);
            } else {
                configPath = Paths.get(System.getProperty("user.home"), ".config");
            }
        }

        return configPath.resolve(appName);
    }

    public static void createPaths() {
        try {
            List<String> defaultTrash = List.of("Raw Cod", "Raw Salmon", "Tropical Fish");
            List<String> defaultTreasure = List.of("Saddle", "Name Tag", "Pufferfish", "Nautilus Shell");
            List<String> defaultEnchanted = List.of("Enchanted Book", "Bow", "Fishing Rod");

            createFileWithDefaults(trashItemsPath, defaultTrash);
            createFileWithDefaults(treasureItemsPath, defaultTreasure);
            createFileWithDefaults(enchantedItemsPath, defaultEnchanted);

        } catch (IOException e) {
            System.err.println("Failed to initialize FishGambling configuration files!");
            e.printStackTrace();
        }
    }

    private static void createFileWithDefaults(Path filePath, List<String> defaultLines) throws IOException {
        if (filePath.getParent() != null && !Files.exists(filePath.getParent())) {
            Files.createDirectories(filePath.getParent());
        }

        if (!Files.exists(filePath)) {
            Files.write(filePath, defaultLines);
        }
    }

    public static ArrayList<String> getItemsFromFile(Path itemFilePath) {
        ArrayList<String> trashItems = new ArrayList<>();

        try (Stream<String> lines = Files.lines(itemFilePath)) {
            lines.forEach(trashItems::add);
        } catch (Exception e) {
            System.err.println("Error reading file: " + e.getMessage());
            throw new RuntimeException();
        }

        return trashItems;
    }
}
