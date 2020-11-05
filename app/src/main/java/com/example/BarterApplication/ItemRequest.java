package com.example.BarterApplication;

public class ItemRequest {
    public String requesterId;
    public Item item;

    public ItemRequest(String requesterId, Item i)
    {
        this.item = i;
        this.requesterId = requesterId;
    }

    public String getRequesterId() {
        return requesterId;
    }

    public Item getItem() {
        return item;
    }
}
