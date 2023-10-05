package com.example.messenger;

public class dialog {
    private String NameDialog;
    private String Sender;
    private String Recipient;

    public dialog (String NameDialog, String Sender, String Recipient){
        this.NameDialog = NameDialog;
        this.Sender = Sender;
        this.Recipient = Recipient;
    }

    public String getNameDialog() {
        return NameDialog;
    }

    public void setNameDialog(String nameDialog) {
        NameDialog = nameDialog;
    }

    public String getSender() {
        return Sender;
    }

    public void setSender(String sender) {
        Sender = sender;
    }

    public String getRecipient() {
        return Recipient;
    }

    public void setRecipient(String recipient) {
        Recipient = recipient;
    }
}
