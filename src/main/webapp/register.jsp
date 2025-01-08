<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Register - Travel Management</title>
<link rel="stylesheet" href="css/style.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
</head>
<body>
    <%@include file="components/navbar.jsp" %>
    
    <div class="container">
        <div class="row justify-content-center mt-5">
            <div class="col-md-6">
                <div class="card">
                    <div class="card-header text-center">
                        <h4>Register</h4>
                    </div>
                    <div class="card-body">
                        <c:if test="${not empty successMsg}">
                            <p class="text-success text-center">${successMsg}</p>
                            <c:remove var="successMsg" scope="session" />
                        </c:if>
                        
                        <c:if test="${not empty errorMsg}">
                            <p class="text-danger text-center">${errorMsg}</p>
                            <c:remove var="errorMsg" scope="session" />
                        </c:if>
                        
                        <form action="register" method="post">
                            <div class="form-group">
                                <label>Full Name</label>
                                <input type="text" name="fullName" class="form-control" required>
                            </div>
                            
                            <div class="form-group">
                                <label>Email Address</label>
                                <input type="email" name="email" class="form-control" required>
                            </div>
                            
                            <div class="form-group">
                                <label>Password</label>
                                <input type="password" name="password" class="form-control" required>
                            </div>
                            
                            <div class="form-group">
                                <label>Phone Number</label>
                                <input type="tel" name="phone" class="form-control" required>
                            </div>
                            
                            <button type="submit" class="btn btn-primary btn-block">Register</button>
                            
                            <div class="text-center mt-3">
                                <p>Already have an account? <a href="login.jsp">Login here</a></p>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
    
    <%@include file="components/footer.jsp" %>
</body>
</html>
