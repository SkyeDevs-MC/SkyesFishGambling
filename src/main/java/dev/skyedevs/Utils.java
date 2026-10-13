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
}
