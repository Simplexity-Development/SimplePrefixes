package adhdmc.simpleprefixes.gui.chest.listener;

import adhdmc.simpleprefixes.SimplePrefixes;
import adhdmc.simpleprefixes.gui.chest.PrefixMenu;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class PrefixMenuListener implements Listener {

    @EventHandler
    public void onInventoryInteractEvent(InventoryInteractEvent event) {
        event.setCancelled(true);
        SimplePrefixes.getPlugin().getServer().broadcast(MiniMessage.miniMessage().deserialize("<red>SERVER READ INPUT"));
        event.getWhoClicked().sendRichMessage("<green>YOU CLICKED IT!");
        if (!isPrefixMenu(event.getInventory())) return;
        event.getWhoClicked().sendRichMessage("<blue>CANCELLED!");
        event.setCancelled(true);
    }

    private boolean isPrefixMenu(Inventory inv) {
        if (inv.getType() != InventoryType.CHEST) { return false; }
        ItemStack item = inv.getItem(4);
        if (item == null) return false;
        return item.getItemMeta().getPersistentDataContainer().has(PrefixMenu.nskPrefixMenu);
    }

}
