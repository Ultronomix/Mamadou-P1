package com.revature.ers.users;

import java.io.Serializable;
import java.util.Objects;

public class UserResponse implements Serializable {

    private int userID;
    private String username;
    private String email;
    private String givenName;
    private String surname;
    private String role;
    private boolean isActive;

    public UserResponse(){
        super();
    }

    public UserResponse(User subject){

        this.userID = subject.getUserID();
        this.username = subject.getUsername();
        this.email = subject.getEmail();
        this.givenName = subject.getGivenName();
        this.surname = subject.getSurname();
        this.role = subject.getRole();
        this.isActive = subject.getIsActive();
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGivenName() {
        return givenName;
    }

    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean getActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        this.isActive = active;
    }


    @Override
    public String toString() {
        return "UserDTO{"+
                "\nuserID= "+userID+
                ", \nusername= " + username +
                ", \nemail= " + email +
                ", \ngivenName= " + givenName +
                ", \nsurname= " + surname +
                ", \nrole= " + role +
                ", \nactive= " + isActive +
                '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 13 * hash + Objects.hashCode(this.userID);
        hash = 13 * hash + Objects.hashCode(this.username);
        hash = 13 * hash + Objects.hashCode(this.email);
        hash = 13 * hash + Objects.hashCode(this.givenName);
        hash = 13 * hash + Objects.hashCode(this.surname);
        hash = 13 * hash + Objects.hashCode(this.role);
        hash = 13 * hash + Objects.hashCode(this.isActive);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final UserResponse other = (UserResponse) obj;
        if (!Objects.equals(this.userID, other.userID)) {
            return false;
        }
        if (!Objects.equals(this.username, other.username)) {
            return false;
        }
        if (!Objects.equals(this.email, other.email)) {
            return false;
        }
        if (!Objects.equals(this.givenName, other.givenName)) {
            return false;
        }
        if (!Objects.equals(this.surname, other.surname)) {
            return false;
        }
        if (!Objects.equals(this.role, other.role)) {
            return false;
        }
        if (!Objects.equals(this.isActive, other.isActive)) {
            return false;
        }
        return true;
    }

}