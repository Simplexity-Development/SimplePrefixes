package adhdmc.simpleprefixes;

import adhdmc.simpleprefixes.command.CommandHandler;
import adhdmc.simpleprefixes.command.subcommand.Set;
import adhdmc.simpleprefixes.config.Config;
import adhdmc.simpleprefixes.dependency.PAPIExpansion;
import adhdmc.simpleprefixes.util.PrefixUtil;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public final class SimplePrefixes extends JavaPlugin {

    private static Plugin plugin;

    @Override
    public void onEnable() {
        plugin = this;
        configSetup();
        new PAPIExpansion(this).register();
        registerCommands();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static Plugin getPlugin() { return plugin; }

    public void configSetup() {
        this.saveDefaultConfig();
        Config.loadConfig();
        PrefixUtil.getInstance().loadSaveHandler();
    }

    private void registerCommands() {
        this.getCommand("simpleprefix").setExecutor(new CommandHandler());
        CommandHandler.subcommandList.clear();
        CommandHandler.subcommandList.put("set", new Set());
    }

}
