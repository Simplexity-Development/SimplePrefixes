package simplexity.simpleprefixes.config;

import simplexity.simpleprefixes.SimplePrefixes;
import simplexity.simpleprefixes.util.Message;
import org.bukkit.configuration.file.FileConfiguration;


public class Config {

    public enum SAVING_TYPE { PDC, FILE }
    private static SAVING_TYPE savingType = SAVING_TYPE.PDC;
    private static String defaultPrefix = "<white>[<gray>Player</gray>]</white> ";
    private static String prefixMenuName = "<bold>Prefix Menu</bold>";

    public static void loadConfig() {
        SimplePrefixes.getPlugin().reloadConfig();
        FileConfiguration config = SimplePrefixes.getPlugin().getConfig();
        try {
            savingType = SAVING_TYPE.valueOf(config.getString("saving-type", "pdc").toUpperCase());
        } catch (IllegalArgumentException e) {
            SimplePrefixes.getPlugin().getLogger().warning(Message.LOGGER_INVALID_CONFIG_SAVING_TYPE.getMessage());
        }
        defaultPrefix = config.getString("default-prefix", defaultPrefix);
        prefixMenuName = config.getString("prefix-menu-name", prefixMenuName);
    }

    public static SAVING_TYPE getSavingType() { return savingType; }
    public static String getDefaultPrefix() { return defaultPrefix; }
    public static String getPrefixMenuName() { return prefixMenuName; }

}
