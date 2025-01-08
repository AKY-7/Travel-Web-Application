package com.travel.servlet;

import com.travel.dao.UserDAO;
import com.travel.model.User;
import com.travel.util.DatabaseConnection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/user/*")
public class UserServlet extends HttpServlet {
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String path = request.getPathInfo();
        HttpSession session = request.getSession();
        
        try {
            UserDAO dao = new UserDAO(DatabaseConnection.getConnection());
            
            if ("/register".equals(path)) {
                String fullName = request.getParameter("fullName");
                String email = request.getParameter("email");
                String password = request.getParameter("password");
                String phone = request.getParameter("phone");
                
                // Check if email already exists
                if (dao.checkEmail(email)) {
                    session.setAttribute("errorMsg", "Email already exists");
                    response.sendRedirect("register.jsp");
                    return;
                }
                
                User user = new User(fullName, email, password, phone);
                if (dao.registerUser(user)) {
                    session.setAttribute("successMsg", "Registration Successful");
                    response.sendRedirect("login.jsp");
                } else {
                    session.setAttribute("errorMsg", "Something went wrong");
                    response.sendRedirect("register.jsp");
                }
            } 
            else if ("/login".equals(path)) {
                String email = request.getParameter("email");
                String password = request.getParameter("password");
                
                User user = dao.loginUser(email, password);
                if (user != null) {
                    session.setAttribute("userObj", user);
                    response.sendRedirect("index.jsp");
                } else {
                    session.setAttribute("errorMsg", "Invalid email or password");
                    response.sendRedirect("login.jsp");
                }
            }
            else if ("/update".equals(path)) {
                User user = (User) session.getAttribute("userObj");
                if (user == null) {
                    response.sendRedirect("login.jsp");
                    return;
                }
                
                String fullName = request.getParameter("fullName");
                String email = request.getParameter("email");
                String phone = request.getParameter("phone");
                
                user.setFullName(fullName);
                user.setEmail(email);
                user.setPhone(phone);
                
                if (dao.updateProfile(user)) {
                    session.setAttribute("successMsg", "Profile Updated Successfully");
                } else {
                    session.setAttribute("errorMsg", "Something went wrong");
                }
                response.sendRedirect("profile.jsp");
            }
            else if ("/logout".equals(path)) {
                session.removeAttribute("userObj");
                session.setAttribute("successMsg", "Logout Successful");
                response.sendRedirect("login.jsp");
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("errorMsg", "Server Error");
            response.sendRedirect("error.jsp");
        }
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        doPost(request, response);
    }
}
