package adhdmc.simpleprefixes.util;

import adhdmc.simpleprefixes.SimplePrefixes;
import adhdmc.simpleprefixes.config.Config;
import adhdmc.simpleprefixes.util.saving.PlayerPDC;
import adhdmc.simpleprefixes.util.saving.SaveHandler;
import adhdmc.simpleprefixes.util.saving.YMLFile;
import me.clip.placeholderapi.PlaceholderAPI;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.NamespacedKey;
import org.bukkit.OfflinePlayer;
import org.bukkit.advancement.Advancement;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

public class PrefixUtil {

    public static SaveHandler saveHandler;

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
        if (prefixId == null) return defaultTag(p);
        ConfigurationSection section = SimplePrefixes.getPlugin().getConfig().getConfigurationSection(prefixId);
        if (section == null) return defaultTag(p);
        if (section.getBoolean("check-always", false) && !isEarnedPrefix(p, section)) {
            saveHandler.setPrefixId(p, "");
            return defaultTag(p);
        }
        String prefix = section.getString("prefix");
        if (prefix == null) return defaultTag(p);
        return PlaceholderAPI.setPlaceholders(p, prefix);
    }

    /**
     * Determines if the player has earned the prefix in prefixId
     * Check is done every time the placeholder is needed.
     * @param p The player.
     * @param section Configuration section of the ID.
     * @return True if all criteria are met, false otherwise.
     */
    public static boolean isEarnedPrefix(OfflinePlayer p, ConfigurationSection section) {
        if (section == null) return false;
        // If the player is not online, assume the prefix is earned.
        if (!p.isOnline()) return true;
        // TODO: Permission Check (Online)
        // TODO: Statistic Check (Offline)
        // TODO: Advancement Check (?)
        // TODO: Placeholder Check (PAPI)
        // TODO: Make dynamically configurable.
        return true;
    }

    public static String defaultTag(OfflinePlayer p) {
        return PlaceholderAPI.setPlaceholders(p, Config.getDefaultTag());
    }

}
