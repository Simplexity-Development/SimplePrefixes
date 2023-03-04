package adhdmc.simpleprefixes.prefix;

import adhdmc.simpleprefixes.SimplePrefixes;
import adhdmc.simpleprefixes.config.Config;
import adhdmc.simpleprefixes.util.saving.PlayerPDC;
import adhdmc.simpleprefixes.util.saving.SaveHandler;
import adhdmc.simpleprefixes.util.saving.YMLFile;
import com.github.twitch4j.TwitchClient;
import com.github.twitch4j.helix.domain.User;
import com.github.twitch4j.helix.domain.UserList;
import org.bukkit.OfflinePlayer;

import java.util.Arrays;
import java.util.List;

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

    public User getChannelByName(String name) {
        if (name == null || name.isBlank()) return null;
        TwitchClient client = SimplePrefixes.getTwitch();
        if (client == null) return null;
        UserList result = client.getHelix().getUsers(null, null, List.of(name)).execute();
        if (result.getUsers().size() != 1) return null;
        return result.getUsers().get(0);
    }

}
