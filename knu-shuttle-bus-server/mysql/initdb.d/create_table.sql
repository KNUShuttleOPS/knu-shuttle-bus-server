CREATE TABLE user (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    device_id VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE bus (
    bus_id INT AUTO_INCREMENT PRIMARY KEY,
    device_id VARCHAR(100) NOT NULL UNIQUE, 
    heading VARCHAR(255),
    latitude DECIMAL(10,7),
    longitude DECIMAL(11,7),
    line VARCHAR(10) NOT NULL,
    operation_info BOOLEAN, 
    FOREIGN KEY (device_id) REFERENCES user(device_id),
    INDEX (line, heading)
);

CREATE TABLE passenger (
    passenger_id INT AUTO_INCREMENT PRIMARY KEY,
    device_id VARCHAR(100) NOT NULL UNIQUE,
    student_id VARCHAR(10) NOT NULL UNIQUE,
    station VARCHAR(10),
    fcmtoken VARCHAR(255) NOT NULL,
    FOREIGN KEY (device_id) REFERENCES user(device_id),
    INDEX (student_id, station)
);