package com.users.model;

import java.sql.Timestamp;

public class Message {
    private int messageID;
    private int senderID;
    private int receiverID;
    private String messageContent;
    private Timestamp sentAt;

    // Constructors
    public Message() {}

    public Message(int senderID, int receiverID, String messageContent) {
        this.senderID = senderID;
        this.receiverID = receiverID;
        this.messageContent = messageContent;
    }

    // Getters and Setters
    public int getMessageID() {
        return messageID;
    }

    public void setMessageID(int messageID) {
        this.messageID = messageID;
    }

    public int getSenderID() {
        return senderID;
    }

    public void setSenderID(int senderID) {
        this.senderID = senderID;
    }

    public int getReceiverID() {
        return receiverID;
    }

    public void setReceiverID(int receiverID) {
        this.receiverID = receiverID;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }

    public Timestamp getSentAt() {
        return sentAt;
    }

    public void setSentAt(Timestamp sentAt) {
        this.sentAt = sentAt;
    }
}
