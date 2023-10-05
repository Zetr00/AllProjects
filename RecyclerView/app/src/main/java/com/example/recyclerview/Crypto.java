package com.example.recyclerview;

import java.io.Serializable;

public class Crypto implements Serializable {

    private String Name;
    private String Description;
    private int Photo;

    public Crypto(String Name, String Description, int Photo){

        this.Name=Name;
        this.Description=Description;
        this.Photo=Photo;
    }

    public String getName() {
        return this.Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getDescription() {
        return this.Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }

    public int getPhoto() {
        return this.Photo;
    }

    public void setPhoto(int Photo) {
        this.Photo = Photo;
    }
}