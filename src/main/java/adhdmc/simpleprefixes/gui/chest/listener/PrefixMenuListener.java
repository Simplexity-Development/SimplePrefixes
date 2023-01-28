package adhdmc.simpleprefixes.gui.chest.listener;

import adhdmc.simpleprefixes.gui.chest.PrefixMenu;
import adhdmc.simpleprefixes.util.PrefixUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class PrefixMenuListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Inventory inventory = event.getInventory();
        if (!isPrefixMenu(inventory)) return;
        if (!(event.getWhoClicked() instanceof Player player)) return;
        ItemStack item = event.getCurrentItem();
        if (item == null) return;
        event.setCancelled(true);
        evalSelection(event.getCurrentItem(), player);
    }

    @EventHandler
    public void onInventoryDrag(InventoryDragEvent event) {
        Inventory inventory = event.getInventory();
        if (!isPrefixMenu(inventory)) return;
        event.setCancelled(true);
    }

    private boolean isPrefixMenu(Inventory inv) {
        if (inv.getType() != InventoryType.CHEST) { return false; }
        ItemStack item = inv.getItem(4);
        if (item == null) return false;
        return item.getItemMeta().getPersistentDataContainer().has(PrefixMenu.nskPrefixMenu);
    }

    private void evalSelection(ItemStack item, Player p) {
        if (item == null) return;
        Integer newPage = item.getItemMeta().getPersistentDataContainer().get(PrefixMenu.nskPage, PersistentDataType.INTEGER);
        if (newPage != null) {
            Inventory newMenu = PrefixMenu.getInstance().generatePrefixMenu(p, newPage);
            p.openInventory(newMenu);
            return;
        }
        PersistentDataContainer pdc = item.getItemMeta().getPersistentDataContainer();
        String prefixId = pdc.get(PrefixMenu.nskPrefixId, PersistentDataType.STRING);
        if (prefixId == null) return;
        if (pdc.has(PrefixMenu.nskUnlocked)) PrefixUtil.getInstance().setPrefix(p, prefixId);
    }
}
