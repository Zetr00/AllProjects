package com.example.petportal;

import java.io.Serializable;

public class Pet implements Serializable {
    public int idPets;
    public String namePets;
    public String dateOfBirth;
    public String color;
    public String gender;
    public float weightPet;
    public float heightPet;
    public String descriptionPet;
    public String photo;
    public boolean isDeleted;
    public int reasonId;
    public int statusId;
    public int typeId;
    public int breedId;
    public int castrationId;
}