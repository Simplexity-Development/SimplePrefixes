package adhdmc.simpleprefixes.config;

import adhdmc.simpleprefixes.SimplePrefixes;
import org.bukkit.configuration.file.FileConfiguration;

public class Config {

    public enum SAVING_TYPE { PDC, FILE }
    private static SAVING_TYPE savingType = SAVING_TYPE.PDC;
    private static String defaultTag = "";

    public static void loadConfig() {
        FileConfiguration config = SimplePrefixes.getPlugin().getConfig();
        try {
            savingType = SAVING_TYPE.valueOf(config.getString("saving-type", "pdc").toUpperCase());
        } catch (IllegalArgumentException e) {
            // TODO: Make message configurable.
            SimplePrefixes.getPlugin().getLogger().warning("Configuration option saving-type is not a valid configuration!");
        }
        defaultTag = config.getString("default-tag", "");
    }

    public static SAVING_TYPE getSavingType() { return savingType; }
    public static String getDefaultTag() { return defaultTag; }

}
