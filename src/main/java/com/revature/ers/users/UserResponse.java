package com.revature.ers.users;

import java.io.Serializable;

public class UserResponse implements Serializable {
    private String userId;
    private String givenName;
    private String surname;
    private String email;
    private String username;
    private Role role;
    private boolean is_active;

    public UserResponse (User subject) {
        this.userId = subject.getUserId();
        this.givenName = subject.getGivenName();
        this.surname = subject.getSurname();
        this.email = subject.getEmail();
        this.username = subject.getUsername();
        this.is_active = subject.getActive();
        this.role = subject.getRole();
    }

    public String getUser_Id()
    { return userId; }

    public void setUser_Id(String user_id)
    { this.userId = user_id; }

    public void setGivenName(String givenName)
    { this.givenName = givenName; }

    public String getGivenName()
    { return givenName; }

    public String getSurname()
    { return surname; }

    public void setSurname(String surname)
    { this.surname = surname; }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setIs_active (boolean is_active) {
        this.is_active =is_active;
    }

    public boolean getIs_active() {
        return is_active;
    }

    public String getRole() {
        return role.getName();
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "UserResponse{" +
                "id='" + userId + '\'' +
                ", givenName='" + givenName + '\'' +
                ", surname='" + surname + '\'' +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                "is_active = '" + is_active + "' " +
                ", role='" + role + '\'' +
                '}';
    }

}



