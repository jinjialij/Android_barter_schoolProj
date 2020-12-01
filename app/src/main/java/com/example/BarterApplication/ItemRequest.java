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
    private boolean deleted;

    //This constructor is used for Firebase
    public ItemRequest() {
        this.requesterId = null;
        this.itemIdsOffered = new ArrayList<String>();
        this.requestItemId = null;
        this.uid = UidService.newUID();
        this.accepted = false;
    }

    public ItemRequest(UserAccount u, Item wanted, ArrayList<Item> offered ) {
        this.uid = UidService.newUID();
        this.accepted = false;
        if(offered.size() != 0){
            this.requesterId = u.getUid();
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

    //This constructor is only used for Firebase insert dummy data for test
    public ItemRequest(String requesterId, String requestItemId, String uid) {
        this.uid = uid;
        this.requesterId = requesterId;
        this.requestItemId = requestItemId;
        this.itemIdsOffered = new ArrayList<>();
        this.accepted = false;
    }

    //constructor for inserting test data
    public ItemRequest(String requesterId, Item requestItem, Item offerItem) {
        this.uid = UidService.newUID();
        this.requesterId = requesterId;
        this.requestItemId = requestItem.getUid();
        this.itemIdsOffered = new ArrayList<>();
        itemIdsOffered.add(offerItem.getUid());
        this.accepted = false;
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

    public boolean isDeleted() {
        return deleted;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }

    public void setDeleted(boolean deleted){
        this.deleted = deleted;
    }

}
