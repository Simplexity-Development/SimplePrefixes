package adhdmc.simpleprefixes.util.saving;

import adhdmc.simpleprefixes.prefix.livestream.Platform;
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
     * @param id ID of the prefix represented as the string path. Null to clear prefix.
     */
    public abstract void setPrefixId(OfflinePlayer p, String id);

    /**
     * Obtains the Channel ID / Livestream ID depending on the platform
     * @param p OfflinePlayer
     * @param platform Supported Platform
     * @return String representing the livestream ID, for example "Peashooter101" for "twitch.tv/Peashooter101". Null if unobtainable / unsupported.
     */
    public abstract String getLivestreamId(OfflinePlayer p, Platform platform);

    /**
     * Saves the Channel ID / Livestream ID to be used based on the platform.
     * @param p OfflinePlayer
     * @param platform Supported Platform
     * @param id String representing the livestream ID, for example "Peashooter101" for "twitch.tv/Peashooter101". Null to clear.
     */
    public abstract void setLivestreamId(OfflinePlayer p, Platform platform, String id);

}
