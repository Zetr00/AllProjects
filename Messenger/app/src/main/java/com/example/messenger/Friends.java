package com.example.messenger;

public class Friends {
    private String FriendAcceptRequest;
    private String Friend;

    public Friends (String FriendAcceptRequest, String Friend){
        this.FriendAcceptRequest = FriendAcceptRequest;
        this.Friend = Friend;
    }

    public String getFriendAcceptRequest() {
        return FriendAcceptRequest;
    }

    public void setFriendAcceptRequest(String friendAcceptRequest) {
        FriendAcceptRequest = friendAcceptRequest;
    }

    public String getFriend() {
        return Friend;
    }

    public void setFriend(String friend) {
        Friend = friend;
    }
}
