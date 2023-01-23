package adhdmc.simpleprefixes.command.subcommand;

import adhdmc.simpleprefixes.command.SubCommand;
import adhdmc.simpleprefixes.gui.chest.PrefixMenu;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class GuiCommand extends SubCommand {
    public GuiCommand() {
        super("gui", "Opens the GUI menu for prefixes!", "/sp gui");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player player)) {
            // TODO: Configurable message, message enum.
            sender.sendRichMessage("<red>PLACEHOLDER: YOU ARE NOT A PLAYER");
            return;
        }
        player.openInventory(PrefixMenu.getInstance().generatePrefixMenu(player, 1));
    }

    @Override
    public List<String> getSubcommandArguments(CommandSender sender, String[] args) {
        return new ArrayList<>();
    }
}
