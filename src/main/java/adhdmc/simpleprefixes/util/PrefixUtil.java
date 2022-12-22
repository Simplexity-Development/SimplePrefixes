package adhdmc.simpleprefixes.util;

import adhdmc.simpleprefixes.SimplePrefixes;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

public class PrefixUtil {

    /**
     * Determines if the player has earned the prefix in prefixId
     * Check is done every time the placeholder is needed.
     * @param p The player.
     * @param prefixId The prefix ID as provided in the configuration.
     * @return True if all criteria are met, false otherwise.
     */
    public static boolean isEarnedPrefix(Player p, String prefixId) {
        ConfigurationSection prefixConfig = SimplePrefixes.getPlugin().getConfig().getConfigurationSection(prefixId);
        if (prefixConfig == null) return false;
        if (prefixConfig.contains("permission") && !p.hasPermission(prefixConfig.getString("permission", ""))) return false;
        // TODO: Statistic Check
        // TODO: Advancement Check
        return true;
    }

}
