package App.dao.connection;

import App.ConfiguraionManager;
import lombok.extern.log4j.Log4j2;

import java.io.Closeable;
import java.sql.Connection;

@Log4j2
public class DataSource implements Closeable {
    public final static DataSource INSTANCE = new DataSource();
    private ConnectionPool connectionPool;
    private final String url;
    private final String password;
    private final String user;
    private final String driver;
    private final String profile;

    private DataSource() {
        profile = ConfiguraionManager.INSTANCE.getProperty("profile");
        url = ConfiguraionManager.INSTANCE.getProperty("db." + profile + ".url");
        user = ConfiguraionManager.INSTANCE.getProperty("db." + profile + ".user");
        password = ConfiguraionManager.INSTANCE.getProperty("db." + profile + ".password");
        driver = ConfiguraionManager.INSTANCE.getProperty("db." + profile + ".driver");
    }


    public Connection getConnection() {
        if (connectionPool == null) {
            connectionPool = new ConnectionPool(driver, url, user, password);
            log.info("Successful connection to the sql server. Connection pool initialized.");
        }
        return connectionPool.getConnection();
    }

    ConnectionPool getConnectionPool() {
        return connectionPool;
    }


    @Override
    public void close() {
        if (connectionPool != null) {
            connectionPool.destroy();
            connectionPool = null;
            log.info("Connection poll destroyed.");

        }
    }
}
