package simplexity.simpleprefixes.util.saving;

import org.bukkit.OfflinePlayer;
import simplexity.simpleprefixes.SimplePrefixes;
import simplexity.simpleprefixes.config.Config;

import java.sql.*;
import java.util.logging.Logger;

public class MySQL extends SaveHandler {

    private Connection connection;
    private String dbName;
    Logger logger = SimplePrefixes.getPrefixLogger();
    boolean enabled = true;

    private final String CREATE_TABLE = """
            CREATE TABLE IF NOT EXISTS player_prefixes (
                id VARCHAR(36) PRIMARY KEY,
                prefix_id VARCHAR(256) NOT NULL
            );""";
    private final String INSERT_ROW = "REPLACE INTO player_prefixes (id, prefix_id) VALUES (?, ?);";
    private final String REMOVE_ROW = "DELETE FROM player_prefixes WHERE id = ?;";
    private final String SELECT_ROW = "SELECT * FROM player_prefixes WHERE id = ?;";

    @Override
    public void init() {
        dbName = Config.getSqlDbName();

        try {
            connection = DriverManager.getConnection(Config.getSqlUrl(), Config.getSqlUser(), Config.getSqlPass());
            logger.info("Established connection to the database.");

            try (Statement statement = connection.createStatement()) {
                statement.execute(CREATE_TABLE);
            }

        } catch (SQLException e) {
            if (e.getSQLState().equals("42000")) {
                logger.severe("Could not find database " + dbName + ", please create this database or fix the name to use MySQL.");
            }
            else {
                e.printStackTrace();
            }
            enabled = false;
        }
    }

    @Override
    public String getPrefixId(OfflinePlayer p) {
        String prefixId = null;
        if (!isEnabled()) return null;
        try (PreparedStatement statement = connection.prepareStatement(SELECT_ROW)) {
            statement.setString(1, p.getUniqueId().toString());
            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    prefixId = result.getString("prefix_id");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return prefixId;
    }

    @Override
    public void setPrefixId(OfflinePlayer p, String id) {
        if (id == null) {
            try (PreparedStatement statement = connection.prepareStatement(REMOVE_ROW)) {
                statement.setString(1, p.getUniqueId().toString());
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return;
        }
        try (PreparedStatement statement = connection.prepareStatement(INSERT_ROW)) {
            statement.setString(1, p.getUniqueId().toString());
            statement.setString(2, id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void close() {
        if (connection == null) return;
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private boolean isEnabled() {
        if (!enabled) {
            logger.severe("This plugin has disabled MySQL due to invalid configuration, please check configuration and reload.");
        }
        return enabled;
    }

}
