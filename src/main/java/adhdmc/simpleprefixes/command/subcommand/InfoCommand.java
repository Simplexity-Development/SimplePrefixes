package adhdmc.simpleprefixes.command.subcommand;

import adhdmc.simpleprefixes.SimplePrefixes;
import adhdmc.simpleprefixes.command.SubCommand;
import adhdmc.simpleprefixes.util.Prefix;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public class InfoCommand extends SubCommand {

    public InfoCommand() {
        super("info", "Displays information about the given prefix.", "/sp info <id>");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Prefix prefix = Prefix.getPrefix(args[0]);
        if (prefix == null) {
            // TODO: Configurable message, message enum.
            sender.sendRichMessage("<red>PLACEHOLDER: NOT VALID ID");
            return;
        }
        sender.sendMessage(buildInfoString(prefix));
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

    private Component buildInfoString(Prefix prefix) {
        // TODO: Configurable message, message enum.
        String infoString = """
        <green>--==[</green><prefix_name><reset><green>]==--</green>
        <prefix_id>
        <prefix_description>
        <prefix_requirements>
        """;
        String prefix_name = "" + prefix.prefix;
        String prefix_id = "<yellow><u>Prefix ID:</u></yellow> " + prefix.prefixId;
        String prefix_description = "<yellow><u>Description:</u></yellow> " + String.join(" ", prefix.description);
        StringBuilder prefix_requirements = new StringBuilder("<yellow><u>Requirements:</u></yellow>");
        for (String requirement : prefix.requirements) {
            prefix_requirements.append("\n  ").append(requirement);
        }
        return SimplePrefixes.getMiniMessage().deserialize(infoString,
                Placeholder.parsed("prefix_id", prefix_id),
                Placeholder.parsed("prefix_name", prefix_name),
                Placeholder.parsed("prefix_description", prefix_description),
                Placeholder.parsed("prefix_requirements", prefix_requirements.toString())
        );
    }
}
