package com.users.model;

import java.sql.Timestamp;

public class TravelItinerary {
    private int itineraryID;
    private int userID;
    private int bookingID;
    private String notes;
    private Timestamp createdAt;

    // Constructors
    public TravelItinerary() {}

    public TravelItinerary(int userID, int bookingID, String notes) {
        this.userID = userID;
        this.bookingID = bookingID;
        this.notes = notes;
    }

    // Getters and Setters
    public int getItineraryID() {
        return itineraryID;
    }

    public void setItineraryID(int itineraryID) {
        this.itineraryID = itineraryID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getBookingID() {
        return bookingID;
    }

    public void setBookingID(int bookingID) {
        this.bookingID = bookingID;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}
