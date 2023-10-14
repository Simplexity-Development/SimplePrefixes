package simplexity.simpleprefixes.command.subcommand;

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
        super("reset", "Resets your prefix to default", "/sp reset", Permission.RESET);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(Message.INVALID_NOT_PLAYER.getParsedMessage(null));
            return;
        }
        if (!player.hasPermission(Permission.RESET.get())) {
            player.sendMessage(Message.INVALID_PERMISSION.getParsedMessage(player));
            return;
        }
        PrefixUtil.getInstance().setPrefix(player, null);
        player.sendMessage(Message.SUCCESS_RESET.getParsedMessage(player));
    }

    @Override
    public List<String> getSubcommandArguments(CommandSender sender, String[] args) {
        return new ArrayList<>();
    }
}
