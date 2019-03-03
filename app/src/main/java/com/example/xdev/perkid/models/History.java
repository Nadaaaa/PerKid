package com.example.xdev.perkid.models;

import io.realm.RealmObject;

public class History extends RealmObject {

    private String kidUsername;
    private String socialMediaName;
    private String socialMediaTimeAndData;

    public String getSocialMediaName() {
        return socialMediaName;
    }

    public void setSocialMediaName(String socialMediaName) {
        this.socialMediaName = socialMediaName;
    }

    public String getSocialMediaTimeAndData() {
        return socialMediaTimeAndData;
    }

    public void setSocialMediaTimeAndData(String socialMediaTimeAndData) {
        this.socialMediaTimeAndData = socialMediaTimeAndData;
    }

    public String getKidUsername() {
        return kidUsername;
    }

    public void setKidUsername(String kidUsername) {
        this.kidUsername = kidUsername;
    }
}
