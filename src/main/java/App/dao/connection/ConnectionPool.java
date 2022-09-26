package App.dao.connection;

import lombok.extern.log4j.Log4j2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

@Log4j2
public class ConnectionPool {
    private final static int POOL_SIZE = 2;
    private final BlockingDeque<ProxyConnection> freeConnections;
    private final Queue<ProxyConnection> givenAwayConnection;

    ConnectionPool(String driver, String url, String user, String password, DataSource dataSource) {
        freeConnections = new LinkedBlockingDeque<>(POOL_SIZE);
        givenAwayConnection = new ArrayDeque<>();
        try {
            Class.forName(driver);
            log.info("Database driver loaded");
            for (int i = 0; i < POOL_SIZE; i++) {
                Connection connection = DriverManager.getConnection(url, user, password);
                freeConnections.offer(new ProxyConnection(connection, dataSource));
                log.info("Connection created.");
            }
        } catch (SQLException | ClassNotFoundException e) {
            log.error(e);
        }

    }

    public Connection getConnection() {
        ProxyConnection connection = null;
        try {
            connection = freeConnections.take();
            givenAwayConnection.offer(connection);
        } catch (InterruptedException e) {
            log.error(e);
        }
        return connection;
    }

    public void releaseConnection(Connection connection) {
        if (connection instanceof ProxyConnection proxyConnection && givenAwayConnection.remove(connection)) {
            freeConnections.offer(proxyConnection);
        } else {
            log.warn("Returns not proxy connection!");
        }
    }

    public void destroy() {
        for (int i = 0; i < POOL_SIZE; i++) {
            try {
                freeConnections.take().reallyClose();
                log.info("Connection destroyed.");
            } catch (InterruptedException | SQLException e) {
                log.error(e);
            }
        }
        deregisterDrivers();
    }

    private void deregisterDrivers() {
        DriverManager.getDrivers().asIterator().forEachRemaining(driver -> {
            try {
                DriverManager.deregisterDriver(driver);
                log.info("Driver= {} deregistered", driver);
            } catch (SQLException e) {
                log.error(e);
            }
        });
    }

}
