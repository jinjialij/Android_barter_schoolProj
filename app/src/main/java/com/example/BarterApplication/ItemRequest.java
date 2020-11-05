package com.example.BarterApplication;

import com.example.BarterApplication.helpers.UID_Service;

import java.util.ArrayList;

public class ItemRequest {
    private String requesterId;
    private ArrayList<String> ItemIdsOffered;
    private String itemId;

    public ItemRequest(User u, Item wanted, Item offered ) {
        this.requesterId = u.getUid();
        this.itemId = wanted.getUid();
        this.ItemIdsOffered = new ArrayList<String>();
        this.ItemIdsOffered.add(offered.getUid());
    }

    public String getRequesterId() {
        return requesterId;
    }

    public ArrayList<String> getOfferings(){
        /** @todo */
        return null;
    }

    public Item getItem(){
        /** @todo */
        return UID_Service.findItemById(this.itemId);
    }

    public User getRequester(){
        /** @todo */
        return null;
    }

    public User getOwner(){
        return null;
    }

}
