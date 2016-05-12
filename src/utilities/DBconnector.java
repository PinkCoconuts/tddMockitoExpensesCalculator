/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Logger;

/**
 *
 * @author mady
 */
public class DBconnector {

    private String databaseHost;
    private String databaseUsername;
    private String databasePassword;
    private String jdbcDriverName = "oracle.jdbc.driver.OracleDriver";

    public DBconnector(String databaseHost, String databaseUsername, String databasePassword, String jdbcDriverName) {
        this.databaseHost = databaseHost;
        this.databaseUsername = databaseUsername;
        this.databasePassword = databasePassword;
        if (jdbcDriverName != null) {
            this.jdbcDriverName = jdbcDriverName;
        }
    }

    public Boolean testJDBCdriver(Logger logger) {
        try {
            Class.forName(jdbcDriverName);
        } catch (ClassNotFoundException e) {
            if (logger != null) {
                logger.severe("SQL Exception while trying to test the JDBC Driver " + e);
            } else {
                System.out.println("SQL Exception while trying to test the JDBC Driver " + e);
            }
            return false;
        }
        return true;
    }

    public Connection getConnection(Logger logger) {
        Connection connection = null;
        if (testJDBCdriver(logger)) {
            try {
                connection = DriverManager.getConnection(databaseHost, databaseUsername, databasePassword);
            } catch (SQLException e) {
                if (logger != null) {
                    logger.severe("SQL Exception while trying to connect the connection to db " + e);
                } else {
                    System.out.println("SQL Exception while trying to connect the connection to db " + e);
                }
                return null;
            }
            if (logger != null) {
                //logger.info( "Connection with database established successfully!" );
            } else {
                System.out.println("Connection with database established successfully!");
            }

            return connection;
        }
        return null;
    }

    public boolean closeConnection(Connection connection, Logger logger) {
        if (connection != null) {

            try {
                connection.close();
            } catch (SQLException e) {
                if (logger != null) {
                    logger.severe("SQL Exception while trying to close the connection to db " + e);
                } else {
                    System.out.println("SQL Exception while trying to close the connection to db " + e);
                }
                return false;
            }
            if (logger != null) {
                //logger.info( "Connection with database closed successfully!\n" );
            } else {
                System.out.println("Connection with database closed successfully!");
            }
            return true;
        }

        if (logger != null) {
            //logger.info( "Connection not initialized, cannot close!" );
        } else {
            System.out.println("Connection not initialized, cannot close!");
        }
        return false;
    }
}
