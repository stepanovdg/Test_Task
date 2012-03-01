package com.epam.testapp.JDBC.pool;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by IntelliJ IDEA.
 * User: Stepanov Dmitriy
 * Date: 04.12.11
 * Time: 10:48
 */
public final class ConnectionManager {
    private static final String ERROR_MESSAGE = "Exception in preparing for connection" +
            " to database. Please come later.";
    private String databaseConnection;

    public ConnectionManager(String databaseDriverName, String databaseUrl,
                             String databaseUser, String databasePassword,
                             String databaseConnection, String databaseUrlPrefix)throws SQLException {
        this.databaseConnection = databaseConnection;
        try {
            JDCConnectionDriver driver= new JDCConnectionDriver(databaseDriverName,databaseUrl,
                    databaseUser, databasePassword);
            driver.setUrlPrefix(databaseUrlPrefix);
        } catch (ClassNotFoundException | SQLException | InstantiationException | IllegalAccessException e) {
            throw new SQLException(e + ERROR_MESSAGE);
        }
    }


    public JDCConnection getConnection() throws SQLException {
        return (JDCConnection) DriverManager.getConnection(databaseConnection);
    }

    public void closeConnection(Connection cn) throws SQLException {
        cn.close();
    }
}