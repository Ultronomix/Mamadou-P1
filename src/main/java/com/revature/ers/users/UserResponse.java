package com.revature.ers.users;

import java.io.Serializable;
import java.util.Objects;

// Example of a response DTO
public class UserResponse implements Serializable {

    private String userId;
    private String username;
    private String email;

    private String password;
    private String givenName;
    private String surname;

    private boolean isActive;

    private String roleId;

    public UserResponse(User subject) {
        this.userId = subject.getUserId();
        this.username = subject.getUsername();
        this.email = subject.getEmail();
        this.password = subject.getPassword();
        this.givenName = subject.getGivenName();
        this.surname = subject.getSurname();
        this.isActive = subject.getIsActive();
        this.roleId = subject.getRoleId();

    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String UserId) {
        this.userId = userId;
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

    public String getPassword() {
        return password;
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

    public boolean isActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }
    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserResponse that = (UserResponse) o;
        return Objects.equals(userId, that.userId) && Objects.equals(username, that.username) &&  Objects.equals(email, that.email) && Objects.equals(password, that.password) && Objects.equals(givenName, that.givenName) && Objects.equals(surname, that.surname) && Objects.equals(isActive, that.isActive) &&  Objects.equals(roleId, that.roleId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, username, email, password, givenName, surname, isActive, roleId);
    }

    @Override
    public String toString() {
        return "UserResponse{" +
                "id='" + userId + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", givenName='" + givenName + '\'' +
                ", surname='" + surname + '\'' +
                ", isActive='" + isActive + '\'' +
                ", roleId='" + roleId + '\'' +
                '}';
    }

}