package com.example.BarterApplication.helpers;

public class AddItemHelper {

    String title, description,labels,ownerId,uuid;

    public AddItemHelper(String title, String description){

        this.title = title;
        this.description = description;

    }

    public AddItemHelper(String title, String description, String uuid) {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
