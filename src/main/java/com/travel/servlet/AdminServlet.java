package com.travel.servlet;

import com.travel.dao.*;
import com.travel.model.*;
import com.travel.util.DatabaseConnection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.*;
import com.google.gson.Gson;

@WebServlet("/admin/*")
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024, // 1MB
    maxFileSize = 1024 * 1024 * 10,  // 10MB
    maxRequestSize = 1024 * 1024 * 50 // 50MB
)
public class AdminServlet extends HttpServlet {
    private Gson gson = new Gson();
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String pathInfo = request.getPathInfo();
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("userObj");
        
        // Check if user is admin
        if (user == null || !"ADMIN".equals(user.getRole())) {
            response.sendRedirect("../login.jsp");
            return;
        }
        
        try {
            if ("/dashboard".equals(pathInfo)) {
                getDashboardStats(request, response);
            }
            else if ("/package".equals(pathInfo)) {
                getPackageDetails(request, response);
            }
            else if ("/packages".equals(pathInfo)) {
                getPackagesList(request, response);
            }
            else if ("/bookings".equals(pathInfo)) {
                getBookingsList(request, response);
            }
            else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String pathInfo = request.getPathInfo();
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("userObj");
        
        // Check if user is admin
        if (user == null || !"ADMIN".equals(user.getRole())) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        
        try {
            if ("/package/save".equals(pathInfo)) {
                savePackage(request, response);
            }
            else if ("/package/delete".equals(pathInfo)) {
                deletePackage(request, response);
            }
            else if ("/booking/update".equals(pathInfo)) {
                updateBookingStatus(request, response);
            }
            else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
    
    private void getDashboardStats(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try {
            Connection conn = DatabaseConnection.getConnection();
            UserDAO userDao = new UserDAO(conn);
            BookingDAO bookingDao = new BookingDAO(conn);
            TravelPackageDAO packageDao = new TravelPackageDAO();
            
            // Get statistics
            int totalUsers = userDao.getTotalUsers();
            int totalPackages = packageDao.getTotalPackages();
            int totalBookings = bookingDao.getTotalBookings();
            double totalRevenue = bookingDao.getTotalRevenue();
            
            // Get recent bookings
            List<Booking> recentBookings = bookingDao.getRecentBookings(5);
            
            // Get booking statistics for chart
            Map<String, Integer> bookingStats = bookingDao.getBookingStatistics();
            
            // Set attributes
            request.setAttribute("totalUsers", totalUsers);
            request.setAttribute("totalPackages", totalPackages);
            request.setAttribute("totalBookings", totalBookings);
            request.setAttribute("totalRevenue", totalRevenue);
            request.setAttribute("recentBookings", recentBookings);
            request.setAttribute("bookingStats", bookingStats);
            
            request.getRequestDispatcher("/admin/dashboard.jsp").forward(request, response);
            
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
    
    private void savePackage(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try {
            // Get form data
            TravelPackage pkg = new TravelPackage();
            pkg.setPackageName(request.getParameter("packageName"));
            pkg.setDestination(request.getParameter("destination"));
            pkg.setPrice(Double.parseDouble(request.getParameter("price")));
            pkg.setMaxCapacity(Integer.parseInt(request.getParameter("maxCapacity")));
            // ... set other fields
            
            // Handle image upload
            Part filePart = request.getPart("images");
            if (filePart != null) {
                // Process and save image
                // Implementation depends on your image storage strategy
            }
            
            // Save package
            TravelPackageDAO dao = new TravelPackageDAO();
            boolean success = dao.savePackage(pkg);
            
            // Send response
            Map<String, Object> result = new HashMap<>();
            result.put("success", success);
            if (!success) {
                result.put("message", "Failed to save package");
            }
            
            response.setContentType("application/json");
            response.getWriter().write(gson.toJson(result));
            
        } catch (Exception e) {
            e.printStackTrace();
            Map<String, Object> result = new HashMap<>();
            result.put("success", false);
            result.put("message", e.getMessage());
            response.setContentType("application/json");
            response.getWriter().write(gson.toJson(result));
        }
    }
    
    private void deletePackage(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try {
            String pathInfo = request.getPathInfo();
            String[] pathParts = pathInfo.split("/");
            int packageId = Integer.parseInt(pathParts[pathParts.length - 1]);
            
            TravelPackageDAO dao = new TravelPackageDAO();
            boolean success = dao.deletePackage(packageId);
            
            Map<String, Object> result = new HashMap<>();
            result.put("success", success);
            if (!success) {
                result.put("message", "Failed to delete package");
            }
            
            response.setContentType("application/json");
            response.getWriter().write(gson.toJson(result));
            
        } catch (Exception e) {
            e.printStackTrace();
            Map<String, Object> result = new HashMap<>();
            result.put("success", false);
            result.put("message", e.getMessage());
            response.setContentType("application/json");
            response.getWriter().write(gson.toJson(result));
        }
    }
    
    private void updateBookingStatus(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try {
            int bookingId = Integer.parseInt(request.getParameter("bookingId"));
            String status = request.getParameter("status");
            
            BookingDAO dao = new BookingDAO(DatabaseConnection.getConnection());
            boolean success = dao.updateBookingStatus(bookingId, status);
            
            Map<String, Object> result = new HashMap<>();
            result.put("success", success);
            if (!success) {
                result.put("message", "Failed to update booking status");
            }
            
            response.setContentType("application/json");
            response.getWriter().write(gson.toJson(result));
            
        } catch (Exception e) {
            e.printStackTrace();
            Map<String, Object> result = new HashMap<>();
            result.put("success", false);
            result.put("message", e.getMessage());
            response.setContentType("application/json");
            response.getWriter().write(gson.toJson(result));
        }
    }
}
