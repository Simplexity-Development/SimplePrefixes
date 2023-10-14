package simplexity.simpleprefixes.gui.chest.listener;

import simplexity.simpleprefixes.gui.chest.PrefixMenu;
import simplexity.simpleprefixes.util.Message;
import simplexity.simpleprefixes.prefix.PrefixUtil;
import org.bukkit.Sound;
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
        if (item.getItemMeta().getPersistentDataContainer().has(PrefixMenu.nskPrefixMenu)) {
            PrefixUtil.getInstance().setPrefix(p, null);
            p.sendMessage(Message.SUCCESS_RESET.getParsedMessage(p));
            p.playSound(p.getLocation(), Sound.UI_CARTOGRAPHY_TABLE_TAKE_RESULT, 1f, 1f);
            return;
        }
        Integer newPage = item.getItemMeta().getPersistentDataContainer().get(PrefixMenu.nskPage, PersistentDataType.INTEGER);
        if (newPage != null) {
            Inventory newMenu = PrefixMenu.getInstance().generatePrefixMenu(p, newPage);
            p.openInventory(newMenu);
            p.playSound(p.getLocation(), Sound.UI_BUTTON_CLICK, 1f, 1f);
            return;
        }
        PersistentDataContainer pdc = item.getItemMeta().getPersistentDataContainer();
        String prefixId = pdc.get(PrefixMenu.nskPrefixId, PersistentDataType.STRING);
        if (prefixId == null) return;
        if (pdc.has(PrefixMenu.nskUnlocked)) {
            PrefixUtil.getInstance().setPrefix(p, prefixId);
            p.sendMessage(Message.SUCCESS_SET.getParsedMessage(p));
            p.playSound(p.getLocation(), Sound.UI_CARTOGRAPHY_TABLE_TAKE_RESULT, 1f, 1f);
        }
        else {
            p.sendMessage(Message.INVALID_REQUIREMENTS.getParsedMessage(p));
            p.playSound(p.getLocation(), Sound.UI_BUTTON_CLICK, 1f, 0.5f);
        }
    }
}
