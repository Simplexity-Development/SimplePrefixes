package adhdmc.simpleprefixes.util.saving;

import org.bukkit.OfflinePlayer;

public abstract class SaveHandler {

    /**
     * Initializes the Saving Method utilized.
     * Generally will be used by Databases.
     */
    public abstract void init();

    /**
     * Returns the prefixId of the player.
     * Is nullable depending on the saving type.
     * @param p OfflinePlayer
     * @return prefixId of the player, empty String if none, null for special cases.
     */
    public abstract String getPrefixId(OfflinePlayer p);

    /**
     * Sets the prefixId of the player.
     * @param p OfflinePlayer
     * @param id ID of the prefix represented as the string path.
     */
    public abstract void setPrefixId(OfflinePlayer p, String id);

}
