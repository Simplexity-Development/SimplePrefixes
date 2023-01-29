package adhdmc.simpleprefixes.config;

import adhdmc.simpleprefixes.SimplePrefixes;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class PrefixConfig {

    private static PrefixConfig instance;

    private final String fileName = "prefixes.yml";
    private final File dataFile = new File(SimplePrefixes.getPlugin().getDataFolder(), fileName);
    private final FileConfiguration prefixConfig = new YamlConfiguration();

    private PrefixConfig() {
        if (!dataFile.exists()) SimplePrefixes.getPlugin().saveResource(fileName, false);
        reloadPrefixConfig();
    }

    public static PrefixConfig getInstance() {
        if (instance == null) instance = new PrefixConfig();
        return instance;
    }

    public FileConfiguration getPrefixConfig() { return prefixConfig; }

    public void reloadPrefixConfig() {
        try { prefixConfig.load(dataFile); }
        catch (IOException | InvalidConfigurationException e) { e.printStackTrace(); }
    }

}
