package com.Nick.auth;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Nick.common.exceptions.AuthenticationException;
import com.Nick.common.exceptions.InvalidRequestException;
import com.Nick.user.UserDAO;
import com.Nick.user.UserResponse;


@Service
public class AuthService {
    
    private static Logger logger = LogManager.getLogger(AuthService.class);
    DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
    
    private final UserDAO userDAO;

    @Autowired
    public AuthService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public UserResponse authenticate(Credentials credentials) {

        
        logger.info("Starting authentication at {}", LocalDateTime.now().format(format));

        if(credentials == null) {
            logger.warn("Invalid credentials provided at {}", LocalDateTime.now().format(format));
            throw new InvalidRequestException("The provided credentials cannot be empty");
        }

        if(credentials.getUsername().length() < 4) {
            logger.warn("Invalid username provided at {}", LocalDateTime.now().format(format));
            throw new InvalidRequestException("The provided username must be at least 4 characters");
        }

        if(credentials.getPassword().length() < 4) {
            logger.warn("Invalid password provided at {}", LocalDateTime.now().format(format));
            throw new InvalidRequestException("The provided password must be at least 4 characters");
        }

        boolean active = userDAO.isActive(credentials.getUsername(), credentials.getPassword());
            
        UserResponse user = userDAO.findUserByUsernameAndPassword(credentials.getUsername(), credentials.getPassword())
                .map(UserResponse :: new).orElseThrow(AuthenticationException::new);
                
        logger.info("Checking if user is active at {}", LocalDateTime.now().format(format));
        if(active == true) {
            return user;
        } else {
            logger.warn("Inactive user tried to log in at {}", LocalDateTime.now().format(format));
            throw new InvalidRequestException("User unable to log in due to inactive");
        }

        
    }
}