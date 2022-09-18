package com.revature.ers.common.exceptions;

public class AuthenticationException extends RuntimeException {

    public AuthenticationException() {
        //logged at Servlet level
        super("No user account with the provided credentials!");
    }
}