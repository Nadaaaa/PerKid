package com.example.xdev.perkid.models;

public class HistoryAdapterModel {

    private String socialMediaName;
    private String socialMediaTimeAndData;
    private String kidLocation;

    public HistoryAdapterModel(String socialMediaName, String socialMediaTimeAndData,String kidLocation) {
        this.socialMediaName = socialMediaName;
        this.socialMediaTimeAndData = socialMediaTimeAndData;
        this.kidLocation=kidLocation;
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

    public String getKidLocation() {
        return kidLocation;
    }

    public void setKidLocation(String kidLocation) {
        this.kidLocation = kidLocation;
    }
}
