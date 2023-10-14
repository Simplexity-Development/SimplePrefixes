package simplexity.simpleprefixes.command.subcommand;

import simplexity.simpleprefixes.command.SubCommand;
import simplexity.simpleprefixes.gui.chest.PrefixMenu;
import simplexity.simpleprefixes.util.Message;
import simplexity.simpleprefixes.util.Permission;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class GuiCommand extends SubCommand {
    public GuiCommand() {
        super("gui", "Opens the GUI menu for prefixes!", "/sp gui", Permission.GUI);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(Message.INVALID_NOT_PLAYER.getParsedMessage(null));
            return;
        }
        if (!player.hasPermission(Permission.GUI.get())) {
            player.sendMessage(Message.INVALID_PERMISSION.getParsedMessage(player));
            return;
        }
        player.openInventory(PrefixMenu.getInstance().generatePrefixMenu(player, 1));
    }

    @Override
    public List<String> getSubcommandArguments(CommandSender sender, String[] args) {
        return new ArrayList<>();
    }
}
