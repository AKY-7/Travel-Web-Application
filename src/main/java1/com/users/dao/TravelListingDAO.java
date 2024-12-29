package com.users.dao;

import com.users.model.TravelListing;
import com.users.util.DatabaseUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TravelListingDAO {
    
    public boolean createListing(TravelListing listing) {
        String sql = "INSERT INTO TravelListings (TravelAgentID, ListingType, Title, Description, Price) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, listing.getTravelAgentID());
            pstmt.setString(2, listing.getListingType().toString());
            pstmt.setString(3, listing.getTitle());
            pstmt.setString(4, listing.getDescription());
            pstmt.setBigDecimal(5, listing.getPrice());
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public TravelListing getListingById(int listingId) {
        String sql = "SELECT * FROM TravelListings WHERE ListingID = ?";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, listingId);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return extractListingFromResultSet(rs);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public List<TravelListing> getListingsByAgent(int agentId) {
        List<TravelListing> listings = new ArrayList<>();
        String sql = "SELECT * FROM TravelListings WHERE TravelAgentID = ?";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, agentId);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                listings.add(extractListingFromResultSet(rs));
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listings;
    }
    
    public boolean updateListingStatus(int listingId, String status) {
        String sql = "UPDATE TravelListings SET Status = ? WHERE ListingID = ?";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, status);
            pstmt.setInt(2, listingId);
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public List<TravelListing> searchListings(String listingType, BigDecimal maxPrice) {
        List<TravelListing> listings = new ArrayList<>();
        String sql = "SELECT * FROM TravelListings WHERE ListingType = ? AND Price <= ? AND Status = 'Approved'";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, listingType);
            pstmt.setBigDecimal(2, maxPrice);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                listings.add(extractListingFromResultSet(rs));
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listings;
    }

    private TravelListing extractListingFromResultSet(ResultSet rs) throws SQLException {
        TravelListing listing = new TravelListing();
        listing.setListingID(rs.getInt("ListingID"));
        listing.setTravelAgentID(rs.getInt("TravelAgentID"));
        listing.setListingType(rs.getString("ListingType"));
        listing.setTitle(rs.getString("Title"));
        listing.setDescription(rs.getString("Description"));
        listing.setPrice(rs.getBigDecimal("Price"));
        listing.setStatus(rs.getString("Status"));
        listing.setCreatedAt(rs.getTimestamp("CreatedAt"));
        listing.setUpdatedAt(rs.getTimestamp("UpdatedAt"));
        return listing;
    }
}
