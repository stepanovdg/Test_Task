package com.epam.testapp.JDBC.pool;

/**
 * Created by IntelliJ IDEA.
 * User: Stepanov Dmitriy
 * Date: 01.12.11
 * Time: 12:46
 *
 */

import java.sql.*;
import java.util.*;

class ConnectionReaper extends Thread {

    private JDCConnectionPool pool;
    private final static long DELAY =300000;

    ConnectionReaper(JDCConnectionPool pool) {
        this.pool=pool;
    }

    public void run() {
        while(true) {
           try {
              sleep(DELAY);
           } catch( InterruptedException ignored) { }
           pool.reapConnections();
        }
    }
}

public class JDCConnectionPool {

    private static final String ERROR_MESSAGE_POOL = "All" +
            " connections available for this resource are in use";
    private static final String ERROR_MESSAGE_CONNECTION = "Exception in connection to database. Please try later.";
    private Vector<JDCConnection> connections;
   private String url, user, password;
   final private static long TIME_OUT =10000;
    final private static int POOL_SIZE =10;

   public JDCConnectionPool(String url, String user, String password) {
      this.url = url;
      this.user = user;
      this.password = password;
      connections = new Vector<>(POOL_SIZE);
      ConnectionReaper reaper = new ConnectionReaper(this);
      reaper.start();
   }

   public synchronized void reapConnections() {

      long stale = System.currentTimeMillis() - TIME_OUT;
      Enumeration<JDCConnection> connlist = connections.elements();

      while((connlist != null) && (connlist.hasMoreElements())) {
          JDCConnection conn = connlist.nextElement();

          if((conn.inUse()) && (stale >conn.getLastUse()) &&
                                            (!conn.validate())) {
 	      removeConnection(conn);
         }
      }
   }

   public synchronized void closeConnections() {
      Enumeration<JDCConnection> connlist = connections.elements();
      while((connlist != null) && (connlist.hasMoreElements())) {
          JDCConnection conn = connlist.nextElement();
          removeConnection(conn);
      }
   }

   private synchronized void removeConnection(JDCConnection conn) {
       connections.removeElement(conn);
   }


   public synchronized Connection getConnection() throws  SQLException {

       JDCConnection c;
       for(int i = 0; i < connections.size(); i++) {
           c = connections.elementAt(i);
           if (c.lease()) {
              return c;
           }else {
               if(i== POOL_SIZE -1){
                   throw new SQLException(ERROR_MESSAGE_POOL);
               }
           }
       }
       Connection conn;
       try {
           conn = DriverManager.getConnection(url, user, password);
       } catch (SQLException e) {
           throw new SQLException(ERROR_MESSAGE_CONNECTION);
       }
       c = new JDCConnection(conn, this);
       c.lease();
       connections.addElement(c);
       return c;
  }

   public synchronized void returnConnection(JDCConnection conn) {
      conn.expireLease();
   }
}
