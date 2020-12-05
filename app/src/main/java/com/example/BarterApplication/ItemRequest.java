package com.example.BarterApplication;

import com.example.BarterApplication.helpers.UidService;

import java.io.Serializable;
import java.util.ArrayList;

public class ItemRequest implements Serializable {
    private String requesterId;
    private ArrayList<String> itemIdsOffered;
    private String requestItemId;
    private String uid;
    private boolean accepted;
    private String requesterEmail;
    private boolean completed;

    //This constructor is used for Firebase
    public ItemRequest() {
        this.requesterId = null;
        this.itemIdsOffered = new ArrayList<String>();
        this.requestItemId = null;
        this.uid = UidService.newUID();
        this.accepted = false;
        this.completed = false;
    }

    public ItemRequest(String userUID, Item wanted, ArrayList<Item> offered, String email) {
        this.uid = UidService.newUID();
        this.accepted = false;
        this.requesterEmail = email;
        this.completed = false;
        if(offered.size() != 0){
            this.requesterId = userUID;
            this.requestItemId = wanted.getUid();
            this.itemIdsOffered = new ArrayList<String>();
            int i = 0;
            for(i = 0; i < offered.size(); i++){
                if(!this.itemIdsOffered.contains(offered.get(i).getUid())){
                    this.itemIdsOffered.add(offered.get(i).getUid());
                }
            }
        }
    }

    //constructor for inserting test data
    public ItemRequest(String requesterId, Item requestItem, Item offerItem) {
        this.uid = UidService.newUID();
        this.requesterId = requesterId;
        this.requestItemId = requestItem.getUid();
        this.itemIdsOffered = new ArrayList<>();
        itemIdsOffered.add(offerItem.getUid());
        this.accepted = false;
        this.completed = false;
    }

    public String getRequesterId() {
        return requesterId;
    }

    public ArrayList<String> getItemIdsOffered(){
        /** @todo */
        return this.itemIdsOffered;
    }

    public String getRequestItemId() {
        return requestItemId;
    }

    public String getUid() {
        return this.uid;
    }

    public boolean isAccepted() {
        return accepted;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }

    public String getRequesterEmail(){return this.requesterEmail;}

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean responded) {
        this.completed = responded;
    }
}
