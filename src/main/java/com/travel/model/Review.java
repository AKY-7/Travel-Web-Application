package com.travel.model;

import java.util.Date;

public class Review {
    private int id;
    private int userId;
    private int packageId;
    private String userName;
    private int rating;
    private String comment;
    private Date date;

    public Review() {
    }

    public Review(int userId, int packageId, int rating, String comment) {
        this.userId = userId;
        this.packageId = packageId;
        this.rating = rating;
        this.comment = comment;
        this.date = new Date();
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
