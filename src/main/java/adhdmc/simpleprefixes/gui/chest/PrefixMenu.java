package adhdmc.simpleprefixes.gui.chest;

import adhdmc.simpleprefixes.util.Prefix;
import adhdmc.simpleprefixes.util.PrefixUtil;
import adhdmc.simpleprefixes.util.RequirementUtil;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PrefixMenu {

    private static PrefixMenu instance;

    private PrefixMenu() {}

    public static PrefixMenu getInstance() {
        if (instance == null) instance = new PrefixMenu();
        return instance;
    }

    public Inventory generatePrefixMenu(Player p, int page) {
        Inventory inv = Bukkit.createInventory(null, 56);
        return null;
    }

    public List<String> getPlayerPrefixOptions(Player p) {
        RequirementUtil reqUtil = RequirementUtil.getInstance();
        List<String> prefixes = new ArrayList<>(Prefix.getPrefixes().keySet());
        List<String> prefixesToRemove = new ArrayList<>();
        for (String prefixId : prefixes) {
            Prefix prefix = Prefix.getPrefix(prefixId);
            if (!prefix.showWhenLocked && reqUtil.isEarnedPrefix(p, prefix)) prefixesToRemove.add(prefixId);
        }
        prefixes.removeAll(prefixesToRemove);
        Collections.sort(prefixes);
        return prefixes;
    }

}
