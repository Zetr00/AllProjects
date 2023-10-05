package com.example.deliver.database;

public class Message {
    private String id;
    private String from;
    private String message;
    private int number;

    public Message(){}

    public Message(String id, String from, String message, int number) {
        this.id = id;
        this.from = from;
        this.message = message;
        this.number = number;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
