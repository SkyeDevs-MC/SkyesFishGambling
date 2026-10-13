package dev.skyedevs;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ConfigManager {
    public static final Path trashItemsPath = Utils.getUserConfigFolder("FishGambling/itemConfig/trashItems.txt");
    public static final Path treasureItemsPath = 
            Utils.getUserConfigFolder("FishGambling/itemConfig/treasureItems.txt");
    public static final Path enchantedItemsPath = 
            Utils.getUserConfigFolder("FishGambling/itemConfig/enchantedItems.txt");

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

    public static HashSet<String> getItemsFromFile(Path itemFilePath) {
        HashSet<String> items = new HashSet<>();

        try (Stream<String> lines = Files.lines(itemFilePath)) {
            return lines.collect(Collectors.toCollection(HashSet::new));
        } catch (Exception e) {
            System.err.println("Error reading file: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
