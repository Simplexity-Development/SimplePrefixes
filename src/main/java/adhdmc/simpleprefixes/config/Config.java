package adhdmc.simpleprefixes.config;

import adhdmc.simpleprefixes.SimplePrefixes;
import org.bukkit.configuration.file.FileConfiguration;

public class Config {

    public enum SAVING_TYPE { PDC, FILE }
    private static SAVING_TYPE savingType = SAVING_TYPE.PDC;

    public static void loadConfig() {
        FileConfiguration config = SimplePrefixes.getPlugin().getConfig();
        try {
            savingType = SAVING_TYPE.valueOf(config.getString("saving_type", "pdc").toUpperCase());
        } catch (IllegalArgumentException e) {
            // TODO: Make message configurable.
            SimplePrefixes.getPlugin().getLogger().warning("Configuration option saving_type is not a valid configuration!");
        }
    }

    public static SAVING_TYPE getSavingType() { return savingType; }

}
