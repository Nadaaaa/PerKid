package com.example.xdev.perkid.models;

import io.realm.RealmObject;

public class SocialMedia extends RealmObject {

    // username is a foreign key
    private String username;
    private int visitTimes;
    private String name;

    public SocialMedia() {

    }

    public SocialMedia(String username, int visitTimes, String name) {
        this.username = username;
        this.visitTimes = visitTimes;
        this.name = name;
    }

    public int getVisitTimes() {
        return visitTimes;
    }

    public void setVisitTimes(int visitTimes) {
        this.visitTimes = visitTimes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
