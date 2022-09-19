package com.revature.ers.users;

import java.util.Objects;

// POJO = Plain Ol' Java Objects
public class User {

    private String userId;
    private String givenName;
    private String surname;
    private String email;
    private String username;
    private String password;
    private boolean isActive;
    private Role role;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String User_id) {
        this.userId = User_id;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public void setActive(boolean active) {
        this.isActive = active;
    }

    public boolean getActive() {
        return isActive;
    }
    public void setRole (Role role) {
        this.role = role;
    }

    public Role getRole() {
        return role;
    }

    public boolean checkRole(String role_check) {
        return role.getName().equals(role_check);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(userId, user.userId)
                && Objects.equals(givenName, user.givenName) && Objects.equals(surname, user.surname)
                && Objects.equals(email, user.email) && Objects.equals(username, user.username)
                && Objects.equals(password, user.password) && Objects.equals(role, user.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, givenName, surname, email, username, password, isActive, role);
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + userId + '\'' +
                ", givenName='" + givenName + '\'' +
                ", surname='" + surname + '\'' +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                '}';
    }


}
