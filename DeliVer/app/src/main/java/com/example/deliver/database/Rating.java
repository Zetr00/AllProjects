package com.example.deliver.database;

public class Rating {
    private String id;
    private int rate;
    private String from;
    private String message;

    public Rating(){};

    public Rating(String id, int rate, String from, String message) {
        this.id = id;
        this.rate = rate;
        this.from = from;
        this.message = message;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
