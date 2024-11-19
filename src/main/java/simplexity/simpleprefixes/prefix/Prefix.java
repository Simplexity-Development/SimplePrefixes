package simplexity.simpleprefixes.prefix;

import simplexity.simpleprefixes.SimplePrefixes;
import simplexity.simpleprefixes.config.PrefixConfig;
import simplexity.simpleprefixes.util.Message;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class Prefix {

    private static final HashMap<String, Prefix> prefixes = new HashMap<>();

    public final String prefixId;
    public final String displayName;
    public final List<String> description;
    public final List<String> layout;
    public final String prefix;
    public final boolean verifyAlways;
    public final boolean showWhenLocked;
    public final List<String> requirements;
    public final ItemStack itemStack;

    private Prefix(String prefixId) {
        ConfigurationSection config = PrefixConfig.getInstance().getPrefixConfig().getConfigurationSection(prefixId);
        assert config != null;
        this.prefixId = prefixId;
        this.displayName = config.getString("display-name", "Unnamed Prefix");
        this.prefix = config.getString("prefix", "No Prefix");
        this.verifyAlways = config.getBoolean("verify-always", false);
        this.showWhenLocked = config.getBoolean("show-when-locked", true);
        this.requirements = Collections.unmodifiableList(config.getStringList("requirements"));
        this.description = Collections.unmodifiableList(config.getStringList("description"));
        this.layout = Collections.unmodifiableList(config.getStringList("layout"));

        ConfigurationSection configItem = config.getConfigurationSection("item");
        if (configItem == null) {
            this.itemStack = new ItemStack(Material.NAME_TAG);
            return;
        }
        ItemStack itemStack;
        Material material = Material.getMaterial(configItem.getString("material", "NAME_TAG"));
        int count = configItem.getInt("count", 1);
        itemStack = material == null ? new ItemStack(Material.NAME_TAG, count) : new ItemStack(material, count);
        if (configItem.isInt("custom-model-data")) {
            ItemMeta meta = itemStack.getItemMeta();
            meta.setCustomModelData(configItem.getInt("custom-model-data"));
            itemStack.setItemMeta(meta);
        }
        this.itemStack = itemStack;
    }

    public static void populatePrefixes() {
        prefixes.clear();
        Set<String> prefixIds = PrefixConfig.getInstance().getPrefixConfig().getKeys(false);
        for (String key : prefixIds) {
            SimplePrefixes.getPrefixLogger().info(Message.LOGGER_PREFIX.getMessage() + key);
            prefixes.put(key, new Prefix(key));
        }
    }

    public static Map<String, Prefix> getPrefixes() { return Collections.unmodifiableMap(prefixes); }

    public static Prefix getPrefix(String id) { return prefixes.getOrDefault(id, null); }

    public static boolean isPrefix(String id) { return prefixes.containsKey(id); }

}
