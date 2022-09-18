package com.revature.ers.auth;

import com.revature.ers.common.exceptions.AuthenticationException;
import com.revature.ers.common.exceptions.InvalidRequestException;
import com.revature.ers.users.UserDAO;
import com.revature.ers.users.UserResponse;

public class AuthService {
    private final UserDAO userDAO;

    public AuthService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    //TODO impliment

    public UserResponse authenticate(Credentials credImport){
        if(credImport == null){
            //will be logged at Servlet level
            throw new InvalidRequestException("Credentials empty");
        }
        if(credImport.getUserID() == null || credImport.getUserID().length() == 0){
            //will be logged at Servlet level
            throw new InvalidRequestException("Username found to be empty/null");
        }
        if(credImport.getPassword() == null || credImport.getPassword().length() == 0){
            //will be logged at Servlet level
            throw new InvalidRequestException("Password found to be empty/null");
        }

        return userDAO.findUserByUsernameAndPassword(credImport.getUserID(), credImport.getPassword())
                .map(UserResponse::new)
                .orElseThrow(AuthenticationException::new);
    }
}
