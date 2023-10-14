package simplexity.simpleprefixes.command;

import simplexity.simpleprefixes.util.Message;
import simplexity.simpleprefixes.util.Permission;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class CommandHandler implements CommandExecutor, TabExecutor {

    public static HashMap<String, SubCommand> subcommandList = new HashMap<>();

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 0) {
            // TODO: Help
            return true;
        }
        String subcommand = args[0].toLowerCase();
        if (subcommandList.containsKey(subcommand)) {
            subcommandList.get(subcommand).execute(sender, Arrays.copyOfRange(args, 1, args.length));
        } else {
            sender.sendMessage(Message.INVALID_COMMAND.getParsedMessage(null));
        }
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 0) return new ArrayList<>();
        if (args.length == 1) {
            List<String> list = new ArrayList<>();
            for (String cmd : subcommandList.keySet()) {
                if (sender.hasPermission(Permission.valueOf(cmd.toUpperCase()).get()) && cmd.contains(args[0])) list.add(cmd);
            }
            return list;
        }
        String subcommand = args[0].toLowerCase();
        if (subcommandList.containsKey(subcommand) && sender.hasPermission(Permission.valueOf(subcommand.toUpperCase()).get())) {
            return subcommandList.get(subcommand).getSubcommandArguments(sender, args);
        }
        return new ArrayList<>();
    }
}
