package simplexity.simpleprefixes.command.subcommand;

import simplexity.simpleprefixes.SimplePrefixes;
import simplexity.simpleprefixes.command.SubCommand;
import simplexity.simpleprefixes.util.Message;
import simplexity.simpleprefixes.util.Permission;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public class ReloadCommand extends SubCommand {
    public ReloadCommand() {
        super("reload", "Reloads the configuration.", "/sp reload", Permission.RELOAD);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!sender.hasPermission(Permission.RELOAD.get())) {
            sender.sendMessage(Message.INVALID_PERMISSION.getParsedMessage(null));
            return;
        }
        SimplePrefixes.configSetup();
        sender.sendMessage(Message.SUCCESS_RELOAD.getParsedMessage(null));
    }

    @Override
    public List<String> getSubcommandArguments(CommandSender sender, String[] args) {
        return new ArrayList<>();
    }
}
