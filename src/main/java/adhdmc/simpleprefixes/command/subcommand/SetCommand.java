package adhdmc.simpleprefixes.command.subcommand;

import adhdmc.simpleprefixes.command.SubCommand;
import adhdmc.simpleprefixes.util.Prefix;
import adhdmc.simpleprefixes.util.PrefixUtil;
import adhdmc.simpleprefixes.util.RequirementUtil;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SetCommand extends SubCommand {
    public SetCommand() {
        super("set", "Sets the user's prefix to the given ID", "/sp set <id>");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Prefix prefix = Prefix.getPrefix(args[0]);
        if (prefix == null) {
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
        sender.sendRichMessage("<green>PLACEHOLDER: SET PREFIX TO PREFIX " + prefix.displayName + " (ID: " + prefix.prefixId + ") → " + prefix.prefix);
    }

    @Override
    public List<String> getSubcommandArguments(CommandSender sender, String[] args) {
        if (args.length != 2) return new ArrayList<>();
        List<String> returnList = new ArrayList<>();
        for (String s : Prefix.getPrefixes().keySet()) {
            if (s.toLowerCase().contains(args[1].toLowerCase())) returnList.add(s);
        }
        return returnList;
    }
}
