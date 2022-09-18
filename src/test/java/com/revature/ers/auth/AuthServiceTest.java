package com.revature.ers.auth;

import com.revature.ers.common.exceptions.AuthenticationException;
import com.revature.ers.common.exceptions.InvalidRequestException;
import com.revature.ers.users.UserDAO;
import com.revature.ers.users.UserResponse;

public class AuthServiceTest {

    private UserDAO userDAO;

    public void AuthService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public UserResponse authenticate(Credentials credentials) {

        if (credentials == null) {
            throw new InvalidRequestException("The provided credentials object was found to be null.");
        }

        if (credentials.getUserID().length() < 4) {
            throw new InvalidRequestException("The provided username was not the correct length (must be at least 4 characters).");
        }

        if (credentials.getPassword().length() < 8) {
            throw new InvalidRequestException("The provided password was not the correct length (must be at least 8 characters).");
        }

        return userDAO.findUserByUsernameAndPassword(credentials.getUserID(), credentials.getPassword())
                .map(UserResponse::new)
                .orElseThrow(AuthenticationException::new);

    }
}

