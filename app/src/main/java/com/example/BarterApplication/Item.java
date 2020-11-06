package com.example.BarterApplication;

import com.example.BarterApplication.helpers.UidService;

import java.util.ArrayList;


public class Item {
    private String name = null; // THE ABSOLUTE MINIMUM REQUIRED IS A NAME
    private String description = null;
    private ArrayList<String> labels;
    private String uid;
    private String ownerId;
    //@todo PHOTO

    // ctor
    public Item() {
        this.name = null;
        this.labels = new ArrayList<String>();
        this.ownerId = null;
        this.uid = UidService.newUID();
    }

    public Item(String name, String ownerId) {
        this.name = name;
        this.labels = new ArrayList<String>();
        this.ownerId = ownerId;
        this.uid = UidService.newUID();
    }

    // other form of ctor
    public Item(String name, String description, ArrayList<String> labels, String ownerId) {
        this.name = name;
        this.description = description;
        this.uid = UidService.newUID();
        this.labels = labels;
        removeDuplicateLabels();
        this.ownerId = ownerId;
    }

    // ctor
    public Item(String name, String description, String ownerId) {
        this.name = name;
        this.description = description;
        this.ownerId = ownerId;
        this.uid = UidService.newUID();
    }



    public String getUid() {
        return this.uid;
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return description;
    }

    public ArrayList<String> getLabels() {
        return this.labels;
    }

    public String getOwnerId() {
        return this.ownerId;
    }

    public void addLabel(String lbl) {
        if(!this.labels.contains(lbl)){
            labels.add(lbl);
        }
    }

    public void removeLabel(String lbl) {
        if(this.labels.contains((lbl))){
            labels.remove(lbl);
        }
    }

    public void setDescription(String desc) {
        this.description = desc;
    }

    public void addRequest(User u) {
        // add to list
    }


    public User[] getRequests() {
        //return list

        return null;
    }

    private void removeDuplicateLabels(){
        ArrayList<String> singles = new ArrayList<String>();
        for(int i = 0; i < this.labels.size(); i++){
            if(!singles.contains(this.labels.get(i))) {
                singles.add(this.labels.get(i));
            }
        }
        this.labels = singles;
    }


}
