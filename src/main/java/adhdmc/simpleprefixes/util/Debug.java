package adhdmc.simpleprefixes.util;

import adhdmc.simpleprefixes.SimplePrefixes;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.logging.Logger;

public class Debug {
    private static final Logger logger = SimplePrefixes.getPrefixLogger();

    public static void debugLvl1(String message){
        FileConfiguration config = SimplePrefixes.getPlugin().getConfig();
        if (config.getInt("debug-level") == 1 ||config.getInt("debug-level") == 4) {
            logger.info(message);
        }
    }

    public static void debugLvl2(String message){
        FileConfiguration config = SimplePrefixes.getPlugin().getConfig();
        if (config.getInt("debug-level") == 2 ||config.getInt("debug-level") == 4) {
            logger.info(message);
        }
    }

    public static void debugLvl3(String message){
        FileConfiguration config = SimplePrefixes.getPlugin().getConfig();
        if (config.getInt("debug-level") == 3 ||config.getInt("debug-level") == 4) {
            logger.info(message);
        }
    }
}
