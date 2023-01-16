package adhdmc.simpleprefixes.util;

import adhdmc.simpleprefixes.SimplePrefixes;
import adhdmc.simpleprefixes.config.Config;
import adhdmc.simpleprefixes.util.saving.PlayerPDC;
import adhdmc.simpleprefixes.util.saving.SaveHandler;
import adhdmc.simpleprefixes.util.saving.YMLFile;
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

    public void loadSaveHandler() {
        switch (Config.getSavingType()) {
            case PDC -> saveHandler = new PlayerPDC();
            case FILE -> saveHandler = new YMLFile();
            // TODO: SQLite Implementation?
        }
        saveHandler.init();
    }

    public String getPrefix(String id) {
        if (id == null || id.isEmpty()) return Config.getDefaultPrefix();
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
