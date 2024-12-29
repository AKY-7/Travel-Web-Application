package com.users.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class TravelListing {
    private int listingID;
    private int travelAgentID;
    private ListingType listingType;
    private String title;
    private String description;
    private BigDecimal price;
    private ListingStatus status;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    public enum ListingType {
        Flight, Hotel, CarRental
    }

    public enum ListingStatus {
        Pending, Approved, Rejected
    }

    // Constructors
    public TravelListing() {}

    public TravelListing(int travelAgentID, String listingType, String title, String description, BigDecimal price) {
        this.travelAgentID = travelAgentID;
        this.listingType = ListingType.valueOf(listingType);
        this.title = title;
        this.description = description;
        this.price = price;
        this.status = ListingStatus.Pending;
    }

    // Getters and Setters
    public int getListingID() {
        return listingID;
    }

    public void setListingID(int listingID) {
        this.listingID = listingID;
    }

    public int getTravelAgentID() {
        return travelAgentID;
    }

    public void setTravelAgentID(int travelAgentID) {
        this.travelAgentID = travelAgentID;
    }

    public ListingType getListingType() {
        return listingType;
    }

    public void setListingType(String listingType) {
        this.listingType = ListingType.valueOf(listingType);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public ListingStatus getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = ListingStatus.valueOf(status);
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }
}
