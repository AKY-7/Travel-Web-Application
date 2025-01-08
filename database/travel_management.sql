-- Create database if not exists
CREATE DATABASE IF NOT EXISTS travel_management;
USE travel_management;

-- Users table
CREATE TABLE IF NOT EXISTS users (
    id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    full_name VARCHAR(100) NOT NULL,
    role VARCHAR(20) DEFAULT 'USER',
    phone VARCHAR(20),
    address TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Travel packages table
CREATE TABLE IF NOT EXISTS travel_packages (
    id INT PRIMARY KEY AUTO_INCREMENT,
    package_name VARCHAR(100) NOT NULL,
    destination VARCHAR(100) NOT NULL,
    description TEXT,
    price DECIMAL(10,2) NOT NULL,
    duration INT NOT NULL,
    max_capacity INT NOT NULL,
    available_seats INT NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    image_url VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Bookings table
CREATE TABLE IF NOT EXISTS bookings (
    id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    package_id INT NOT NULL,
    booking_date DATE NOT NULL,
    travel_date DATE NOT NULL,
    number_of_persons INT NOT NULL,
    total_price DECIMAL(10,2) NOT NULL,
    status VARCHAR(20) DEFAULT 'PENDING',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (package_id) REFERENCES travel_packages(id)
);

-- Payments table
CREATE TABLE IF NOT EXISTS payments (
    id INT PRIMARY KEY AUTO_INCREMENT,
    booking_id INT NOT NULL,
    amount DECIMAL(10,2) NOT NULL,
    payment_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    payment_status VARCHAR(20) NOT NULL,
    payment_method VARCHAR(50),
    transaction_id VARCHAR(100),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (booking_id) REFERENCES bookings(id)
);

-- Reviews table
CREATE TABLE IF NOT EXISTS reviews (
    id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    package_id INT NOT NULL,
    rating INT NOT NULL CHECK (rating >= 1 AND rating <= 5),
    comment TEXT,
    review_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (package_id) REFERENCES travel_packages(id)
);

-- Insert default admin user
INSERT INTO users (username, password, email, full_name, role, phone)
VALUES ('admin', 'admin123', 'admin@travel.com', 'System Administrator', 'ADMIN', '1234567890')
ON DUPLICATE KEY UPDATE username=username;

-- Insert sample travel packages
INSERT INTO travel_packages (package_name, destination, description, price, duration, max_capacity, available_seats, start_date, end_date, image_url)
VALUES 
('Goa Beach Paradise', 'Goa, India', 'Experience the beautiful beaches and nightlife of Goa', 25000.00, 5, 20, 20, '2024-02-01', '2024-02-05', '/images/goa.jpg'),
('Kerala Backwaters', 'Kerala, India', 'Explore the serene backwaters and lush greenery', 35000.00, 7, 15, 15, '2024-02-15', '2024-02-21', '/images/kerala.jpg'),
('Manali Adventure', 'Manali, India', 'Adventure sports and mountain exploration', 28000.00, 6, 25, 25, '2024-03-01', '2024-03-06', '/images/manali.jpg')
ON DUPLICATE KEY UPDATE package_name=package_name;
