package com.travel.dao;

import com.travel.model.Booking;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookingDAO {
    private Connection conn;

    public BookingDAO(Connection conn) {
        this.conn = conn;
    }

    public boolean createBooking(Booking booking) {
        boolean success = false;
        try {
            String sql = "INSERT INTO bookings (user_id, package_id, booking_date, travel_date, " +
                        "number_of_persons, total_price, status) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, booking.getUserId());
            ps.setInt(2, booking.getPackageId());
            ps.setDate(3, new java.sql.Date(booking.getBookingDate().getTime()));
            ps.setDate(4, new java.sql.Date(booking.getTravelDate().getTime()));
            ps.setInt(5, booking.getNumberOfPersons());
            ps.setDouble(6, booking.getTotalPrice());
            ps.setString(7, booking.getStatus());

            int i = ps.executeUpdate();
            if (i == 1) {
                success = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return success;
    }

    public List<Booking> getUserBookings(int userId) {
        List<Booking> bookings = new ArrayList<>();
        try {
            String sql = "SELECT b.*, p.package_name FROM bookings b " +
                        "JOIN travel_packages p ON b.package_id = p.id " +
                        "WHERE b.user_id = ? ORDER BY b.booking_date DESC";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Booking booking = new Booking();
                booking.setId(rs.getInt("id"));
                booking.setUserId(rs.getInt("user_id"));
                booking.setPackageId(rs.getInt("package_id"));
                booking.setPackageName(rs.getString("package_name"));
                booking.setBookingDate(rs.getDate("booking_date"));
                booking.setTravelDate(rs.getDate("travel_date"));
                booking.setNumberOfPersons(rs.getInt("number_of_persons"));
                booking.setTotalPrice(rs.getDouble("total_price"));
                booking.setStatus(rs.getString("status"));
                bookings.add(booking);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bookings;
    }

    public boolean cancelBooking(int bookingId, int userId) {
        boolean success = false;
        try {
            String sql = "UPDATE bookings SET status = 'CANCELLED' " +
                        "WHERE id = ? AND user_id = ? AND status != 'CANCELLED'";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, bookingId);
            ps.setInt(2, userId);

            int i = ps.executeUpdate();
            if (i == 1) {
                success = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return success;
    }

    public Booking getBooking(int bookingId) {
        Booking booking = null;
        try {
            String sql = "SELECT b.*, p.package_name FROM bookings b " +
                        "JOIN travel_packages p ON b.package_id = p.id " +
                        "WHERE b.id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, bookingId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                booking = new Booking();
                booking.setId(rs.getInt("id"));
                booking.setUserId(rs.getInt("user_id"));
                booking.setPackageId(rs.getInt("package_id"));
                booking.setPackageName(rs.getString("package_name"));
                booking.setBookingDate(rs.getDate("booking_date"));
                booking.setTravelDate(rs.getDate("travel_date"));
                booking.setNumberOfPersons(rs.getInt("number_of_persons"));
                booking.setTotalPrice(rs.getDouble("total_price"));
                booking.setStatus(rs.getString("status"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return booking;
    }
}
