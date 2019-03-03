package com.example.xdev.perkid.models;

// This object is used to send data to the adapter.
public class SocialMediaAdapterModel {
    private String username;
    private int visitTimes;
    private String name;

    public SocialMediaAdapterModel() {

    }

    public SocialMediaAdapterModel(String username, int visitTimes, String name) {
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
