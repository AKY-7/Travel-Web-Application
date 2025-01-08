<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>${package.packageName} - Travel Management</title>
<link rel="stylesheet" href="css/style.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
</head>
<body>
    <%@include file="components/navbar.jsp" %>
    
    <div class="container mt-5">
        <div class="row">
            <div class="col-md-8">
                <div class="card">
                    <img src="images/packages/${package.id}/main.jpg" class="card-img-top" 
                         alt="${package.packageName}">
                    <div class="card-body">
                        <h2 class="card-title">${package.packageName}</h2>
                        <p class="card-text">
                            <i class="fas fa-map-marker-alt"></i> ${package.destination}
                        </p>
                        
                        <div class="package-details mt-4">
                            <h4>Package Details</h4>
                            <p>${package.description}</p>
                            
                            <div class="features mt-4">
                                <h5>What's Included</h5>
                                <ul class="list-unstyled">
                                    <li><i class="fas fa-hotel"></i> Hotel Accommodation</li>
                                    <li><i class="fas fa-utensils"></i> Meals</li>
                                    <li><i class="fas fa-bus"></i> Transportation</li>
                                    <li><i class="fas fa-user-tie"></i> Tour Guide</li>
                                </ul>
                            </div>
                            
                            <div class="itinerary mt-4">
                                <h5>Itinerary</h5>
                                <div class="timeline">
                                    <div class="timeline-item">
                                        <h6>Day 1</h6>
                                        <p>Arrival and Welcome Dinner</p>
                                    </div>
                                    <div class="timeline-item">
                                        <h6>Day 2-4</h6>
                                        <p>Sightseeing and Activities</p>
                                    </div>
                                    <div class="timeline-item">
                                        <h6>Day 5</h6>
                                        <p>Departure</p>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                
                <!-- Reviews Section -->
                <div class="card mt-4">
                    <div class="card-header">
                        <h5>Reviews</h5>
                    </div>
                    <div class="card-body">
                        <c:forEach items="${reviews}" var="review">
                            <div class="review-item border-bottom pb-3 mb-3">
                                <div class="d-flex justify-content-between">
                                    <h6>${review.userName}</h6>
                                    <div class="rating">
                                        <c:forEach begin="1" end="5" var="i">
                                            <i class="fas fa-star ${i <= review.rating ? 'text-warning' : 'text-muted'}"></i>
                                        </c:forEach>
                                    </div>
                                </div>
                                <p class="mb-1">${review.comment}</p>
                                <small class="text-muted">${review.date}</small>
                            </div>
                        </c:forEach>
                        
                        <c:if test="${not empty userObj}">
                            <form action="review/add" method="post" class="mt-4">
                                <input type="hidden" name="packageId" value="${package.id}">
                                <div class="form-group">
                                    <label>Your Rating</label>
                                    <select name="rating" class="form-control" required>
                                        <option value="5">5 Stars</option>
                                        <option value="4">4 Stars</option>
                                        <option value="3">3 Stars</option>
                                        <option value="2">2 Stars</option>
                                        <option value="1">1 Star</option>
                                    </select>
                                </div>
                                <div class="form-group">
                                    <label>Your Review</label>
                                    <textarea name="comment" class="form-control" rows="3" required></textarea>
                                </div>
                                <button type="submit" class="btn btn-primary">Submit Review</button>
                            </form>
                        </c:if>
                    </div>
                </div>
            </div>
            
            <!-- Booking Sidebar -->
            <div class="col-md-4">
                <div class="card booking-card">
                    <div class="card-body">
                        <h4 class="text-center mb-4">Book This Package</h4>
                        <div class="price-tag text-center mb-4">
                            <h3>$${package.price}</h3>
                            <small>per person</small>
                        </div>
                        
                        <form action="booking/create" method="post" id="bookingForm">
                            <input type="hidden" name="packageId" value="${package.id}">
                            
                            <div class="form-group">
                                <label>Travel Date</label>
                                <input type="date" name="travelDate" class="form-control" required
                                       min="${package.startDate}" max="${package.endDate}">
                            </div>
                            
                            <div class="form-group">
                                <label>Number of Persons</label>
                                <input type="number" name="persons" class="form-control" required
                                       min="1" max="${package.maxCapacity}" value="1"
                                       onchange="updateTotalPrice(this.value)">
                            </div>
                            
                            <div class="total-price mb-4">
                                <h5>Total Price: $<span id="totalPrice">${package.price}</span></h5>
                            </div>
                            
                            <c:choose>
                                <c:when test="${empty userObj}">
                                    <a href="login.jsp" class="btn btn-primary btn-block">Login to Book</a>
                                </c:when>
                                <c:otherwise>
                                    <button type="submit" class="btn btn-primary btn-block">Book Now</button>
                                </c:otherwise>
                            </c:choose>
                        </form>
                    </div>
                </div>
                
                <!-- Package Information -->
                <div class="card mt-4">
                    <div class="card-body">
                        <h5>Package Information</h5>
                        <ul class="list-unstyled">
                            <li><i class="far fa-calendar"></i> Duration: 5 Days</li>
                            <li><i class="fas fa-users"></i> Group Size: Max ${package.maxCapacity} people</li>
                            <li><i class="fas fa-language"></i> Language: English</li>
                            <li><i class="fas fa-map"></i> Tour Type: Guided</li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
    </div>
    
    <%@include file="components/footer.jsp" %>
    
    <script>
    function updateTotalPrice(persons) {
        const basePrice = ${package.price};
        const total = basePrice * persons;
        document.getElementById('totalPrice').textContent = total;
    }
    
    document.getElementById('bookingForm').addEventListener('submit', function(e) {
        e.preventDefault();
        
        const persons = this.persons.value;
        const travelDate = new Date(this.travelDate.value);
        const today = new Date();
        
        if (travelDate < today) {
            alert('Please select a future date');
            return;
        }
        
        if (persons > ${package.maxCapacity}) {
            alert('Maximum capacity exceeded');
            return;
        }
        
        this.submit();
    });
    </script>
</body>
</html>
