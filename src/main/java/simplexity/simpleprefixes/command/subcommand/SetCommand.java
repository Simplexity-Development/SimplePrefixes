package simplexity.simpleprefixes.command.subcommand;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import simplexity.simpleprefixes.command.SubCommand;
import simplexity.simpleprefixes.prefix.Prefix;
import simplexity.simpleprefixes.prefix.PrefixUtil;
import simplexity.simpleprefixes.prefix.RequirementUtil;
import simplexity.simpleprefixes.util.Message;
import simplexity.simpleprefixes.util.Permission;

import java.util.ArrayList;
import java.util.List;

public class SetCommand extends SubCommand {
    public SetCommand() {
        super("set", "Sets the user's prefix to the given ID", "/sp set <id> [player]", Permission.SET);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length == 1) handleSelfSet(sender, args);
        else if (args.length == 2) handleOtherSet(sender, args);
        else sender.sendMessage(Message.INVALID_TOO_MANY_ARGS.getParsedMessage());
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

    private void handleSelfSet(CommandSender sender, String[] args) {
        if (Prefix.isPrefix(args[0])) {
            sender.sendMessage(Message.INVALID_PREFIX_ID.getParsedMessage());
            return;
        }
        if (!(sender instanceof Player player)) {
            sender.sendMessage(Message.INVALID_NOT_PLAYER.getParsedMessage());
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

    private void handleOtherSet(CommandSender sender, String[] args) {
        if (!sender.hasPermission(Permission.SET_OTHER.get())) {
            sender.sendMessage(Message.INVALID_PERMISSION.getParsedMessage());
        }
        OfflinePlayer player = Bukkit.getOfflinePlayerIfCached(args[1]);
        if (player == null) {
            sender.sendMessage(Message.INVALID_COULD_NOT_FIND_PLAYER.getParsedMessage());
            return;
        }
        if (Prefix.isPrefix(args[0])) {
            sender.sendMessage(Message.INVALID_PREFIX_ID.getParsedMessage());
            return;
        }
        PrefixUtil.getInstance().setPrefix(player, args[0]);
        sender.sendMessage(Message.SUCCESS_SET_OTHER.getParsedMessage(player));
    }
}
