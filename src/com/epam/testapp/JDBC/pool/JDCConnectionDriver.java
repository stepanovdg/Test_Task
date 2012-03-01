package com.epam.testapp.JDBC.pool;

/**
 * Created by IntelliJ IDEA.
 * User: Stepanov Dmitriy
 * Date: 01.12.11
 * Time: 12:44
 *
 */

import java.sql.*;
import java.util.Properties;
import java.util.logging.Logger;


public class JDCConnectionDriver implements Driver {


    private String urlPrefix;
    private static final int MAJOR_VERSION = 1;
    private static final int MINOR_VERSION = 0;
    private JDCConnectionPool pool;

    public void setUrlPrefix(String urlPrefix) {
        this.urlPrefix = urlPrefix;
    }

    public JDCConnectionDriver(String driver, String url,
                               String user, String password)
            throws ClassNotFoundException,
            InstantiationException, IllegalAccessException,
            SQLException {
        DriverManager.registerDriver(this);
        Class.forName(driver).newInstance();
        pool = new JDCConnectionPool(url, user, password);
    }

    public Connection connect(String url, Properties props)
            throws SQLException {
        if (!url.startsWith(urlPrefix)) {
            return null;
        }
        return pool.getConnection();
    }

    public boolean acceptsURL(String url) {
        return url.startsWith(urlPrefix);
    }

    public int getMajorVersion() {
        return MAJOR_VERSION;
    }

    public int getMinorVersion() {
        return MINOR_VERSION;
    }

    public DriverPropertyInfo[] getPropertyInfo(String str, Properties props) {
        return new DriverPropertyInfo[0];
    }

    public boolean jdbcCompliant() {
        return false;
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return null;
    }

}
