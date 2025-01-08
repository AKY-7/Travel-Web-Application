<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Profile - Travel Management</title>
<link rel="stylesheet" href="css/style.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
</head>
<body>
    <%@include file="components/navbar.jsp" %>
    
    <div class="container mt-5">
        <div class="row">
            <div class="col-md-4">
                <div class="card">
                    <div class="card-body text-center">
                        <i class="fas fa-user-circle fa-5x mb-3"></i>
                        <h4>${userObj.fullName}</h4>
                        <p class="text-muted">${userObj.email}</p>
                    </div>
                </div>
                
                <div class="list-group mt-4">
                    <a href="#profile" class="list-group-item active" data-toggle="tab">
                        <i class="fas fa-user-edit"></i> Edit Profile
                    </a>
                    <a href="#bookings" class="list-group-item" data-toggle="tab">
                        <i class="fas fa-list"></i> My Bookings
                    </a>
                    <a href="#password" class="list-group-item" data-toggle="tab">
                        <i class="fas fa-key"></i> Change Password
                    </a>
                </div>
            </div>
            
            <div class="col-md-8">
                <div class="tab-content">
                    <!-- Profile Edit Section -->
                    <div id="profile" class="tab-pane active">
                        <div class="card">
                            <div class="card-header">
                                <h5>Edit Profile</h5>
                            </div>
                            <div class="card-body">
                                <form action="user/update" method="post">
                                    <div class="form-group">
                                        <label>Full Name</label>
                                        <input type="text" name="fullName" class="form-control" 
                                               value="${userObj.fullName}" required>
                                    </div>
                                    
                                    <div class="form-group">
                                        <label>Email</label>
                                        <input type="email" name="email" class="form-control" 
                                               value="${userObj.email}" required>
                                    </div>
                                    
                                    <div class="form-group">
                                        <label>Phone</label>
                                        <input type="tel" name="phone" class="form-control" 
                                               value="${userObj.phone}" required>
                                    </div>
                                    
                                    <button type="submit" class="btn btn-primary">Update Profile</button>
                                </form>
                            </div>
                        </div>
                    </div>
                    
                    <!-- Bookings Section -->
                    <div id="bookings" class="tab-pane">
                        <div class="card">
                            <div class="card-header">
                                <h5>My Bookings</h5>
                            </div>
                            <div class="card-body">
                                <c:forEach items="${bookings}" var="booking">
                                    <div class="booking-item border-bottom pb-3 mb-3">
                                        <h6>${booking.packageName}</h6>
                                        <p class="mb-1">
                                            <strong>Booking Date:</strong> ${booking.bookingDate}<br>
                                            <strong>Persons:</strong> ${booking.numberOfPersons}<br>
                                            <strong>Total Price:</strong> $${booking.totalPrice}<br>
                                            <strong>Status:</strong> 
                                            <span class="badge ${booking.status == 'CONFIRMED' ? 'badge-success' : 'badge-warning'}">
                                                ${booking.status}
                                            </span>
                                        </p>
                                        <c:if test="${booking.status != 'CANCELLED'}">
                                            <button class="btn btn-sm btn-danger" 
                                                    onclick="cancelBooking(${booking.id})">
                                                Cancel Booking
                                            </button>
                                        </c:if>
                                    </div>
                                </c:forEach>
                            </div>
                        </div>
                    </div>
                    
                    <!-- Change Password Section -->
                    <div id="password" class="tab-pane">
                        <div class="card">
                            <div class="card-header">
                                <h5>Change Password</h5>
                            </div>
                            <div class="card-body">
                                <form action="user/change-password" method="post" onsubmit="return validatePassword()">
                                    <div class="form-group">
                                        <label>Current Password</label>
                                        <input type="password" name="currentPassword" class="form-control" required>
                                    </div>
                                    
                                    <div class="form-group">
                                        <label>New Password</label>
                                        <input type="password" name="newPassword" id="newPassword" 
                                               class="form-control" required>
                                    </div>
                                    
                                    <div class="form-group">
                                        <label>Confirm New Password</label>
                                        <input type="password" name="confirmPassword" id="confirmPassword" 
                                               class="form-control" required>
                                    </div>
                                    
                                    <button type="submit" class="btn btn-primary">Change Password</button>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    
    <%@include file="components/footer.jsp" %>
    
    <script>
    function validatePassword() {
        var newPass = document.getElementById('newPassword').value;
        var confirmPass = document.getElementById('confirmPassword').value;
        
        if (newPass !== confirmPass) {
            alert('Passwords do not match!');
            return false;
        }
        return true;
    }
    
    function cancelBooking(bookingId) {
        if (confirm('Are you sure you want to cancel this booking?')) {
            fetch('booking/cancel/' + bookingId, {
                method: 'POST'
            })
            .then(response => response.json())
            .then(data => {
                if (data.success) {
                    location.reload();
                } else {
                    alert('Failed to cancel booking: ' + data.message);
                }
            })
            .catch(error => {
                alert('Error canceling booking');
            });
        }
    }
    
    // Tab handling
    document.querySelectorAll('.list-group-item').forEach(item => {
        item.addEventListener('click', function(e) {
            e.preventDefault();
            // Remove active class from all items
            document.querySelectorAll('.list-group-item').forEach(i => {
                i.classList.remove('active');
            });
            // Add active class to clicked item
            this.classList.add('active');
            
            // Show corresponding tab
            const tabId = this.getAttribute('href').substring(1);
            document.querySelectorAll('.tab-pane').forEach(tab => {
                tab.classList.remove('active');
            });
            document.getElementById(tabId).classList.add('active');
        });
    });
    </script>
</body>
</html>
