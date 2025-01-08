package com.travel.dao;

import com.travel.model.Payment;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PaymentDAO {
    private Connection conn;

    public PaymentDAO(Connection conn) {
        this.conn = conn;
    }

    public boolean createPayment(Payment payment) {
        try {
            String sql = "INSERT INTO payments (booking_id, amount, payment_method, transaction_id, " +
                        "status, payment_date, currency, payment_gateway, error_message) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, payment.getBookingId());
            ps.setDouble(2, payment.getAmount());
            ps.setString(3, payment.getPaymentMethod());
            ps.setString(4, payment.getTransactionId());
            ps.setString(5, payment.getStatus());
            ps.setTimestamp(6, new Timestamp(payment.getPaymentDate().getTime()));
            ps.setString(7, payment.getCurrency());
            ps.setString(8, payment.getPaymentGateway());
            ps.setString(9, payment.getErrorMessage());

            int affectedRows = ps.executeUpdate();
            if (affectedRows > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    payment.setId(rs.getInt(1));
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Payment getPayment(int paymentId) {
        try {
            String sql = "SELECT * FROM payments WHERE id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, paymentId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return mapResultSetToPayment(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Payment> getPaymentsByBooking(int bookingId) {
        List<Payment> payments = new ArrayList<>();
        try {
            String sql = "SELECT * FROM payments WHERE booking_id = ? ORDER BY payment_date DESC";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, bookingId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                payments.add(mapResultSetToPayment(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return payments;
    }

    public boolean updatePaymentStatus(int paymentId, String status, String transactionId, String errorMessage) {
        try {
            String sql = "UPDATE payments SET status = ?, transaction_id = ?, error_message = ? WHERE id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, status);
            ps.setString(2, transactionId);
            ps.setString(3, errorMessage);
            ps.setInt(4, paymentId);

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public double getTotalRevenue() {
        try {
            String sql = "SELECT SUM(amount) as total FROM payments WHERE status = 'SUCCESS'";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            if (rs.next()) {
                return rs.getDouble("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0;
    }

    private Payment mapResultSetToPayment(ResultSet rs) throws SQLException {
        Payment payment = new Payment();
        payment.setId(rs.getInt("id"));
        payment.setBookingId(rs.getInt("booking_id"));
        payment.setAmount(rs.getDouble("amount"));
        payment.setPaymentMethod(rs.getString("payment_method"));
        payment.setTransactionId(rs.getString("transaction_id"));
        payment.setStatus(rs.getString("status"));
        payment.setPaymentDate(rs.getTimestamp("payment_date"));
        payment.setCurrency(rs.getString("currency"));
        payment.setPaymentGateway(rs.getString("payment_gateway"));
        payment.setErrorMessage(rs.getString("error_message"));
        return payment;
    }
}
