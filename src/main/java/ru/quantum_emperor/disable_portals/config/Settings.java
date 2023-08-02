package ru.quantum_emperor.disable_portals.config;

import net.fabricmc.loader.api.FabricLoader;
import org.spongepowered.asm.mixin.transformer.Config;
import ru.quantum_emperor.disable_portals.ExampleMod;

import java.io.*;
import java.util.Properties;

public class Settings {
    private static boolean isDisabledNether = true;
    private static boolean isDisabledEnd = true;
    private static boolean isDisabledEden = true;
    private Settings() {

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
        } catch (IOException e) {
            ExampleMod.LOGGER.warn("Failed to write data to configuration file");
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
            reader.close();
        } catch (FileNotFoundException e) {
            ExampleMod.LOGGER.warn("Configuration file not found");
        } catch (IOException e) {
            ExampleMod.LOGGER.warn("Unable to read configuration file contents");
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
}
