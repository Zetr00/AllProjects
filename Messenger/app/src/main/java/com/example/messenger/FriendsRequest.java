package com.example.messenger;

public class FriendsRequest {
    private String NameUserRequest;
    private String NameUserForRequest;

    public FriendsRequest(String NameUserRequest, String NameUserForRequest){
        this.NameUserRequest = NameUserRequest;
        this.NameUserForRequest = NameUserForRequest;
    }

    public String getNameUserRequest() {
        return NameUserRequest;
    }

    public void setNameUserRequest(String nameUserRequest) {
        NameUserRequest = nameUserRequest;
    }

    public String getNameUserForRequest() {
        return NameUserForRequest;
    }

    public void setNameUserForRequest(String nameUserForRequest) {
        NameUserForRequest = nameUserForRequest;
    }
}
