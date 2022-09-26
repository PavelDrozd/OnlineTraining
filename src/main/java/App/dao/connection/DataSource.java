package App.dao.connection;

import App.ConfiguraionManager;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.io.Closeable;
import java.sql.Connection;

@Log4j2
@Component
public class DataSource implements Closeable {
    private ConnectionPool connectionPool;
    private final String url;
    private final String password;
    private final String user;
    private final String driver;
    private final String profile;

    private DataSource(ConfiguraionManager configuraionManager) {
        profile = configuraionManager.getProperty("profile");
        url = configuraionManager.getProperty("db." + profile + ".url");
        user = configuraionManager.getProperty("db." + profile + ".user");
        password = configuraionManager.getProperty("db." + profile + ".password");
        driver = configuraionManager.getProperty("db." + profile + ".driver");
    }


    public Connection getConnection() {
        if (connectionPool == null) {
            connectionPool = new ConnectionPool(driver, url, user, password, this);
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
