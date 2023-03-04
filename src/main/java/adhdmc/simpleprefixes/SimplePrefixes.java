package adhdmc.simpleprefixes;

import adhdmc.simpleprefixes.command.CommandHandler;
import adhdmc.simpleprefixes.command.subcommand.*;
import adhdmc.simpleprefixes.config.Config;
import adhdmc.simpleprefixes.config.Locale;
import adhdmc.simpleprefixes.config.PrefixConfig;
import adhdmc.simpleprefixes.dependency.PAPIExpansion;
import adhdmc.simpleprefixes.gui.chest.listener.PrefixMenuListener;
import adhdmc.simpleprefixes.prefix.Prefix;
import adhdmc.simpleprefixes.prefix.PrefixUtil;
import com.github.twitch4j.TwitchClient;
import com.github.twitch4j.TwitchClientBuilder;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import net.kyori.adventure.text.minimessage.tag.standard.StandardTags;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public final class SimplePrefixes extends JavaPlugin {

    private static Plugin plugin;
    private static MiniMessage miniMessage;
    private static MiniMessage miniMessageStripper;
    private static Logger logger;
    private static TwitchClient twitch;

    @Override
    public void onEnable() {
        plugin = this;
        miniMessage = MiniMessage.miniMessage();
        miniMessageStripper = MiniMessage.builder().tags(
                TagResolver.builder()
                        .resolver(StandardTags.clickEvent())
                        .resolver(StandardTags.hoverEvent())
                        .build()).build();
        logger = getPlugin().getLogger();
        configSetup();
        new PAPIExpansion(this).register();
        registerCommands();
        Bukkit.getPluginManager().registerEvents(new PrefixMenuListener(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static Plugin getPlugin() { return plugin; }
    public static MiniMessage getMiniMessage() { return miniMessage; }
    public static MiniMessage getStripper() { return miniMessageStripper; }
    public static Logger getPrefixLogger() { return logger; }
    public static TwitchClient getTwitch() { return twitch; }


    public static void configSetup() {
        plugin.saveDefaultConfig();
        Locale.getInstance().reloadLocale();
        Config.loadConfig();
        PrefixConfig.getInstance().reloadPrefixConfig();
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

    public static void reloadTwitchClient() {
        String twitchClientId = Config.getTwitchClientId();
        String twitchClientSecret = Config.getTwitchClientSecret();
        if (twitchClientId == null || twitchClientSecret == null) return;
        twitch = TwitchClientBuilder.builder()
                .withClientId(Config.getTwitchClientId())
                .withClientSecret(Config.getTwitchClientSecret())
                .withEnableHelix(true)
                .build();
    }

}
