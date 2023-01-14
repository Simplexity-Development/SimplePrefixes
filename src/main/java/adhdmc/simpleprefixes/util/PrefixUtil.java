package adhdmc.simpleprefixes.util;

import adhdmc.simpleprefixes.SimplePrefixes;
import adhdmc.simpleprefixes.config.Config;
import adhdmc.simpleprefixes.util.saving.PlayerPDC;
import adhdmc.simpleprefixes.util.saving.SaveHandler;
import adhdmc.simpleprefixes.util.saving.YMLFile;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.ConfigurationSection;

public class PrefixUtil {

    private static PrefixUtil instance;

    private SaveHandler saveHandler;

    private PrefixUtil() {}

    public static PrefixUtil getInstance() {
        if (instance == null) instance = new PrefixUtil();
        return instance;
    }

    /**
     * Determines if the player has earned the prefix in prefixId
     * Check is done every time the placeholder is needed.
     * @param p The player.
     * @param prefixId The prefix ID as provided in the configuration.
     * @return True if all criteria are met, false otherwise.
     */
    public boolean isEarnedPrefix(OfflinePlayer p, String prefixId) {
        ConfigurationSection prefixConfig = SimplePrefixes.getPlugin().getConfig().getConfigurationSection(prefixId);
        if (prefixConfig == null) return false;
        // TODO: Permission Check
        // TODO: Statistic Check
        // TODO: Advancement Check
        // TODO: Make dynamically configurable.
        return true;
    }

    public void loadSaveHandler() {
        switch (Config.getSavingType()) {
            case PDC -> saveHandler = new PlayerPDC();
            case FILE -> saveHandler = new YMLFile();
            // TODO: SQLite Implementation?
        }
        saveHandler.init();
    }

    public String getPrefix(String id) {
        if (id == null) return Config.getDefaultPrefix();
        ConfigurationSection prefixConfig = SimplePrefixes.getPlugin().getConfig().getConfigurationSection(id);
        if (prefixConfig == null) return Config.getDefaultPrefix();
        return prefixConfig.getString("prefix", "");
    }

    public ConfigurationSection getPrefixConfig(String id) {
        if (id == null) return null;
        // TODO: Move prefix config into it's own separate config.
        if (id.equalsIgnoreCase("saving-type") || id.equalsIgnoreCase("default-prefix")) return null;
        return SimplePrefixes.getPlugin().getConfig().getConfigurationSection(id);
    }

    public void setPrefix(OfflinePlayer p, String id) { saveHandler.setPrefixId(p, id); }
    public String getPlayerPrefix(OfflinePlayer p) { return saveHandler.getPrefixId(p); }

}
