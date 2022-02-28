package com.thanhha.dto;

import java.io.Serializable;

public class AccountDTO implements Serializable {
    private String username;
    private String password;
    private String firstname;
    private boolean isAdmin;
    private String gmail;

    public AccountDTO() {
    }

    public AccountDTO(String username, String password, String firstname, boolean isAdmin, String gmail) {
        this.username = username;
        this.password = password;
        this.firstname = firstname;
        this.isAdmin = isAdmin;
        this.gmail = gmail;
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

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public String getGmail() {
        return gmail;
    }

    public void setGmail(String gmail) {
        this.gmail = gmail;
    }
}
