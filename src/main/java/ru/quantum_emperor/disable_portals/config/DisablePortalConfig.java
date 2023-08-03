package ru.quantum_emperor.disable_portals.config;

import net.fabricmc.loader.api.FabricLoader;
import ru.quantum_emperor.disable_portals.DisablePortalsMod;

import java.io.*;
import java.util.Properties;

public class DisablePortalConfig {
    private static boolean isDisabledNether = true;
    private static boolean isDisabledEnd = true;
    private static boolean isDisabledEden = true;
    private static int darknessDuration = 100;

    private DisablePortalConfig() {

    }

    public static void register() {
        File configFile = new File(FabricLoader.getInstance().getConfigDir().toFile(), "disable_portals.properties");
        loadConfig(configFile);
    }

    public static void saveConfig(File file) {
        try (FileWriter writer = new FileWriter(file)) {
            writer.write("isDisabledNether=true\n");
            writer.write("isDisabledEnd=true\n");
            writer.write("isDisabledEden=true\n");
            writer.write("darknessDuration=100\n");
        } catch (IOException e) {
            DisablePortalsMod.LOGGER.warn("Failed to write data to configuration file");
        }
    }

    public static void loadConfig(File file) {
        Properties properties = new Properties();
        if (!file.exists() || !file.canRead()) {
            saveConfig(file);
            return;
        }
        try {
            FileReader reader = new FileReader(file);
            properties.load(reader);
            isDisabledEden = Boolean.parseBoolean(properties.getProperty("isDisabledEden"));
            isDisabledEnd = Boolean.parseBoolean(properties.getProperty("isDisabledEnd"));
            isDisabledNether = Boolean.parseBoolean(properties.getProperty("isDisabledNether"));
            darknessDuration = Integer.parseInt(properties.getProperty("darknessDuration"));
            reader.close();
        } catch (FileNotFoundException e) {
            DisablePortalsMod.LOGGER.warn("Configuration file not found");
        } catch (IOException | NumberFormatException e) {
            DisablePortalsMod.LOGGER.warn("Unable to read configuration file contents", e);
        }
    }

    public static boolean isDisabledNether() {
        return isDisabledNether;
    }

    public static boolean isDisableEnd() {
        return isDisabledEnd;
    }

    public static boolean isDisableEden() {
        return isDisabledEden;
    }

    public static int getDarknessDuration() {
        return darknessDuration;
    }
}
