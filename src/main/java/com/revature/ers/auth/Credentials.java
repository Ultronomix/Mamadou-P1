package com.revature.ers.auth;

public class Credentials {
    private String userID;
    private String password;

    //make Jackson happy with default constructor
    public Credentials(){
        super();
    }

    public Credentials(String userIdA, String passwordA){
        this.userID = userIdA;
        this.password = passwordA;
    }//end real constructor

    //getters and setters
    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}//end CredentialsDTO class
