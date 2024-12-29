package com.users.filter;

import com.users.model.User;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AuthenticationFilter implements Filter {
    
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession(false);

        String requestURI = httpRequest.getRequestURI();
        
        // Check if user is logged in
        if (session == null || session.getAttribute("user") == null) {
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/login");
            return;
        }

        // Get user role and check permissions
        User user = (User) session.getAttribute("user");
        String userRole = user.getRole().toString();

        // Check access permissions based on URL and role
        if (requestURI.contains("/admin/") && !userRole.equals("Admin")) {
            httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN, "Access Denied");
            return;
        }
        if (requestURI.contains("/agent/") && !userRole.equals("TravelAgent")) {
            httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN, "Access Denied");
            return;
        }
        if (requestURI.contains("/traveler/") && !userRole.equals("Traveler")) {
            httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN, "Access Denied");
            return;
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
}
