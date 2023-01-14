package adhdmc.simpleprefixes.command;

import net.kyori.adventure.text.minimessage.MiniMessage;
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
            // TODO: Configurable message, message enum.
            sender.sendRichMessage("<red>PLACEHOLDER: NO COMMAND");
        }
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 0) return new ArrayList<>();
        if (args.length == 1) return new ArrayList<>(subcommandList.keySet());
        String subcommand = args[0].toLowerCase();
        if (subcommandList.containsKey(subcommand)) return subcommandList.get(subcommand).getSubcommandArguments(sender, args);
        return new ArrayList<>();
    }
}
