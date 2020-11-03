package com.example.BarterApplication;

public class Item {
    private String name = null;
    private String description = null;
    private String[] labels = null;
    //@todo PHOTO

    // ctor
    public Item()
    {
        this.name = new String("enter a name");
    }


    public Item(String name) {
        this.name = name;
    }

    // ctor
    public Item(String name, String description, String[] labels) {
        this.name = name;
        this.description = description;
        this.labels = labels;
    }

    // ctor
    public Item(String name, String description) {
        this.name = name;
        this.description = description;
    }



    public String get_description() {
        return description;
    }

    public String[] get_labels() {
        return null;
    }


    public void add_label(String lbl) {
        // find duplicate,

        // if not duplicate add to the list
    }

    public void remove_label(String lbl) {
        // find in list

        // if in list, remove from list
    }


    public void add_description(String desc) {
        // overwrite the description
    }

    public void add_request(User u) {
        // add to list
    }


    public User[] get_requests() {
        //return list

        return null;
    }
}
