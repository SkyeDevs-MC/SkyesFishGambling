package dev.skyedevs;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Utils {
    public static final String interferenceMessage = "Game ended with interference, no one gets any money...";

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

    public static Path getUserConfigFolder(String appName) {
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

    public static double parseFormattedNumber(String input) {
        if (input == null || input.isBlank()) {
            throw new IllegalArgumentException("Input cannot be null or blank");
        }

        String trimmed = input.trim().toLowerCase();
        char lastChar = trimmed.charAt(trimmed.length() - 1);

        double multiplier = 1.0;
        int endIndex = trimmed.length();

        switch (lastChar) {
            case 'k':
                multiplier = 1_000.0;
                endIndex--;
                break;
            case 'm':
                multiplier = 1_000_000.0;
                endIndex--;
                break;
            case 'b':
                multiplier = 1_000_000_000.0;
                endIndex--;
                break;
            default:
                break;
        }

        String numericPart = trimmed.substring(0, endIndex);
        double baseValue = Double.parseDouble(numericPart);

        return baseValue * multiplier;
    }

    private static final char[] SUFFIXES = {'k', 'm', 'b', 't'};
    private static final double[] THRESHOLDS = {
            1_000.0,
            1_000_000.0,
            1_000_000_000.0,
            1_000_000_000_000.0
    };

    public static String formatNumber(double value) {
        if (Math.abs(value) < 1000.0) {
            return String.valueOf(value);
        }

        int unitIndex = -1;
        double scaledValue = value;

        for (int i = 0; i < THRESHOLDS.length; i++) {
            if (Math.abs(value) >= THRESHOLDS[i]) {
                unitIndex = i;
                scaledValue = value / THRESHOLDS[i];
            }
        }

        return scaledValue + String.valueOf(SUFFIXES[unitIndex]);
    }
}
