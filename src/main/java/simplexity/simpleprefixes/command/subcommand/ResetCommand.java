package simplexity.simpleprefixes.command.subcommand;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import simplexity.simpleprefixes.command.SubCommand;
import simplexity.simpleprefixes.util.Message;
import simplexity.simpleprefixes.util.Permission;
import simplexity.simpleprefixes.prefix.PrefixUtil;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class ResetCommand extends SubCommand {

    public ResetCommand() {
        super("reset", "Resets your prefix to default", "/sp reset [player]", Permission.RESET);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length == 0) handleSelfReset(sender);
        else if (args.length == 1) handleOtherReset(sender, args);
        else sender.sendMessage(Message.INVALID_TOO_MANY_ARGS.getParsedMessage());
    }

    @Override
    public List<String> getSubcommandArguments(CommandSender sender, String[] args) {
        return new ArrayList<>();
    }

    private void handleSelfReset(CommandSender sender) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(Message.INVALID_NOT_PLAYER.getParsedMessage());
            return;
        }
        if (!player.hasPermission(Permission.RESET.get())) {
            player.sendMessage(Message.INVALID_PERMISSION.getParsedMessage(player));
            return;
        }
        PrefixUtil.getInstance().setPrefix(player, null);
        player.sendMessage(Message.SUCCESS_RESET.getParsedMessage(player));
    }

    private void handleOtherReset(CommandSender sender, String[] args) {
        OfflinePlayer player = Bukkit.getOfflinePlayerIfCached(args[0]);
        if (!sender.hasPermission(Permission.RESET_OTHER.get())) {
            sender.sendMessage(Message.INVALID_PERMISSION.getParsedMessage());
            return;
        }
        if (player == null) {
            sender.sendMessage(Message.INVALID_COULD_NOT_FIND_PLAYER.getParsedMessage());
            return;
        }
        PrefixUtil.getInstance().setPrefix(player, null);
        sender.sendMessage(Message.SUCCESS_RESET_OTHER.getParsedMessage());
    }
}
