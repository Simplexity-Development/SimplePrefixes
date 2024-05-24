package simplexity.simpleprefixes.config;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import simplexity.simpleprefixes.SimplePrefixes;
import simplexity.simpleprefixes.util.Message;
import org.bukkit.configuration.file.FileConfiguration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Config {

    public enum SAVING_TYPE { PDC, FILE, MYSQL }
    private static SAVING_TYPE savingType = SAVING_TYPE.PDC;
    private static String defaultPrefix = "<white>[<gray>Player</gray>]</white> ";
    private static String prefixMenuName = "<bold>Prefix Menu</bold>";
    private static ItemStack headerItem;
    private static String headerName = "<aqua>Click to Reset Prefix</aqua>";
    private static List<String> headerLore = new ArrayList<>();
    private static String headerCount = "1";

    private static String sqlUser;
    private static String sqlPass;
    private static String sqlDbName;
    private static String sqlIp;

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
        generateBaseHeaderItem();
        headerName = config.getString("header.name", "<aqua>Click to Reset Prefix</aqua>");
        headerLore = config.getStringList("header.lore");
        headerCount = config.getString("header.count", "1");

        sqlUser = config.getString("sql.user");
        sqlPass = config.getString("sql.pass");
        sqlDbName = config.getString("sql.dbName");
        sqlIp = config.getString("sql.ip");
    }

    public static void generateBaseHeaderItem() {
        FileConfiguration config = SimplePrefixes.getPlugin().getConfig();
        Material material = Material.getMaterial(config.getString("header.material", "ENDER_EYE"));
        headerItem = material != null ? new ItemStack(material) : new ItemStack(Material.ENDER_EYE);
        if (config.isInt("header.custom-model-data")) {
            ItemMeta meta = headerItem.getItemMeta();
            meta.setCustomModelData(Integer.valueOf(config.getInt("header.custom-model-data")));
            headerItem.setItemMeta(meta);
        }
    }

    public static SAVING_TYPE getSavingType() { return savingType; }
    public static String getDefaultPrefix() { return defaultPrefix; }
    public static String getPrefixMenuName() { return prefixMenuName; }
    public static ItemStack getHeaderItem() { return headerItem.clone(); }
    public static String getHeaderName() { return headerName; }
    public static List<String> getHeaderLore() { return Collections.unmodifiableList(headerLore); }
    public static String getHeaderCount() { return headerCount; }

    public static String getSqlUser() { return sqlUser; }
    public static String getSqlPass() { return sqlPass; }
    public static String getSqlDbName() { return sqlDbName; }
    public static String getSqlIp() { return sqlIp; }
    public static String getSqlUrl() { return "jdbc:mysql://" + sqlIp + "/" + sqlDbName; }

}
