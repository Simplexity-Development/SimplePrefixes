package adhdmc.simpleprefixes.command.subcommand;

import adhdmc.simpleprefixes.command.SubCommand;
import adhdmc.simpleprefixes.util.PrefixUtil;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class ResetCommand extends SubCommand {

    public ResetCommand() {
        super("reset", "Resets your prefix to default", "/sp reset");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player player)) {
            // TODO: Configurable message, message enum.
            sender.sendRichMessage("<red>PLACEHOLDER: YOU ARE NOT A PLAYER");
            return;
        }
        PrefixUtil.getInstance().setPrefix(player, null);
        // TODO: Configurable message, message enum.
        sender.sendRichMessage("<green>PLACEHOLDER: RESET PREFIX");
    }

    @Override
    public List<String> getSubcommandArguments(CommandSender sender, String[] args) {
        return new ArrayList<>();
    }
}
