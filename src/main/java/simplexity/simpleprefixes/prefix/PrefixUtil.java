package simplexity.simpleprefixes.prefix;

import simplexity.simpleprefixes.config.Config;
import simplexity.simpleprefixes.util.saving.PlayerPDC;
import simplexity.simpleprefixes.util.saving.SaveHandler;
import simplexity.simpleprefixes.util.saving.YMLFile;
import org.bukkit.OfflinePlayer;

public class PrefixUtil {

    private static PrefixUtil instance;

    private SaveHandler saveHandler;

    private PrefixUtil() {}

    public static PrefixUtil getInstance() {
        if (instance == null) instance = new PrefixUtil();
        return instance;
    }

    public void loadSaveHandler() {
        switch (Config.getSavingType()) {
            case PDC -> saveHandler = new PlayerPDC();
            case FILE -> saveHandler = new YMLFile();
            // TODO: SQLite Implementation?
        }
        saveHandler.init();
    }

    public void setPrefix(OfflinePlayer p, String id) { saveHandler.setPrefixId(p, id); }

    public String getPlayerPrefix(OfflinePlayer p) {
        String prefixId = saveHandler.getPrefixId(p);
        Prefix prefix = Prefix.getPrefix(prefixId);
        if (prefix == null) return Config.getDefaultPrefix();
        if (prefix.verifyAlways && !RequirementUtil.getInstance().isEarnedPrefix(p, prefix)) {
            PrefixUtil.getInstance().setPrefix(p, null);
            return Config.getDefaultPrefix();
        }
        return prefix.prefix;
    }

}
