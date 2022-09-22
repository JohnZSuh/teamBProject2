package com.project.user;

import java.io.Serializable;
import java.util.Objects;

//* Response DTO
public class UserResponse implements Serializable{

    private String user_id;
    private String given_name;
    private String surname;
    private String email;
    private String username;
    //! private String password;
    private boolean is_active;
    private String role;

    public UserResponse (User subject) {
        this.user_id = subject.getUser_id();
        this.username = subject.getUsername();
        this.email = subject.getEmail();
        this.given_name = subject.getGiven_name();
        this.surname = subject.getSurname();
        this.is_active = subject.getIs_active();
        this.role = subject.getRole();
    }

    public void setUser_id (String user_id) {
        this.user_id = user_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUsername (String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setEmail (String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setGiven_name (String given_name) {
        this.given_name = given_name;
    }

    public String getGiven_name() {
        return given_name;
    }

    public void setSurname (String surname) {
        this.surname = surname;
    }

    public String getSurname() {
        return surname;
    }

    public void setIs_active (boolean is_active) {
        this.is_active =is_active;
    }

    public boolean getIs_active() {
        return is_active;
    }

    public void setRole (String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    @Override
    public int hashCode() {
        return Objects.hash(user_id, username, email, given_name, surname, is_active, role);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        UserResponse userResponse = (UserResponse) obj;
        return Objects.equals(user_id, userResponse.user_id) && Objects.equals(username, userResponse.username)
            && Objects.equals(email, userResponse.email) && Objects.equals(given_name, userResponse.given_name)
            && Objects.equals(surname, userResponse.surname) && Objects.equals(is_active, userResponse.is_active)
            && Objects.equals(role, userResponse.role);
    }

    @Override
    public String toString() {
        return  "UserResponse {" +
                "user_id = '" + user_id +  "' " +
                "username = '" + username + "' " +
                "email = '" + email + "' " +
                "given_name = '" + given_name + "' " +
                "surname = '" + surname + "' " +
                "is_active = '" + is_active + "' " +
                "role = '" + role + "' " +
                "}";
    }
}