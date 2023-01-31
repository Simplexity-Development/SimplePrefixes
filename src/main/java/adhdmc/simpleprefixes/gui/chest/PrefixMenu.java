package adhdmc.simpleprefixes.gui.chest;

import adhdmc.simpleprefixes.SimplePrefixes;
import adhdmc.simpleprefixes.util.Message;
import adhdmc.simpleprefixes.prefix.Prefix;
import adhdmc.simpleprefixes.prefix.RequirementUtil;
import me.clip.placeholderapi.PlaceholderAPI;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PrefixMenu {

    private static PrefixMenu instance;
    public static final NamespacedKey nskPrefixId = new NamespacedKey(SimplePrefixes.getPlugin(), "prefix_id");
    public static final NamespacedKey nskUnlocked = new NamespacedKey(SimplePrefixes.getPlugin(), "prefix_unlocked");
    public static final NamespacedKey nskPrefixMenu = new NamespacedKey(SimplePrefixes.getPlugin(), "prefix_menu");
    public static final NamespacedKey nskPage = new NamespacedKey(SimplePrefixes.getPlugin(), "prefix_page");
    private final int PER_PAGE = 45;
    private final MiniMessage mini = SimplePrefixes.getMiniMessage();

    private PrefixMenu() {}

    public static PrefixMenu getInstance() {
        if (instance == null) instance = new PrefixMenu();
        return instance;
    }

    public Inventory generatePrefixMenu(Player p, int page) {
        Inventory inv = Bukkit.createInventory(null, 54);
        List<String> prefixes = getPlayerPrefixOptions(p);
        inv.setItem(0, generatePageArrowItem(page, prefixes.size(), false));
        inv.setItem(4, generateHeaderItem());
        inv.setItem(8, generatePageArrowItem(page, prefixes.size(), true));

        int start = (page - 1) * PER_PAGE;
        int end = Math.min((page * PER_PAGE), prefixes.size());
        int itemIndex = 9;

        for (int i = start; i < end; i++) {
            ItemStack item = generatePrefixItem(p, Prefix.getPrefix(prefixes.get(i)));
            inv.setItem(itemIndex++, item);
        }

        return inv;
    }

    private List<String> getPlayerPrefixOptions(Player p) {
        RequirementUtil reqUtil = RequirementUtil.getInstance();
        List<String> prefixes = new ArrayList<>(Prefix.getPrefixes().keySet());
        List<String> prefixesToRemove = new ArrayList<>();
        for (String prefixId : prefixes) {
            Prefix prefix = Prefix.getPrefix(prefixId);
            if (!prefix.showWhenLocked && !reqUtil.isEarnedPrefix(p, prefix)) prefixesToRemove.add(prefixId);
        }
        prefixes.removeAll(prefixesToRemove);
        Collections.sort(prefixes);
        return prefixes;
    }

    // TODO: Everything within this section should be configurable.
    private ItemStack generatePrefixItem(Player player, Prefix prefix) {
        boolean unlocked = RequirementUtil.getInstance().isEarnedPrefix(player, prefix);
        ItemStack item = (unlocked) ? new ItemStack(Material.NAME_TAG) : new ItemStack(Material.BARRIER);
        ItemMeta meta = item.getItemMeta();
        assert prefix.displayName != null;
        assert prefix.prefix != null;
        String papiDisplayName = "<!i><white>" + PlaceholderAPI.setPlaceholders(player, prefix.displayName);
        String papiPrefix = "<!i><white>" + PlaceholderAPI.setPlaceholders(player, prefix.prefix);
        String unlockedLore = "<!i><white>" + (unlocked ? Message.GUI_UNLOCKED.getMessage() : Message.GUI_LOCKED.getMessage());
        meta.displayName(mini.deserialize(papiDisplayName));
        List<Component> lore = new ArrayList<>();
        lore.add(mini.deserialize(papiPrefix));
        lore.add(mini.deserialize(" "));
        lore.add(mini.deserialize(unlockedLore));
        for (String line : prefix.description) {
            String papiLine = "<!i><white>" + PlaceholderAPI.setPlaceholders(player, line);
            lore.add(mini.deserialize(papiLine));
        }
        meta.lore(lore);
        PersistentDataContainer pdc = meta.getPersistentDataContainer();
        pdc.set(nskPrefixId, PersistentDataType.STRING, prefix.prefixId);
        if (unlocked) pdc.set(nskUnlocked, PersistentDataType.BYTE, (byte) 1);
        item.setItemMeta(meta);
        return item;
    }

    private ItemStack generatePageArrowItem(int page, int prefixes, boolean forward) {
        if (!forward && page == 1) return null;
        boolean lastPage = prefixes - (page * PER_PAGE) <= 0;
        if (forward && lastPage) return null;
        ItemStack item = new ItemStack(Material.ARROW);
        ItemMeta meta = item.getItemMeta();
        String displayName = forward ? Message.GUI_NEXT.getMessage() : Message.GUI_PREV.getMessage();
        int toPage = forward ? page+1 : page-1;
        meta.getPersistentDataContainer().set(nskPage, PersistentDataType.INTEGER, toPage);
        meta.displayName(mini.deserialize(displayName));
        item.setItemMeta(meta);
        return item;
    }

    private ItemStack generateHeaderItem() {
        ItemStack item = new ItemStack(Material.ENDER_EYE);
        // TODO: Set name and description through config.
        ItemMeta meta = item.getItemMeta();
        meta.getPersistentDataContainer().set(nskPrefixMenu, PersistentDataType.BYTE, (byte) 1);
        item.setItemMeta(meta);
        return item;
    }

}
