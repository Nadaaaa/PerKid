package com.example.xdev.perkid.models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class CurrentUser extends RealmObject {

    @PrimaryKey
    String username;
    String password;
    boolean isLogged;

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

    public boolean isLogged() {
        return isLogged;
    }

    public void setLogged(boolean logged) {
        isLogged = logged;
    }
}
