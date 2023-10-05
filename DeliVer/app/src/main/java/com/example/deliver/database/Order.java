package com.example.deliver.database;

public class Order {
    private String id;
    private String ordererName;
    private String ordererID;
    private String deliverName;
    private String deliverID;
    private String addressFrom;
    private String addressTo;
    private String description;
    private String Status;
    private int cost;

    public Order(){}

    public Order(String id, String ordererName, String ordererID, String deliverName, String deliverID, String addressFrom, String addressTo, String description, String status, int cost) {
        this.id = id;
        this.ordererName = ordererName;
        this.ordererID = ordererID;
        this.deliverName = deliverName;
        this.deliverID = deliverID;
        this.addressFrom = addressFrom;
        this.addressTo = addressTo;
        this.description = description;
        Status = status;
        this.cost = cost;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrdererName() {
        return ordererName;
    }

    public void setOrdererName(String ordererName) {
        this.ordererName = ordererName;
    }

    public String getOrdererID() {
        return ordererID;
    }

    public void setOrdererID(String ordererID) {
        this.ordererID = ordererID;
    }

    public String getDeliverName() {
        return deliverName;
    }

    public void setDeliverName(String deliverName) {
        this.deliverName = deliverName;
    }

    public String getDeliverID() {
        return deliverID;
    }

    public void setDeliverID(String deliverID) {
        this.deliverID = deliverID;
    }

    public String getAddressFrom() {
        return addressFrom;
    }

    public void setAddressFrom(String addressFrom) {
        this.addressFrom = addressFrom;
    }

    public String getAddressTo() {
        return addressTo;
    }

    public void setAddressTo(String addressTo) {
        this.addressTo = addressTo;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }
}
