package com.example.BarterApplication;

import android.location.Location;
import android.location.LocationManager;

import com.example.BarterApplication.helpers.LocationHelper;
import com.example.BarterApplication.helpers.UidService;

import java.io.Serializable;
import java.util.ArrayList;


public class Item implements Serializable {
    // THE ABSOLUTE MINIMUM REQUIRED IS the uid and name (for user)
    private String name = null;
    private String description = null;
    private ArrayList<String> labels;
    private String uid;
    private String ownerId;
    private SimpleLocation location;

    public SimpleLocation getLocation() {
        return location;
    }

    public void setLocation(SimpleLocation location) {
        this.location = location;
    }

    //@todo PHOTO

    // ctor
    public Item() {
        this.name = null;
        this.labels = new ArrayList<String>();
        this.ownerId = null;
        this.uid = UidService.newUID();
        this.location = new SimpleLocation(0, 0);
    }

    public Item(String name, String ownerId) {
        this.name = name;
        this.labels = new ArrayList<String>();
        this.ownerId = ownerId;
        this.uid = UidService.newUID();
        this.location = new SimpleLocation(0, 0);

    }

    // other form of ctor
    public Item(String name, String description, ArrayList<String> labels, String ownerId) {
        this.name = name;
        this.description = description;
        this.uid = UidService.newUID();
        this.labels = labels;
        removeDuplicateLabels();
        this.ownerId = ownerId;
        this.location = new SimpleLocation(0, 0);

    }

    // ctor
    public Item(String name, String description, String ownerId) {
        this.name = name;
        this.description = description;
        this.ownerId = ownerId;
        this.uid = UidService.newUID();
        this.location = new SimpleLocation(0, 0);

    }

    // other form of ctor
    public Item(String name, String description, ArrayList<String> labels, String ownerId, SimpleLocation location) {
        this.name = name;
        this.description = description;
        this.uid = UidService.newUID();
        this.labels = labels;
        removeDuplicateLabels();
        this.ownerId = ownerId;
        this.location = location;
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
        removeDuplicateLabels();
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

    /**
     * @brief remove duplicate labels from an item
     */
    private void removeDuplicateLabels(){
        ArrayList<String> singles = new ArrayList<String>();
        for(int i = 0; i < this.labels.size(); i++){
            if(!singles.contains(this.labels.get(i))) {
                singles.add(this.labels.get(i));
            }
        }
        this.labels = singles;
    }


    public String toString(){
        String str = new String();
        str += "name: "  + this.name + " ";
        str += "ownerId: " + this.ownerId + " ";
        str += "UID: " + this.uid + " ";
        str += "Location: " + this.location.toString();
        str += "desc: ";
        if(this.description != null){
            str += this.description;
        }
        else {
            str += "null";
        }
        str += " ";
        str += "labels: ";
        if(this.labels.size() > 0){
            str += "[";
            for(int i = 0; i < this.labels.size(); i++){
                String l = this.labels.get(i);
                str += l;
                if(i != this.labels.size() -1){
                    str += ", ";
                }
            }
            str += "] ";
        }
        else {
            str += "[ ] (Empty)";
        }
        return str;
    }
}
