<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>About Us - Travel Management</title>
<link rel="stylesheet" href="css/style.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
</head>
<body>
    <%@include file="components/navbar.jsp" %>
    
    <div class="container-fluid">
        <div class="about-section">
            <h1>About Us</h1>
            <p>Welcome to our Travel Management System. We are dedicated to providing the best travel experiences.</p>
            
            <div class="features">
                <div class="feature-item">
                    <i class="fas fa-globe"></i>
                    <h3>Worldwide Destinations</h3>
                    <p>Explore destinations across the globe with our curated travel packages.</p>
                </div>
                
                <div class="feature-item">
                    <i class="fas fa-hotel"></i>
                    <h3>Quality Accommodations</h3>
                    <p>Stay in hand-picked hotels that ensure comfort and luxury.</p>
                </div>
                
                <div class="feature-item">
                    <i class="fas fa-users"></i>
                    <h3>Expert Guides</h3>
                    <p>Travel with experienced guides who know the destinations inside out.</p>
                </div>
                
                <div class="feature-item">
                    <i class="fas fa-shield-alt"></i>
                    <h3>Safe Travel</h3>
                    <p>Your safety is our priority with comprehensive travel insurance.</p>
                </div>
            </div>
            
            <div class="mission-section">
                <h2>Our Mission</h2>
                <p>To provide unforgettable travel experiences while ensuring customer satisfaction and safety.</p>
            </div>
        </div>
    </div>
    
    <%@include file="components/footer.jsp" %>
</body>
</html>
