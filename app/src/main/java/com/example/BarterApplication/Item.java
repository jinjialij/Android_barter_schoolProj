package com.example.BarterApplication;

import java.util.ArrayList;
import java.util.Collections;

public class Item {
    private String name = null; // THE ABSOLUTE MINIMUM REQUIRED IS A NAME
    private String description = null;
    private ArrayList<String> labels;
    private String ownerId;
    //@todo PHOTO

    // ctor
    public Item()
    {
        this.name = null;
        this.labels = new ArrayList<String>();
        this.ownerId = null;
    }


    public Item(String name, String ownerId) {
        this.name = name;
        this.labels = new ArrayList<String>();
        this.ownerId = ownerId;
    }

    // other form of ctor
    public Item(String name, String description, ArrayList<String> labels, String ownerId) {
        this.name = name;
        this.description = description;
        this.labels = labels;
        removeDuplicateLabels();
        this.ownerId = ownerId;
    }


    // ctor
    public Item(String name, String description, String ownerId) {
        this.name = name;
        this.description = description;
        this.ownerId = ownerId;
    }

    public String get_name() {
        return this.name;
    }

    public String get_description() {
        return description;
    }

    public ArrayList<String> get_labels() {
        return this.labels;
    }

    public String getOwnerId() {
        return this.ownerId;
    }

    public void add_label(String lbl) {
        if(!this.labels.contains(lbl)){
            labels.add(lbl);
        }
    }

    public void remove_label(String lbl) {
        if(this.labels.contains((lbl))){
            labels.remove(lbl);
        }
    }

    public void set_description(String desc) {
        this.description = desc;
    }

    public void add_request(User u) {
        // add to list
    }


    public User[] get_requests() {
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
