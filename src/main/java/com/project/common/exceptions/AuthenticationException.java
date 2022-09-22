package com.project.common.exceptions;

public class AuthenticationException extends RuntimeException {

    public AuthenticationException () {
        super("Could not find user");
    }
    
}
