package adhdmc.simpleprefixes.command.subcommand;

import adhdmc.simpleprefixes.SimplePrefixes;
import adhdmc.simpleprefixes.command.SubCommand;
import adhdmc.simpleprefixes.util.PrefixUtil;
import adhdmc.simpleprefixes.util.RequirementUtil;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class SetCommand extends SubCommand {
    public SetCommand() {
        super("set", "Sets the user's prefix to the given ID", "/sp set <id>");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (PrefixUtil.getInstance().getPrefixConfig(args[0]) == null) {
            // TODO: Configurable message, message enum.
            sender.sendRichMessage("<red>PLACEHOLDER: NOT VALID ID");
            return;
        }
        if (!(sender instanceof Player player)) {
            // TODO: Configurable message, message enum.
            sender.sendRichMessage("<red>PLACEHOLDER: YOU ARE NOT A PLAYER");
            return;
        }
        if (!RequirementUtil.getInstance().isEarnedPrefix(player, args[0])) {
            // TODO: Configurable message, message enum.
            sender.sendRichMessage("<red>PLACEHOLDER: REQUIREMENTS NOT MET");
            return;
        }
        PrefixUtil.getInstance().setPrefix(player, args[0]);
        // TODO: Configurable message, message enum.
        String displayName = PrefixUtil.getInstance().getPrefixConfig(args[0]).getString("display-name", "ERROR");
        String prefix = PrefixUtil.getInstance().getPrefixConfig(args[0]).getString("prefix", "ERROR");
        sender.sendRichMessage("<green>PLACEHOLDER: SET PREFIX TO PREFIX " + displayName + " (ID: " + args[0] + ") → " + prefix);
    }

    @Override
    public List<String> getSubcommandArguments(CommandSender sender, String[] args) {
        if (args.length != 2) return new ArrayList<>();
        Set<String> prefixIds = SimplePrefixes.getPlugin().getConfig().getKeys(false);
        prefixIds.remove("saving-type");
        prefixIds.remove("default-prefix");
        return new ArrayList<>(prefixIds);
    }
}
