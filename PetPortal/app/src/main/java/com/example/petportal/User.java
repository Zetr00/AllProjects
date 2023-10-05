package com.example.petportal;

import java.io.Serializable;

public class User implements Serializable {
    public int idUser;
    public String fioUser;
    public String dateOfBirth;
    public String phoneNumber;
    public String email;
    public String login;
    public String password;
    public String salt;
}
