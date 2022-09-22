package com.project.user;

import java.util.Objects;

//* POJO = Plain Ol' Java Object
public class User {

    private String user_id;
    private String username;
    private String email;
    private String password;
    private String given_name;
    private String surname;
    private boolean is_active;
    private String role;

    public User() {
        super();
    }

    public User(String user_id, String username, String email, String password, String given_name, String surname,
            boolean is_active) {
        this.user_id = user_id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.given_name = given_name;
        this.surname = surname;
        this.is_active = is_active;
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

    public void setPassword (String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(user_id, user.user_id)
            && Objects.equals(username, user.username) && Objects.equals(email, user.email) 
            && Objects.equals(password, user.password) && Objects.equals(given_name, user.given_name)
            && Objects.equals(surname, user.surname) && Objects.equals(is_active, user.is_active) 
            && Objects.equals(role, user.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user_id, username, email, password, given_name, surname, is_active, role);
    }

    @Override
    public String toString() {
        return  "User " + "{" +
                "user_id = '" + user_id +  "' " +
                "username = '" + username + "' " +
                "email = '" + email + "' " +
                "password = '" + password + "' " +
                "given_name = '" + given_name + "' " +
                "surname = '" + surname + "' " +
                "is_active = '" + is_active + "' " +
                "role = '" + role + "' " +
                "'}";
    }
}

/** set user's id, name, and task from the user or get their 
 * information from the database. Displays the current data
 * from the database using the above format.
 */