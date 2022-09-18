package com.revature.ers.users;

import java.util.ArrayList;
import java.util.List;

import com.revature.ers.common.exceptions.InvalidRequestException;
import com.revature.ers.common.exceptions.ResourcePersistenceException;

public class UserService {
    private final UserDAO userDAO;

    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public List<UserResponse> getAllUsers(){
        List<UserResponse> result = new ArrayList<>();
        List<User> users = userDAO.allUsers();
        for (User user : users) {
            result.add(new UserResponse(user));
        }
        return result;
    }

    public UserResponse getUserByUsername(String usernameImport) throws InvalidRequestException{
        //filter invalid usernames
        if(usernameImport == null || usernameImport.length() == 0){
            throw new InvalidRequestException(
                    "ERROR: submitted Username is null"+
                            " or empty and can not be looked up");
        }

        try{
            User target = userDAO.getUsername(usernameImport).orElse(null);
            if (target == null){
                throw new IllegalArgumentException("ERROR");
            }

            UserResponse result = new UserResponse(target);
            return result;
        }catch(IllegalArgumentException e){
            throw new InvalidRequestException("ERROR");
        }
    }

    public UserResponse register(NewUserRequest newUser) throws InvalidRequestException, ResourcePersistenceException{
        //logic for checking first/last name, email, username, and password
        //are present and meet formatting requirements
        if(newUser == null){
            throw new InvalidRequestException("ERROR");
        }
        if(newUser.getGivenName() == null || newUser.getGivenName().equals("")){
            throw new InvalidRequestException("ERROR");
        }
        if(newUser.getSurname() == null || newUser.getSurname().equals("")){
            throw new InvalidRequestException("ERROR");
        }
        if(newUser.getEmail() == null || newUser.getEmail().equals("")){
            throw new InvalidRequestException("ERROR");
        }
        if(newUser.getUsername() == null || newUser.getUsername().equals("")){
            throw new InvalidRequestException("ERROR");
        }
        if(newUser.getPassword() == null || newUser.getPassword().equals("")){
            throw new InvalidRequestException("ERROR");
        }

        //logic for checking Username and Email are unique before passing to database
        if(userDAO.isUsernameTaken(newUser.getUsername())){
            throw new ResourcePersistenceException("ERROR");
        }
        if(userDAO.isEmailTaken(newUser.getEmail())){
            throw new ResourcePersistenceException("ERROR");
        }

        User target = userDAO.newUser(newUser).orElse(null);
        UserResponse result = new UserResponse(target);
        return result;
    }

    //TODO add updateUser method


    public UserResponse deactivate(String usernameImport){
        User target = userDAO.deactivateUser(usernameImport).orElse(null);
        UserResponse result = new UserResponse(target);

        return result;
    }
}
