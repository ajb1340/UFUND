package com.ufund.api.ufundapi.model;

import java.util.logging.Logger;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Model for account functions
 * 
 * @author Aron Lee
 */
public class Account {

    private static final Logger LOG = Logger.getLogger(Account.class.getName());
    static final String STRING_FORMAT = "Account [username=%s, password=%s, isAdmin=%s]";

    @JsonProperty("username") private String username;
    @JsonProperty("password") private String password;
    @JsonProperty("admin") private boolean isAdmin;

    public Account(@JsonProperty("username") String username, @JsonProperty("password") String password, @JsonProperty("admin") boolean isAdmin) {
        this.username = username;
        this.password = password;
        this.isAdmin = isAdmin;
    }

    public String getUsername() {
        return username;
    }

    public boolean checkPassword(String attempt) {
        return attempt.equals(password);
    }

    public void changePassword(String newPassword) {
        password = newPassword;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    @Override
    public String toString() {
        return String.format(STRING_FORMAT, username, password, isAdmin ? "TRUE" : "FALSE");
    }
}
