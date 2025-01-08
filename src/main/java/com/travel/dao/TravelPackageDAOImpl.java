package com.travel.dao;

import com.travel.model.TravelPackage;
import com.travel.util.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TravelPackageDAOImpl implements TravelPackageDAO {
    private Connection connection;

    public TravelPackageDAOImpl() {
        try {
            this.connection = DatabaseConnection.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addPackage(TravelPackage package) {
        String sql = "INSERT INTO travel_packages (package_name, destination, price, start_date, end_date, description, max_capacity) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, package.getPackageName());
            stmt.setString(2, package.getDestination());
            stmt.setDouble(3, package.getPrice());
            stmt.setDate(4, new java.sql.Date(package.getStartDate().getTime()));
            stmt.setDate(5, new java.sql.Date(package.getEndDate().getTime()));
            stmt.setString(6, package.getDescription());
            stmt.setInt(7, package.getMaxCapacity());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public TravelPackage getPackageById(int id) {
        String sql = "SELECT * FROM travel_packages WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return extractPackageFromResultSet(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<TravelPackage> getAllPackages() {
        List<TravelPackage> packages = new ArrayList<>();
        String sql = "SELECT * FROM travel_packages";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                packages.add(extractPackageFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return packages;
    }

    @Override
    public void updatePackage(TravelPackage package) {
        String sql = "UPDATE travel_packages SET package_name = ?, destination = ?, price = ?, " +
                    "start_date = ?, end_date = ?, description = ?, max_capacity = ? WHERE id = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, package.getPackageName());
            stmt.setString(2, package.getDestination());
            stmt.setDouble(3, package.getPrice());
            stmt.setDate(4, new java.sql.Date(package.getStartDate().getTime()));
            stmt.setDate(5, new java.sql.Date(package.getEndDate().getTime()));
            stmt.setString(6, package.getDescription());
            stmt.setInt(7, package.getMaxCapacity());
            stmt.setInt(8, package.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deletePackage(int id) {
        String sql = "DELETE FROM travel_packages WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private TravelPackage extractPackageFromResultSet(ResultSet rs) throws SQLException {
        TravelPackage package = new TravelPackage();
        package.setId(rs.getInt("id"));
        package.setPackageName(rs.getString("package_name"));
        package.setDestination(rs.getString("destination"));
        package.setPrice(rs.getDouble("price"));
        package.setStartDate(rs.getDate("start_date"));
        package.setEndDate(rs.getDate("end_date"));
        package.setDescription(rs.getString("description"));
        package.setMaxCapacity(rs.getInt("max_capacity"));
        return package;
    }
}
