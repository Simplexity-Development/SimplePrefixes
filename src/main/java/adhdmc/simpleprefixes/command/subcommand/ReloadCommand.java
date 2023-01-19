package adhdmc.simpleprefixes.command.subcommand;

import adhdmc.simpleprefixes.SimplePrefixes;
import adhdmc.simpleprefixes.command.SubCommand;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public class ReloadCommand extends SubCommand {
    public ReloadCommand() {
        super("reload", "Reloads the configuration.", "/sp reload");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!sender.hasPermission("simpleprefixes.reload")) {
            // TODO: NO PERMISSION ERROR
            return;
        }
        SimplePrefixes.configSetup();
        sender.sendRichMessage("<green>PLACEHOLDER: RELOADED");
    }

    @Override
    public List<String> getSubcommandArguments(CommandSender sender, String[] args) {
        return new ArrayList<>();
    }
}
