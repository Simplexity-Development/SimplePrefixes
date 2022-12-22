package adhdmc.simpleprefixes;

import adhdmc.simpleprefixes.dependency.PAPIExpansion;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public final class SimplePrefixes extends JavaPlugin {

    private static Plugin plugin;

    @Override
    public void onEnable() {
        plugin = this;
        new PAPIExpansion(this).register();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static Plugin getPlugin() { return plugin; }

}
