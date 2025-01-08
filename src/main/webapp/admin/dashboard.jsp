<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Admin Dashboard - Travel Management</title>
    <link rel="stylesheet" href="../css/style.css">
    <link rel="stylesheet" href="../css/admin.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
</head>
<body>
    <div class="admin-container">
        <!-- Sidebar -->
        <div class="admin-sidebar">
            <div class="sidebar-header">
                <h3>Admin Panel</h3>
            </div>
            <ul class="sidebar-menu">
                <li class="active">
                    <a href="dashboard.jsp">
                        <i class="fas fa-tachometer-alt"></i> Dashboard
                    </a>
                </li>
                <li>
                    <a href="packages.jsp">
                        <i class="fas fa-box"></i> Packages
                    </a>
                </li>
                <li>
                    <a href="bookings.jsp">
                        <i class="fas fa-calendar-check"></i> Bookings
                    </a>
                </li>
                <li>
                    <a href="users.jsp">
                        <i class="fas fa-users"></i> Users
                    </a>
                </li>
                <li>
                    <a href="reviews.jsp">
                        <i class="fas fa-star"></i> Reviews
                    </a>
                </li>
                <li>
                    <a href="payments.jsp">
                        <i class="fas fa-credit-card"></i> Payments
                    </a>
                </li>
            </ul>
        </div>

        <!-- Main Content -->
        <div class="admin-content">
            <div class="admin-header">
                <div class="header-search">
                    <input type="text" placeholder="Search...">
                </div>
                <div class="header-user">
                    <span>Welcome, ${userObj.fullName}</span>
                    <a href="../logout" class="btn btn-outline-danger btn-sm">Logout</a>
                </div>
            </div>

            <div class="dashboard-stats">
                <div class="stat-card">
                    <div class="stat-icon bg-primary">
                        <i class="fas fa-users"></i>
                    </div>
                    <div class="stat-details">
                        <h3>${totalUsers}</h3>
                        <p>Total Users</p>
                    </div>
                </div>
                <div class="stat-card">
                    <div class="stat-icon bg-success">
                        <i class="fas fa-box"></i>
                    </div>
                    <div class="stat-details">
                        <h3>${totalPackages}</h3>
                        <p>Active Packages</p>
                    </div>
                </div>
                <div class="stat-card">
                    <div class="stat-icon bg-warning">
                        <i class="fas fa-calendar-check"></i>
                    </div>
                    <div class="stat-details">
                        <h3>${totalBookings}</h3>
                        <p>Total Bookings</p>
                    </div>
                </div>
                <div class="stat-card">
                    <div class="stat-icon bg-info">
                        <i class="fas fa-dollar-sign"></i>
                    </div>
                    <div class="stat-details">
                        <h3>$${totalRevenue}</h3>
                        <p>Total Revenue</p>
                    </div>
                </div>
            </div>

            <div class="dashboard-charts">
                <div class="chart-container">
                    <h4>Booking Statistics</h4>
                    <canvas id="bookingChart"></canvas>
                </div>
                <div class="chart-container">
                    <h4>Revenue Overview</h4>
                    <canvas id="revenueChart"></canvas>
                </div>
            </div>

            <div class="dashboard-tables">
                <div class="recent-bookings">
                    <h4>Recent Bookings</h4>
                    <table class="table">
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>User</th>
                                <th>Package</th>
                                <th>Date</th>
                                <th>Amount</th>
                                <th>Status</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${recentBookings}" var="booking">
                                <tr>
                                    <td>#${booking.id}</td>
                                    <td>${booking.userName}</td>
                                    <td>${booking.packageName}</td>
                                    <td>${booking.bookingDate}</td>
                                    <td>$${booking.totalPrice}</td>
                                    <td>
                                        <span class="status-badge ${booking.status.toLowerCase()}">
                                            ${booking.status}
                                        </span>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
    <script>
        // Booking Chart
        const bookingCtx = document.getElementById('bookingChart').getContext('2d');
        new Chart(bookingCtx, {
            type: 'line',
            data: {
                labels: ${bookingLabels},
                datasets: [{
                    label: 'Bookings',
                    data: ${bookingData},
                    borderColor: '#4e73df',
                    tension: 0.1
                }]
            },
            options: {
                responsive: true,
                maintainAspectRatio: false
            }
        });

        // Revenue Chart
        const revenueCtx = document.getElementById('revenueChart').getContext('2d');
        new Chart(revenueCtx, {
            type: 'bar',
            data: {
                labels: ${revenueLabels},
                datasets: [{
                    label: 'Revenue',
                    data: ${revenueData},
                    backgroundColor: '#1cc88a'
                }]
            },
            options: {
                responsive: true,
                maintainAspectRatio: false
            }
        });
    </script>
</body>
</html>
