package adhdmc.simpleprefixes.dependency;

import adhdmc.simpleprefixes.SimplePrefixes;
import adhdmc.simpleprefixes.util.PrefixUtil;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class PAPIExpansion extends PlaceholderExpansion {

    private final SimplePrefixes plugin;

    public PAPIExpansion(SimplePrefixes plugin) {
        this.plugin = plugin;
    }

    @Override
    public @NotNull String getIdentifier() {
        return "simple-prefix";
    }

    @Override
    public @NotNull String getAuthor() {
        return "ADHDMC";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0.0";
    }

    @Override
    public boolean persist() { return true; }

    @Override
    public String onRequest(OfflinePlayer player, @NotNull String params) {
        ((Player) player).getPersistentDataContainer();
        if (params.equalsIgnoreCase("prefix")) {
            // TODO: Where to pull prefix data from? Do we save to PDC or YML or..?
            //  https://github.com/PlaceholderAPI/PlaceholderAPI/wiki/PlaceholderExpansion#common-parts
            return "Prefix";
        }

        return null;
    }

}
