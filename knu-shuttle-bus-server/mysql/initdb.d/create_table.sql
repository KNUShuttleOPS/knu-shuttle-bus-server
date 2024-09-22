CREATE TABLE user (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    user_name VARCHAR(20),
    user_password VARCHAR(60),
    device_id VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE bus (
    bus_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    line VARCHAR(10) NOT NULL,
    round INT NOT NULL,
    heading VARCHAR(255),
    operation_info BOOLEAN,
    CONSTRAINT fk_bus_user FOREIGN KEY (user_id) REFERENCES user(user_id),
    INDEX (line)
);

CREATE TABLE passenger (
    passenger_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    student_id VARCHAR(10) NOT NULL UNIQUE,
    station INT,
    alarm BOOLEAN,
    fcmtoken VARCHAR(255),
    CONSTRAINT fk_passenger_user FOREIGN KEY (user_id) REFERENCES user(user_id),
    INDEX (station),
    INDEX (student_id)
);
