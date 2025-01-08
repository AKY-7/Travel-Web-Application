package com.travel.servlet;

import com.travel.dao.PaymentDAO;
import com.travel.dao.BookingDAO;
import com.travel.model.Payment;
import com.travel.model.Booking;
import com.travel.util.DatabaseConnection;
import com.stripe.Stripe;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import com.google.gson.Gson;

@WebServlet("/payment/*")
public class PaymentServlet extends HttpServlet {
    private static final String STRIPE_SECRET_KEY = "your_stripe_secret_key"; // Replace with your actual key
    private Gson gson = new Gson();

    @Override
    public void init() throws ServletException {
        Stripe.apiKey = STRIPE_SECRET_KEY;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String pathInfo = request.getPathInfo();
        
        try {
            if ("/create-payment-intent".equals(pathInfo)) {
                createPaymentIntent(request, response);
            }
            else if ("/confirm-payment".equals(pathInfo)) {
                confirmPayment(request, response);
            }
            else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
            sendErrorResponse(response, "Payment processing failed: " + e.getMessage());
        }
    }

    private void createPaymentIntent(HttpServletRequest request, HttpServletResponse response) 
            throws IOException {
        try {
            // Get booking details
            int bookingId = Integer.parseInt(request.getParameter("bookingId"));
            BookingDAO bookingDao = new BookingDAO(DatabaseConnection.getConnection());
            Booking booking = bookingDao.getBooking(bookingId);

            if (booking == null) {
                sendErrorResponse(response, "Booking not found");
                return;
            }

            // Create a PaymentIntent with the order amount and currency
            PaymentIntentCreateParams createParams = PaymentIntentCreateParams.builder()
                .setAmount((long) (booking.getTotalPrice() * 100)) // Convert to cents
                .setCurrency("usd")
                .setAutomaticPaymentMethods(
                    PaymentIntentCreateParams.AutomaticPaymentMethods.builder()
                        .setEnabled(true)
                        .build()
                )
                .putMetadata("booking_id", String.valueOf(bookingId))
                .build();

            // Create PaymentIntent
            PaymentIntent intent = PaymentIntent.create(createParams);

            // Create initial payment record
            Payment payment = new Payment(bookingId, booking.getTotalPrice(), "card");
            payment.setPaymentGateway("Stripe");
            payment.setTransactionId(intent.getId());
            
            PaymentDAO paymentDao = new PaymentDAO(DatabaseConnection.getConnection());
            paymentDao.createPayment(payment);

            // Send PaymentIntent details to client
            Map<String, String> response_data = new HashMap<>();
            response_data.put("clientSecret", intent.getClientSecret());
            
            response.setContentType("application/json");
            response.getWriter().write(gson.toJson(response_data));

        } catch (Exception e) {
            e.printStackTrace();
            sendErrorResponse(response, "Failed to create payment intent: " + e.getMessage());
        }
    }

    private void confirmPayment(HttpServletRequest request, HttpServletResponse response) 
            throws IOException {
        try {
            String paymentIntentId = request.getParameter("payment_intent");
            PaymentIntent intent = PaymentIntent.retrieve(paymentIntentId);
            
            // Update payment record
            PaymentDAO paymentDao = new PaymentDAO(DatabaseConnection.getConnection());
            String status = "SUCCESS";
            String errorMessage = null;
            
            if ("succeeded".equals(intent.getStatus())) {
                // Update booking status
                int bookingId = Integer.parseInt(intent.getMetadata().get("booking_id"));
                BookingDAO bookingDao = new BookingDAO(DatabaseConnection.getConnection());
                bookingDao.updateBookingStatus(bookingId, "CONFIRMED");
            } else {
                status = "FAILED";
                errorMessage = "Payment failed: " + intent.getLastPaymentError().getMessage();
            }
            
            paymentDao.updatePaymentStatus(
                Integer.parseInt(request.getParameter("payment_id")),
                status,
                paymentIntentId,
                errorMessage
            );

            Map<String, Object> result = new HashMap<>();
            result.put("success", "SUCCESS".equals(status));
            result.put("status", status);
            if (errorMessage != null) {
                result.put("message", errorMessage);
            }
            
            response.setContentType("application/json");
            response.getWriter().write(gson.toJson(result));

        } catch (Exception e) {
            e.printStackTrace();
            sendErrorResponse(response, "Payment confirmation failed: " + e.getMessage());
        }
    }

    private void sendErrorResponse(HttpServletResponse response, String message) throws IOException {
        response.setContentType("application/json");
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("success", false);
        errorResponse.put("message", message);
        response.getWriter().write(gson.toJson(errorResponse));
    }
}
