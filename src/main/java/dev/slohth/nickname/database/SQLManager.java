package dev.slohth.nickname.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import dev.slohth.nickname.Nickname;
import dev.slohth.nickname.utils.framework.Config;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SQLManager {

    private final Nickname core;

    private Connection connection;
    private String url, host, port;

    private HikariDataSource source;
    private HikariConfig config = new HikariConfig();

    private PreparedStatement ps = null;
    private ResultSet rs = null;

    public SQLManager(Nickname core) {
        this.core = core;

        config.setUsername(Config.DETAILS.getString("user"));
        config.setPassword(Config.DETAILS.getString("password"));
        config.addDataSourceProperty( "cachePrepStmts" , "true" );
        config.addDataSourceProperty( "prepStmtCacheSize" , "250" );
        config.addDataSourceProperty( "prepStmtCacheSqlLimit" , "2048" );
        config.setMaxLifetime(50000);

        url = Config.DETAILS.getString("database");
        host = Config.DETAILS.getString("host");
        port = Config.DETAILS.getString("port");

        config.setJdbcUrl("jdbc:mysql://" + host + ":" + port + "/" + url);

        try {
            establishConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        execStatement("CREATE TABLE IF NOT EXISTS `active-nicknames` (`uuid` VARCHAR(36), `name` VARCHAR(16), `skin` TEXT, `rank` VARCHAR(32))");
    }

    private void establishConnection() throws SQLException {
        source = new HikariDataSource(config);
        connection = source.getConnection();
    }

    public void execUpdate(String update) {
        try {
            ps = connection.prepareStatement(update);
            ps.executeUpdate();
        } catch (SQLException e) {
            core.getLogger().severe(e.getMessage());
        } finally {
            try {
                if (rs != null && !rs.isClosed()) rs = null;
                if (ps != null && !ps.isClosed()) ps = null;
            } catch (SQLException e) {
                core.getLogger().severe(e.getMessage());
            }
        }
    }

    public ResultSet execQuery(String query) {
        try {
            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();
            return rs;
        } catch (SQLException e) {
            core.getLogger().severe(e.getMessage());
        } finally {
            try {
                if (rs != null && !rs.isClosed()) rs = null;
                if (ps != null && !ps.isClosed()) ps = null;
            } catch (SQLException e) {
                core.getLogger().severe(e.getMessage());
            }
        }
        return null;
    }

    public void execStatement(String statement) {
        try {
            ps = connection.prepareStatement(statement);
            ps.execute();
        } catch (SQLException e) {
            core.getLogger().severe(e.getMessage());
        } finally {
            try {
                if (rs != null && !rs.isClosed()) rs = null;
                if (ps != null && !ps.isClosed()) ps = null;
            } catch (SQLException e) {
                core.getLogger().severe(e.getMessage());
            }
        }
    }

}