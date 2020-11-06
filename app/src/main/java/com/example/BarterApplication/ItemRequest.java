package com.example.BarterApplication;

import com.example.BarterApplication.helpers.UidService;

import java.util.ArrayList;

public class ItemRequest {
        private String requesterId;
        private ArrayList<String> ItemIdsOffered;
        private String itemId;

        public ItemRequest(User u, Item wanted, ArrayList<Item> offered ) {
            if(offered.size() != 0){
                this.requesterId = u.getUid();
                this.itemId = wanted.getUid();
                this.ItemIdsOffered = new ArrayList<String>();
                int i = 0;
                for(i = 0; i < offered.size(); i++){
                    if(!this.ItemIdsOffered.contains(offered.get(i).getUid())){
                        this.ItemIdsOffered.add(offered.get(i).getUid());
                    }
                }
            }
        }

    public String getRequesterId() {
        return requesterId;
    }

    public ArrayList<String> getItemIdsOffered(){
        /** @todo */
        return this.ItemIdsOffered;
    }
}
