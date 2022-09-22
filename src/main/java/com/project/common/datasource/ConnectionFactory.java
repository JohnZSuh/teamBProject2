package com.project.common.datasource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.project.common.exceptions.DataSourceException;

// Implement ConnectionFactory and singleton
@Component
public class ConnectionFactory {

    private static Logger logger = LogManager.getLogger(ConnectionFactory.class);
    DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
    
    @Value("${db-url}")
    private String dbUrl;

    @Value("${db-username}")
    private String dbUsername;
    
    @Value("${db-password}")
    private String dbPassword;

    public ConnectionFactory() {

        try {
            Class.forName("org.postgresql.Driver");
        }
        catch (ClassNotFoundException e) {
            String message = "Failed to load PostgreSQL JDbc driver";
            logger.fatal(message + " at {}", LocalDateTime.now().format(format));
            throw new DataSourceException(message, e);
        }
    }
    
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
    }
}

/*  Creates a connection to the sql database and uses the
 * singleton design pattern to ensure that one connection is made.
 */