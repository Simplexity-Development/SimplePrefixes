package adhdmc.simpleprefixes.util.saving;

import adhdmc.simpleprefixes.SimplePrefixes;
import adhdmc.simpleprefixes.prefix.LiveNowPrefix;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class YMLFile extends SaveHandler {
    private final File dataFile = new File(SimplePrefixes.getPlugin().getDataFolder(), "prefix_data.yml");
    private final FileConfiguration prefixData = new YamlConfiguration();
    private final String CURRENT_PREFIX = "current-prefix";

    @Override
    public void init() {
        if (!dataFile.exists()) {
            dataFile.getParentFile().mkdirs();
            try { prefixData.save(dataFile); }
            catch (IOException e) { e.printStackTrace(); }
        }
        try { prefixData.load(dataFile); }
        catch (IOException | InvalidConfigurationException e) { e.printStackTrace(); }
    }

    @Override
    public String getPrefixId(OfflinePlayer p) {
        ConfigurationSection playerData = prefixData.getConfigurationSection(p.getUniqueId().toString());
        if (playerData == null) { return null; }
        return playerData.getString(CURRENT_PREFIX, null);
    }

    @Override
    public void setPrefixId(OfflinePlayer p, String id) {
        String uuid = p.getUniqueId().toString();
        ConfigurationSection playerData = prefixData.getConfigurationSection(uuid);
        if (playerData == null) playerData = prefixData.createSection(uuid);
        playerData.set(CURRENT_PREFIX, id);
        saveData();
    }

    @Override
    public String getLivestreamId(OfflinePlayer p, LiveNowPrefix platform) {
        String uuid = p.getUniqueId().toString();
        ConfigurationSection playerData = prefixData.getConfigurationSection(uuid);
        if (playerData == null) return null;
        return playerData.getString(platform.toString(), null);
    }

    @Override
    public void setLivestreamId(OfflinePlayer p, LiveNowPrefix platform, String id) {
        String uuid = p.getUniqueId().toString();
        ConfigurationSection playerData = prefixData.getConfigurationSection(uuid);
        if (playerData == null) playerData = prefixData.createSection(uuid);
        playerData.set(platform.toString(), id);
        saveData();
    }

    private void saveData() {
        try { prefixData.save(dataFile); }
        catch (IOException e) { e.printStackTrace(); }
    }
}
