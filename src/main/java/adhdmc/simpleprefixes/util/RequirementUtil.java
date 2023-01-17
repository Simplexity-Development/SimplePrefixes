package adhdmc.simpleprefixes.util;

import adhdmc.simpleprefixes.SimplePrefixes;
import org.bukkit.NamespacedKey;
import org.bukkit.OfflinePlayer;
import org.bukkit.Statistic;
import org.bukkit.advancement.Advancement;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

public class RequirementUtil {

    public enum RequirementType { PERMISSION, STATISTIC, ADVANCEMENT, COMPARE_STR, COMPARE_INT, COMPARE_STR_NO_CAPS }

    private static RequirementUtil instance;

    private RequirementUtil() {}

    public static RequirementUtil getInstance() {
        if (instance == null) instance = new RequirementUtil();
        return instance;
    }

    /**
     * Determines if the player has earned the prefix in prefixId
     * Check is done every time the placeholder is needed.
     * @param p The player.
     * @param prefixId The prefix ID as provided in the configuration.
     * @return True if all criteria are met, false otherwise.
     */
    public boolean isEarnedPrefix(OfflinePlayer p, String prefixId) {
        ConfigurationSection prefixConfig = SimplePrefixes.getPlugin().getConfig().getConfigurationSection(prefixId);
        if (prefixConfig == null) return false;
        for (String requirement : prefixConfig.getStringList("requirements")) {
            String[] reqArray = requirement.split(" ", 2);
            RequirementType type;
            try { type = RequirementType.valueOf(reqArray[0].toUpperCase()); }
            catch (IllegalArgumentException e) { e.printStackTrace(); /* TODO: Invalid Requirement Message, SKIP */ continue; }
            // If the requirement of the prefix cannot be determined, failure will be false.
            // Failure will only be true if the requirement definitely is not met.
            boolean failure = false;
            switch (type) {
                case PERMISSION -> failure = !checkPermission(p, reqArray[1]);
                case STATISTIC -> failure = !checkStatistic(p, reqArray[1]);
                case ADVANCEMENT -> failure = !checkAdvancement(p, reqArray[1]);
                case COMPARE_STR -> failure = !checkStr(p, reqArray[1]);
                case COMPARE_INT -> failure = !checkInt(p, reqArray[1]);
                case COMPARE_STR_NO_CAPS -> failure = !checkStrNoCaps(p, reqArray[1]);
            }
            if (failure) { return false; }
        }
        return true;
    }

    /**
     * Requirement Checker
     * @param p OfflinePlayer Object
     * @param requirement String representing the requirement
     * @return false if requirement is not met, true if requirement is met or cannot be determined.
     */
    public boolean checkPermission(OfflinePlayer p, String requirement) {
        Player player = p.getPlayer();
        // Player is not online, cannot be determined.
        if (player == null) return true;
        String[] reqArray = requirement.split(" ");
        // Do they have the permission?
        boolean hasPerm = player.hasPermission(reqArray[0]);
        // Did they designate the permission should be false?
        return (reqArray.length >= 2 && reqArray[1].equalsIgnoreCase("false")) != hasPerm;
    }

    /**
     * Requirement Checker
     * @param p OfflinePlayer Object
     * @param requirement String representing the requirement
     * @return false if requirement is not met, true if requirement is met or cannot be determined.
     */
    public boolean checkStatistic(OfflinePlayer p, String requirement) {
        String[] reqArray = requirement.split(" ");
        // Invalid Statistic Requirement Formatting, cannot be determined.
        if (reqArray.length < 3) { return true; }
        // If stat or compareVal are invalid, cannot be determined.
        Statistic stat;
        int compareVal;
        try { stat = Statistic.valueOf(reqArray[0].toUpperCase()); }
        catch (IllegalArgumentException e) { e.printStackTrace(); /* TODO: Invalid Statistic Message */ return true; }
        try { compareVal = Integer.parseInt(reqArray[2]); }
        catch (NumberFormatException e) { e.printStackTrace(); /* TODO: Invalid Integer Message */ return true; }
        int value = p.getStatistic(stat);
        switch (reqArray[1]) {
            case ">" -> { return value > compareVal; }
            case "<" -> { return value < compareVal; }
            case "<=" -> { return value <= compareVal; }
            case ">=" -> { return value >= compareVal; }
            case "==" -> { return value == compareVal; }
            case "!=" -> { return value != compareVal; }
            // If comparison operator is invalid, cannot be determined.
            default -> { /* TODO: Invalid Comparison Message */ return true; }
        }
    }

    /**
     * Requirement Checker
     * @param p OfflinePlayer Object
     * @param requirement String representing the requirement
     * @return false if requirement is not met, true if requirement is met or cannot be determined.
     */
    public boolean checkAdvancement(OfflinePlayer p, String requirement) {
        Player player = p.getPlayer();
        // Player is not online, cannot be determined.
        if (player == null) return true;
        String[] reqArray = requirement.split(" ");
        String[] advancementStr = reqArray[0].split(":");
        NamespacedKey advancementKey = new NamespacedKey(advancementStr[0], advancementStr[1]);
        Advancement advancement = player.getServer().getAdvancement(advancementKey);
        // Advancement does not exist, cannot be determined.
        if (advancement == null) return true;
        boolean complete = player.getAdvancementProgress(advancement).isDone();
        return (reqArray.length >= 2 && reqArray[1].equalsIgnoreCase("false")) != complete;
    }

    /**
     * Requirement Checker
     * @param p OfflinePlayer Object
     * @param requirement String representing the requirement
     * @return false if requirement is not met, true if requirement is met or cannot be determined.
     */
    public boolean checkStr(OfflinePlayer p, String requirement) {
        return true;
    }

    /**
     * Requirement Checker
     * @param p OfflinePlayer Object
     * @param requirement String representing the requirement
     * @return false if requirement is not met, true if requirement is met or cannot be determined.
     */
    public boolean checkInt(OfflinePlayer p, String requirement) {
        return true;
    }

    /**
     * Requirement Checker
     * @param p OfflinePlayer Object
     * @param requirement String representing the requirement
     * @return false if requirement is not met, true if requirement is met or cannot be determined.
     */
    public boolean checkStrNoCaps(OfflinePlayer p, String requirement) {
        return true;
    }

}
