package adhdmc.simpleprefixes.util;

import adhdmc.simpleprefixes.SimplePrefixes;
import adhdmc.simpleprefixes.config.Config;
import adhdmc.simpleprefixes.util.saving.PlayerPDC;
import adhdmc.simpleprefixes.util.saving.SaveHandler;
import adhdmc.simpleprefixes.util.saving.YMLFile;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

public class PrefixUtil {

    public static SaveHandler saveHandler;

    /**
     * Determines if the player has earned the prefix in prefixId
     * Check is done every time the placeholder is needed.
     * @param p The player.
     * @param prefixId The prefix ID as provided in the configuration.
     * @return True if all criteria are met, false otherwise.
     */
    public static boolean isEarnedPrefix(OfflinePlayer p, String prefixId) {
        ConfigurationSection prefixConfig = SimplePrefixes.getPlugin().getConfig().getConfigurationSection(prefixId);
        if (prefixConfig == null) return false;
        // TODO: Permission Check
        // TODO: Statistic Check
        // TODO: Advancement Check
        // TODO: Make dynamically configurable.
        return true;
    }

    public static void loadSaveHandler() {
        switch (Config.getSavingType()) {
            case PDC -> saveHandler = new PlayerPDC();
            case FILE -> saveHandler = new YMLFile();
            // TODO: SQLite Implementation?
        }
        saveHandler.init();
    }

    public static String getPlayerPrefix(OfflinePlayer p) {
        String prefixId = saveHandler.getPrefixId(p);
        prefixId = prefixId == null ? "" : prefixId;
        if (!isEarnedPrefix(p, prefixId)) return "";
        return getPrefix(prefixId);
    }

    public static String getPrefix(String id) {
        ConfigurationSection prefixConfig = SimplePrefixes.getPlugin().getConfig().getConfigurationSection(id);
        if (prefixConfig == null) return "";
        return prefixConfig.getString("display-name", "");
    }

}
