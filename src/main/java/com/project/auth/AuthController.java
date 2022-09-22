package com.Nick.auth;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.catalina.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.Nick.common.ErrorResponse;
import com.Nick.common.exceptions.AuthenticationException;
import com.Nick.common.exceptions.DataSourceException;
import com.Nick.common.exceptions.InvalidRequestException;
import com.Nick.user.UserResponse;
import com.fasterxml.jackson.databind.JsonMappingException;


@RestController
@RequestMapping("/auth")
public class AuthController {
    
    private static Logger logger = LogManager.getLogger(AuthController.class);
    DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping(produces = "application/json", consumes = "application/json")
    public UserResponse authenticate(@RequestBody Credentials credentials, HttpServletRequest req) {
        
        UserResponse authUser = authService.authenticate(credentials);

        logger.info("Establishing user session for user: {}", authUser.getUsername());
        HttpSession usersSession = req.getSession();
        usersSession.setAttribute("authUser", authUser);

        return authUser;
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void logout(HttpServletRequest req) {
        req.getSession().invalidate();
    }

    @ExceptionHandler({InvalidRequestException.class, JsonMappingException.class})
    public ErrorResponse handleBadRequest(Exception e) {
        logger.warn("A bad request was recieved at {}, details: {}", LocalDateTime.now().format(format), e.getMessage());
        return new ErrorResponse(400, e.getMessage());
    }

    @ExceptionHandler
    public ErrorResponse handleAuthenticationExceptions(AuthenticationException e) {
        logger.warn("A failed authentication occured at {}, details: {}", LocalDateTime.now().format(format), e.getMessage());
        return new ErrorResponse(401, e.getMessage());
    }

    @ExceptionHandler
    public ErrorResponse handleDataSourceExceptions(DataSourceException e) {
        logger.warn("A datasource exception was thrown at {}, details: {}", LocalDateTime.now().format(format), e.getMessage());
        return new ErrorResponse(500, e.getMessage());
    }

    @ExceptionHandler
    public ErrorResponse handleOtherExceptions(Exception e) {
        logger.warn("A failed authentication occured at {}, details: {}", LocalDateTime.now().format(format), e.getMessage());
        return new ErrorResponse(500, e.getMessage());
    }
}