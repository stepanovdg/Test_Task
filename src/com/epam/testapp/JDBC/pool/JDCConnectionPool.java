package com.epam.testapp.jdbc.pool;

/**
 * Created by IntelliJ IDEA.
 * User: Stepanov Dmitriy
 * Date: 01.12.11
 * Time: 12:46
 *
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicReferenceArray;

class ConnectionReaper extends Thread {

    private JDCConnectionPool pool;
    private final static long DELAY = 300000;

    ConnectionReaper(JDCConnectionPool pool) {
        this.pool = pool;
    }

    public void run() {
        while (true) {
            try {
                sleep(DELAY);
            } catch (InterruptedException ignored) {
            }
            pool.reapConnections();
        }
    }
}

public class JDCConnectionPool {

    private static final String ERROR_MESSAGE_POOL = "All" +
            " connections available for this resource are in use";
    private static final String ERROR_MESSAGE_CONNECTION = "Exception in connection to database. Please try later.";
    private AtomicReferenceArray<JDCConnection> connections;
    private String url, user, password;
    final private static long TIME_OUT = 10000;
    final private static int POOL_SIZE = 10;

    public JDCConnectionPool(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
        connections = new AtomicReferenceArray<>(POOL_SIZE);
        ConnectionReaper reaper = new ConnectionReaper(this);
        reaper.start();
    }

    public void reapConnections() {

        long stale = System.currentTimeMillis() - TIME_OUT;
        int i = 0;
        while ((connections.length() != 0) && (connections.get(i) != null)) {
            AtomicReference<JDCConnection> conn = new AtomicReference<>(connections.get(i));
            if ((conn.get().inUse()) && (stale > conn.get().getLastUse()) &&
                    (!conn.get().validate())) {
                removeConnection(i);
            } else {
                i++;
            }
        }
    }

    public void closeConnections() {
        int i = 0;
        while ((connections.length() != 0) && (connections.get(i) != null)) {
            removeConnection(i);
            i++;
        }
    }

    private void removeConnection(int i) {
        connections.getAndSet(i, null);
    }


    public Connection getConnection() throws SQLException {
        int space = -1;
        for (int i = 0; i < connections.length(); i++) {
            if (connections.get(i) == null) {
                space = i;
            } else {
                if (connections.get(i).lease()) {
                    return connections.get(i);
                } else {
                    if ((i == POOL_SIZE - 1) && (space<0)) {
                        throw new SQLException(ERROR_MESSAGE_POOL);
                    }
                }
            }
        }
        Connection conn;
        try {
            conn = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            throw new SQLException(ERROR_MESSAGE_CONNECTION);
        }
        JDCConnection c = new JDCConnection(conn, this);
        c.lease();
        connections.set(space, c);
        return connections.get(space);
    }

    public void returnConnection(JDCConnection conn) {
        conn.expireLease();
    }
}
