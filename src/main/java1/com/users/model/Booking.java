package com.users.model;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

public class Booking {
    private int bookingID;
    private int userID;
    private int listingID;
    private Date travelDate;
    private Timestamp bookingDate;
    private int quantity;
    private BigDecimal totalAmount;
    private BookingStatus status;

    public enum BookingStatus {
        Pending, Confirmed, Cancelled
    }

    // Constructors
    public Booking() {}

    public Booking(int userID, int listingID, Date travelDate, int quantity, BigDecimal totalAmount) {
        this.userID = userID;
        this.listingID = listingID;
        this.travelDate = travelDate;
        this.quantity = quantity;
        this.totalAmount = totalAmount;
        this.status = BookingStatus.Pending;
    }

    // Getters and Setters
    public int getBookingID() {
        return bookingID;
    }

    public void setBookingID(int bookingID) {
        this.bookingID = bookingID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getListingID() {
        return listingID;
    }

    public void setListingID(int listingID) {
        this.listingID = listingID;
    }

    public Date getTravelDate() {
        return travelDate;
    }

    public void setTravelDate(Date travelDate) {
        this.travelDate = travelDate;
    }

    public Timestamp getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(Timestamp bookingDate) {
        this.bookingDate = bookingDate;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public BookingStatus getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = BookingStatus.valueOf(status);
    }
}
