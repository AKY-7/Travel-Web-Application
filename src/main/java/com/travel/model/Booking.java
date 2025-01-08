package com.travel.model;

import java.util.Date;

public class Booking {
    private int id;
    private int userId;
    private int packageId;
    private String packageName;
    private Date bookingDate;
    private Date travelDate;
    private int numberOfPersons;
    private double totalPrice;
    private String status;

    public Booking() {
    }

    public Booking(int userId, int packageId, Date travelDate, int numberOfPersons, double totalPrice) {
        this.userId = userId;
        this.packageId = packageId;
        this.bookingDate = new Date();
        this.travelDate = travelDate;
        this.numberOfPersons = numberOfPersons;
        this.totalPrice = totalPrice;
        this.status = "PENDING";
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getPackageId() {
        return packageId;
    }

    public void setPackageId(int packageId) {
        this.packageId = packageId;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public Date getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(Date bookingDate) {
        this.bookingDate = bookingDate;
    }

    public Date getTravelDate() {
        return travelDate;
    }

    public void setTravelDate(Date travelDate) {
        this.travelDate = travelDate;
    }

    public int getNumberOfPersons() {
        return numberOfPersons;
    }

    public void setNumberOfPersons(int numberOfPersons) {
        this.numberOfPersons = numberOfPersons;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
