package com.travel.dao;

import com.travel.model.Review;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReviewDAO {
    private Connection conn;

    public ReviewDAO(Connection conn) {
        this.conn = conn;
    }

    public boolean addReview(Review review) {
        boolean success = false;
        try {
            String sql = "INSERT INTO reviews (user_id, package_id, rating, comment, review_date) " +
                        "VALUES (?, ?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, review.getUserId());
            ps.setInt(2, review.getPackageId());
            ps.setInt(3, review.getRating());
            ps.setString(4, review.getComment());
            ps.setDate(5, new java.sql.Date(review.getDate().getTime()));

            int i = ps.executeUpdate();
            if (i == 1) {
                success = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return success;
    }

    public List<Review> getPackageReviews(int packageId) {
        List<Review> reviews = new ArrayList<>();
        try {
            String sql = "SELECT r.*, u.full_name as user_name FROM reviews r " +
                        "JOIN users u ON r.user_id = u.id " +
                        "WHERE r.package_id = ? ORDER BY r.review_date DESC";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, packageId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Review review = new Review();
                review.setId(rs.getInt("id"));
                review.setUserId(rs.getInt("user_id"));
                review.setPackageId(rs.getInt("package_id"));
                review.setUserName(rs.getString("user_name"));
                review.setRating(rs.getInt("rating"));
                review.setComment(rs.getString("comment"));
                review.setDate(rs.getDate("review_date"));
                reviews.add(review);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return reviews;
    }

    public double getAverageRating(int packageId) {
        double avgRating = 0;
        try {
            String sql = "SELECT AVG(rating) as avg_rating FROM reviews WHERE package_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, packageId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                avgRating = rs.getDouble("avg_rating");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return avgRating;
    }

    public boolean hasUserReviewed(int userId, int packageId) {
        boolean hasReviewed = false;
        try {
            String sql = "SELECT COUNT(*) as count FROM reviews WHERE user_id = ? AND package_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, userId);
            ps.setInt(2, packageId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                hasReviewed = rs.getInt("count") > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hasReviewed;
    }
}
