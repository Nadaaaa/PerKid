package com.example.xdev.perkid.models;

public class HistoryAdapterModel {

    private String socialMediaName;
    private String socialMediaTimeAndData;

    public HistoryAdapterModel(String socialMediaName, String socialMediaTimeAndData) {
        this.socialMediaName = socialMediaName;
        this.socialMediaTimeAndData = socialMediaTimeAndData;
    }

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
}
