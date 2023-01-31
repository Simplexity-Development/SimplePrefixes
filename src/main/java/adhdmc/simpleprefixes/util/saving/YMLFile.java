package adhdmc.simpleprefixes.util.saving;

import adhdmc.simpleprefixes.SimplePrefixes;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

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
        try { prefixData.save(dataFile); }
        catch (IOException e) { e.printStackTrace(); }
    }
}
