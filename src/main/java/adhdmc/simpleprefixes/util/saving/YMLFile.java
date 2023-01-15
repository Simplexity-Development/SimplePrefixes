package adhdmc.simpleprefixes.util.saving;

import adhdmc.simpleprefixes.SimplePrefixes;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class YMLFile extends SaveHandler {
    private final File dataFile = new File(SimplePrefixes.getPlugin().getDataFolder(), "prefix_data.yml");
    private final FileConfiguration prefixData = new YamlConfiguration();

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
        return prefixData.getString(p.getUniqueId().toString(), null);
    }

    @Override
    public void setPrefixId(OfflinePlayer p, String id) {
        prefixData.set(p.getUniqueId().toString(), id);
        try { prefixData.save(dataFile); }
        catch (IOException e) { e.printStackTrace(); }
    }
}
