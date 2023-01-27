package adhdmc.simpleprefixes.util;

import adhdmc.simpleprefixes.SimplePrefixes;
import org.bukkit.configuration.ConfigurationSection;

import java.util.*;

public class Prefix {

    private static final HashMap<String, Prefix> prefixes = new HashMap<>();

    public final String prefixId;
    public final String displayName;
    public final List<String> description;
    public final String prefix;
    public final boolean verifyAlways;
    public final boolean showWhenLocked;
    public final List<String> requirements;

    private Prefix(String prefixId) {
        ConfigurationSection config = SimplePrefixes.getPlugin().getConfig().getConfigurationSection(prefixId);
        assert config != null;
        this.prefixId = prefixId;
        this.displayName = config.getString("display-name", "Unnamed Prefix");
        this.prefix = config.getString("prefix", "No Prefix");
        this.verifyAlways = config.getBoolean("verify-always", false);
        this.showWhenLocked = config.getBoolean("show-when-locked", true);
        this.requirements = Collections.unmodifiableList(config.getStringList("requirements"));
        this.description = Collections.unmodifiableList(config.getStringList("description"));
    }

    public static void populatePrefixes() {
        prefixes.clear();
        Set<String> prefixIds = SimplePrefixes.getPlugin().getConfig().getKeys(false);
        prefixIds.remove("saving-type");
        prefixIds.remove("default-prefix");
        prefixIds.remove("debug-level");
        for (String key : prefixIds) {
            SimplePrefixes.getPrefixLogger().info("Generating Prefix: " + key);
            prefixes.put(key, new Prefix(key));
        }
    }

    public static Map<String, Prefix> getPrefixes() { return Collections.unmodifiableMap(prefixes); }

    public static Prefix getPrefix(String id) { return prefixes.getOrDefault(id, null); }

}
