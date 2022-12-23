package com.example.uas_firebase;

import java.util.Date;

public class Complaints {
    private String key;
    private String title;
    private String content;
    private String date;
    private String location;
    private String displayName;
    private String createdBy;

    public Complaints() {

    }

    public Complaints(String title, String content, String date, String location, String key, String displayName) {
        this.title = title;
        this.content = content;
        this.date = date;
        this.location = location;
        this.createdBy = key;
        this.displayName = displayName;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }



}
