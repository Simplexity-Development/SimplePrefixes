package simplexity.simpleprefixes.gui.chest;

import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import simplexity.simpleprefixes.SimplePrefixes;
import simplexity.simpleprefixes.config.Config;
import simplexity.simpleprefixes.util.Message;
import simplexity.simpleprefixes.prefix.Prefix;
import simplexity.simpleprefixes.prefix.RequirementUtil;
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

    // TODO: Allow for the prefix menu to be generated using a "slots" argument.
    public Inventory generatePrefixMenu(Player p, int page) {
        Inventory inv;
        if (Config.getPrefixMenuName() == null) inv = Bukkit.createInventory(null, 54);
        else {
            Component title = SimplePrefixes.getMiniMessage().deserialize(Config.getPrefixMenuName());
            inv = Bukkit.createInventory(null, 54, title);
        }
        List<String> prefixes = getPlayerPrefixOptions(p);
        inv.setItem(0, generatePageArrowItem(page, prefixes.size(), false));
        inv.setItem(4, generateHeaderItem(p));
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
        ItemStack item = (unlocked) ? prefix.itemStack : new ItemStack(Material.BARRIER);
        setDisplayName(player, prefix, item);
        if (prefix.layout.isEmpty()) generateDefaultLoreLayout(player, prefix, item, unlocked);
        else generateCustomLoreLayout(player, prefix, item, unlocked);
        setPdcInfo(prefix, item, unlocked);
        return item;
    }

    private void generateDefaultLoreLayout(Player player, Prefix prefix, ItemStack item, boolean unlocked) {
        ItemMeta meta = item.getItemMeta();
        assert prefix.prefix != null;
        String papiPrefix = "<!i><white>" + PlaceholderAPI.setPlaceholders(player, prefix.prefix);
        String unlockedLore = "<!i><white>" + (unlocked ? Message.GUI_UNLOCKED.getMessage() : Message.GUI_LOCKED.getMessage());
        List<Component> lore = new ArrayList<>();
        lore.add(mini.deserialize(papiPrefix));
        lore.add(mini.deserialize(" "));
        lore.add(mini.deserialize(unlockedLore));
        for (String line : prefix.description) {
            String papiLine = "<!i><white>" + PlaceholderAPI.setPlaceholders(player, line);
            lore.add(mini.deserialize(papiLine));
        }
        meta.lore(lore);
        item.setItemMeta(meta);
    }

    private void generateCustomLoreLayout(Player player, Prefix prefix, ItemStack item, boolean unlocked) {
        ItemMeta meta = item.getItemMeta();
        String unlockedLore = "<!i><white>" + (unlocked ? Message.GUI_UNLOCKED.getMessage() : Message.GUI_LOCKED.getMessage());
        List<Component> lore = new ArrayList<>();
        for (String line : prefix.layout) {
            Component parsedLine = mini.deserialize("<!i><white>" + PlaceholderAPI.setPlaceholders(player, line),
                    Placeholder.parsed("prefix", prefix.prefix),
                    Placeholder.parsed("prefix_id", prefix.prefixId),
                    Placeholder.parsed("unlocked", unlockedLore)
            );
            lore.add(parsedLine);
        }
        meta.lore(lore);
        item.setItemMeta(meta);
    }

    private void setDisplayName(Player player, Prefix prefix, ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        assert prefix.displayName != null;
        String papiDisplayName = "<!i><white>" + PlaceholderAPI.setPlaceholders(player, prefix.displayName);
        meta.displayName(mini.deserialize(papiDisplayName));
        item.setItemMeta(meta);
    }

    private void setPdcInfo(Prefix prefix, ItemStack item, boolean unlocked) {
        ItemMeta meta = item.getItemMeta();
        PersistentDataContainer pdc = meta.getPersistentDataContainer();
        pdc.set(nskPrefixId, PersistentDataType.STRING, prefix.prefixId);
        if (unlocked) pdc.set(nskUnlocked, PersistentDataType.BYTE, (byte) 1);
        item.setItemMeta(meta);
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

    private ItemStack generateHeaderItem(Player p) {
        ItemStack item = Config.getHeaderItem();
        ItemMeta meta = item.getItemMeta();
        meta.getPersistentDataContainer().set(nskPrefixMenu, PersistentDataType.BYTE, (byte) 1);
        meta.displayName(mini.deserialize("<!i>" + PlaceholderAPI.setPlaceholders(p, Config.getHeaderName())));
        List<Component> loreLines = new ArrayList<>();
        for (String line : Config.getHeaderLore()) {
            loreLines.add(mini.deserialize("<!i><white>" + PlaceholderAPI.setPlaceholders(p, line)));
        }
        meta.lore(loreLines);
        try {
            int count = Integer.parseInt(PlaceholderAPI.setPlaceholders(p, Config.getHeaderCount()));
            item = item.asQuantity(count);
        } catch (NumberFormatException e) {
            SimplePrefixes.getPrefixLogger().warning("Header input type does not produce a number.");
        }
        item.setItemMeta(meta);
        return item;
    }

}
