package simplexity.simpleprefixes.dependency;

import simplexity.simpleprefixes.SimplePrefixes;
import simplexity.simpleprefixes.prefix.Prefix;
import simplexity.simpleprefixes.prefix.PrefixUtil;
import me.clip.placeholderapi.PlaceholderAPI;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;
import simplexity.simpleprefixes.prefix.RequirementUtil;
import simplexity.simpleprefixes.util.Message;

public class PAPIExpansion extends PlaceholderExpansion {

    private final SimplePrefixes plugin;

    public PAPIExpansion(SimplePrefixes plugin) {
        this.plugin = plugin;
    }

    @Override
    public @NotNull String getIdentifier() {
        return "sp";
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
        if (params.equalsIgnoreCase("prefix")) {
            String prefix = PrefixUtil.getInstance().getPlayerPrefix(player);
            return PlaceholderAPI.setPlaceholders(player, prefix);
        }
        if (params.equalsIgnoreCase("prefix_legacy")) {
            String prefix = PrefixUtil.getInstance().getPlayerPrefix(player);
            String papiParsed = PlaceholderAPI.setPlaceholders(player, prefix);
            String stripped = SimplePrefixes.getStripper().stripTags(papiParsed);
            return LegacyComponentSerializer.legacySection().serialize(SimplePrefixes.getMiniMessage().deserialize(stripped));
        }
        if (params.startsWith("prefix_available_")) {
            String prefix_id = params.substring("prefix_available_".length());
            if (!Prefix.isPrefix(prefix_id)) return null;
            String message = RequirementUtil.getInstance().isEarnedPrefix(player, prefix_id) ? Message.GUI_UNLOCKED.getMessage() : Message.GUI_LOCKED.getMessage();
            return LegacyComponentSerializer.legacySection().serialize(SimplePrefixes.getMiniMessage().deserialize(message));
        }

        return null;
    }

}
