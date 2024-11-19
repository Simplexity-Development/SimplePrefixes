package simplexity.simpleprefixes.util;

import org.bukkit.entity.Player;
import simplexity.simpleprefixes.SimplePrefixes;
import me.clip.placeholderapi.PlaceholderAPI;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.OfflinePlayer;

public enum Message {

    // Plugin Name
    PLUGIN("<white>[<aqua>SimplePrefixes</aqua>]<white> »"),

    // Error Messages
    INVALID_PREFIX_ID("<plugin> <red>Invalid prefix ID, try using the GUI."),
    INVALID_COMMAND("<plugin> <red>No such command."),
    INVALID_PERMISSION("<plugin> <red>You do not have permission to use this."),
    INVALID_REQUIREMENTS("<plugin> <red>You do not meet the requirements for this prefix."),
    INVALID_NOT_PLAYER("<plugin> <red>You are not a player."),
    INVALID_TOO_MANY_ARGS("<plugin> <red>Too many arguments."),
    INVALID_COULD_NOT_FIND_PLAYER("<plugin> <red>Could not find player."),

    // Success Messages
    SUCCESS_RELOAD("<plugin> <green>The plugin configurations have been reloaded."),
    SUCCESS_RESET("<plugin> <green>Your prefix has been reset to the default."),
    SUCCESS_RESET_OTHER("<plugin> <green>You successfully reset their prefix to the default."),
    SUCCESS_SET("<plugin> <green>Your prefix has been successfully set to %sp_prefix%<green>."),
    SUCCESS_SET_OTHER("<plugin> <green>You successfully changed their prefix to %sp_prefix%<green>."),

    // Logger Messages
    LOGGER_PREFIX("Prefix loaded: "),
    LOGGER_INVALID_CONFIG_SAVING_TYPE("The saving-type configuration option is not a valid option, defaulting to last set value."),
    LOGGER_INVALID_LOCALE_KEY("The following locale key is not valid: "),

    // GUI Messages
    GUI_UNLOCKED("<aqua><bold>✔ UNLOCKED"),
    GUI_LOCKED("<red><bold>✗ LOCKED"),
    GUI_NEXT("<bold>Next Page >>"),
    GUI_PREV("<bold><< Prev Page")
    ;

    private String message;

    Message(String message) {
        this.message = message;
    }

    public void setMessage(String message) { this.message = message; }

    /**
     * Intended for Logger Messages.
     * Can be used for all Message enums, but getParsedMessage() is preferred.
     * @return Message as a String.
     */
    public String getMessage() { return this.message; }

    /**
     * Returns the Component form of the messages.
     * This should be used for messages overall.
     * Supports PlaceholderAPI.
     * @param p Player if applicable, null otherwise.
     * @return Component form of the message that can be used.
     */
    public Component getParsedMessage(OfflinePlayer p) {
        return SimplePrefixes.getMiniMessage().deserialize(
                PlaceholderAPI.setPlaceholders(p, this.message),
                Placeholder.parsed("plugin", Message.PLUGIN.message)
        );
    }

    /**
     * Returns the Component form of the messages.
     * This should be used for messages overall.
     * Supports PlaceholderAPI and invokes getParsedMessage(null).
     * @return Component form of the message that can be used.
     */
    public Component getParsedMessage() {
        return getParsedMessage(null);
    }

}
