package com.Nick.common.datasource;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// Implement ConnectionFactory and singleton
public class ConnectionFactory {

    private static Logger logger = LogManager.getLogger(ConnectionFactory.class);
    DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

    private static ConnectionFactory connFactory;
    private Properties dbProps = new Properties();
    

    public ConnectionFactory() {

        try {
            dbProps.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("application.properties"));
        }
        catch (IOException e) {
            logger.fatal("There was a problem reading from the properties file at {}", LocalDateTime.now().format(format));
            throw new RuntimeException("Could not read from .properties file.", e);
        }
    }

    public static ConnectionFactory getInstance() {
        if (connFactory == null) {
            connFactory = new ConnectionFactory();
        }
        return connFactory;
    }
    
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(dbProps.getProperty("db_url"), dbProps.getProperty("db_username"), dbProps.getProperty("db_password"));
    }
}

/*  Creates a connection to the sql database and uses the
 * singleton design pattern to ensure that one connection is made.
 */