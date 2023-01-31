package adhdmc.simpleprefixes.command.subcommand;

import adhdmc.simpleprefixes.command.SubCommand;
import adhdmc.simpleprefixes.prefix.Prefix;
import adhdmc.simpleprefixes.prefix.PrefixUtil;
import adhdmc.simpleprefixes.prefix.RequirementUtil;
import adhdmc.simpleprefixes.util.*;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class SetCommand extends SubCommand {
    public SetCommand() {
        super("set", "Sets the user's prefix to the given ID", "/sp set <id>", Permission.SET);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Prefix prefix = Prefix.getPrefix(args[0]);
        if (prefix == null) {
            sender.sendMessage(Message.INVALID_PREFIX_ID.getParsedMessage(null));
            return;
        }
        if (!(sender instanceof Player player)) {
            sender.sendMessage(Message.INVALID_NOT_PLAYER.getParsedMessage(null));
            return;
        }
        if (!player.hasPermission(Permission.SET.get())) {
            player.sendMessage(Message.INVALID_PERMISSION.getParsedMessage(player));
            return;
        }
        if (!RequirementUtil.getInstance().isEarnedPrefix(player, args[0])) {
            player.sendMessage(Message.INVALID_REQUIREMENTS.getParsedMessage(player));
            return;
        }
        PrefixUtil.getInstance().setPrefix(player, args[0]);
        player.sendMessage(Message.SUCCESS_SET.getParsedMessage(player));
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
