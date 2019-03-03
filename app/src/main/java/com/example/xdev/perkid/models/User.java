package com.example.xdev.perkid.models;

import io.realm.RealmObject;

public class User extends RealmObject {

    private String username;
    private String password;
    private String accountType;
    private String kidUsername;
    private boolean isLogged;

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

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public boolean isLogged() {
        return isLogged;
    }

    public void setLogged(boolean logged) {
        isLogged = logged;
    }

    public String getKidUsername() {
        return kidUsername;
    }

    public void setKidUsername(String kidUsername) {
        this.kidUsername = kidUsername;
    }
}
