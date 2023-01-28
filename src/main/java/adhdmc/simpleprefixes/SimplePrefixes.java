package adhdmc.simpleprefixes;

import adhdmc.simpleprefixes.command.CommandHandler;
import adhdmc.simpleprefixes.command.subcommand.*;
import adhdmc.simpleprefixes.config.Config;
import adhdmc.simpleprefixes.dependency.PAPIExpansion;
import adhdmc.simpleprefixes.gui.chest.listener.PrefixMenuListener;
import adhdmc.simpleprefixes.util.Prefix;
import adhdmc.simpleprefixes.util.PrefixUtil;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public final class SimplePrefixes extends JavaPlugin {

    private static Plugin plugin;
    private static MiniMessage miniMessage;
    private static Logger logger;

    @Override
    public void onEnable() {
        plugin = this;
        miniMessage = MiniMessage.miniMessage();
        logger = getPlugin().getLogger();
        logger.info("SimplePrefixes has set up plugin, mini-message, and logger.");
        configSetup();
        logger.info("SimplePrefixes has passed the config setup.");
        new PAPIExpansion(this).register();
        logger.info("SimplePrefixes has registered the PAPI Extension.");
        registerCommands();
        logger.info("SimplePrefixes has registered the commands.");
        Bukkit.getPluginManager().registerEvents(new PrefixMenuListener(), this);
        logger.info("SimplePrefixes has registered the event.");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static Plugin getPlugin() { return plugin; }
    public static MiniMessage getMiniMessage() { return miniMessage; }

    public static Logger getPrefixLogger() {
        return logger;
    }


    public static void configSetup() {
        plugin.saveDefaultConfig();
        Config.loadConfig();
        PrefixUtil.getInstance().loadSaveHandler();
        Prefix.populatePrefixes();
    }

    private void registerCommands() {
        this.getCommand("simpleprefix").setExecutor(new CommandHandler());
        CommandHandler.subcommandList.clear();
        CommandHandler.subcommandList.put("set", new SetCommand());
        CommandHandler.subcommandList.put("gui", new GuiCommand());
        CommandHandler.subcommandList.put("info", new InfoCommand());
        CommandHandler.subcommandList.put("reset", new ResetCommand());
        CommandHandler.subcommandList.put("reload", new ReloadCommand());
    }

}
