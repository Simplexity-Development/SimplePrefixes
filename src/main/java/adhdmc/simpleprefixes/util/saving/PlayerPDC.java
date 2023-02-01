package adhdmc.simpleprefixes.util.saving;

import adhdmc.simpleprefixes.SimplePrefixes;
import adhdmc.simpleprefixes.prefix.livestream.Platform;
import org.bukkit.NamespacedKey;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class PlayerPDC extends SaveHandler {

    private final NamespacedKey pdcKey = new NamespacedKey(SimplePrefixes.getPlugin(), "prefixId");

    /**
     * Empty method, initialization of PDC is not necessary.
     */
    @Override
    public void init() {

    }

    /**
     * Returns the prefix ID from the Player's Persistent Data Container
     * @param p OfflinePlayer
     * @return prefixId of the player's prefix, empty string if none, null if player is offline.
     */
    @Override
    public String getPrefixId(OfflinePlayer p) {
        if (!(p instanceof Player player)) return null;
        PersistentDataContainer pdc = player.getPersistentDataContainer();
        if (!pdc.has(pdcKey)) return "";
        return pdc.get(pdcKey, PersistentDataType.STRING);
    }

    @Override
    public void setPrefixId(OfflinePlayer p, String id) {
        if (!(p instanceof Player player)) return;
        PersistentDataContainer pdc = player.getPersistentDataContainer();
        if (id == null || id.isEmpty()) { pdc.remove(pdcKey); }
        else pdc.set(pdcKey, PersistentDataType.STRING, id);
    }

    @Override
    public String getLivestreamId(OfflinePlayer p, Platform platform) {
        // TODO: Logger warning, unsupported operation.
        return null;
    }

    @Override
    public void setLivestreamId(OfflinePlayer p, Platform platform, String id) {
        // TODO: Logger warning, unsupported operation.
    }

}
