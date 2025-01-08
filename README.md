# Travel Management System

A comprehensive travel package booking system built with Java, JSP, and MySQL.

## Features

### User Features
- User registration and authentication
- Browse travel packages
- View package details with images and itinerary
- Book travel packages
- Secure payment processing with Stripe
- View booking history
- Submit package reviews and ratings
- Profile management

### Admin Features
- Admin dashboard with statistics
- Package management (CRUD operations)
- Booking management
- User management
- Review moderation
- Payment tracking
- Analytics and reports

## Technology Stack

### Backend
- Java 11
- Servlets & JSP
- MySQL Database
- Maven

### Frontend
- HTML5
- CSS3
- JavaScript
- Bootstrap
- Chart.js for analytics

### Payment Integration
- Stripe Payment Gateway

## Setup Instructions

1. Clone the repository:
```bash
git clone https://github.com/yourusername/travel-management-system.git
cd travel-management-system
```

2. Configure MySQL database:
- Create a database named 'travel_management'
- Run the SQL scripts from `src/main/resources/schema.sql`

3. Configure database connection:
- Copy `src/main/resources/database.properties.example` to `database.properties`
- Update with your database credentials

4. Configure Stripe:
- Sign up for a Stripe account
- Add your Stripe secret key in `PaymentServlet.java`
- Add your Stripe public key in the frontend JavaScript

5. Build the project:
```bash
mvn clean install
```

6. Deploy to Tomcat:
- Copy the WAR file from `target/travel-management-system.war` to Tomcat's webapps directory
- Start Tomcat server

## Database Schema

The system uses the following main tables:
- users
- travel_packages
- bookings
- payments
- reviews

## Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Contact

Your Name - your.email@example.com
Project Link: https://github.com/yourusername/travel-management-system
