package com.example.messenger;

import java.util.stream.Stream;

public class Messages {
    private String NickName;
    private String Message;

    public Messages(String NickName, String Message){
        this.NickName = NickName;
        this.Message = Message;
    }

    public String getNickName() {
        return NickName;
    }

    public void setNickName(String nickName) {
        NickName = nickName;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }
}
