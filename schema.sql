-- ============================================
-- Student Management System - Database Schema
-- ============================================

CREATE DATABASE IF NOT EXISTS student_db;
USE student_db;

CREATE TABLE IF NOT EXISTS students (
    id          INT AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR(100)    NOT NULL,
    age         INT             NOT NULL,
    course      VARCHAR(100)    NOT NULL,
    email       VARCHAR(150)    UNIQUE,
    phone       VARCHAR(20),
    created_at  TIMESTAMP       DEFAULT CURRENT_TIMESTAMP
);

-- Optional: sample data
-- INSERT INTO students (name, age, course, email, phone)
-- VALUES ('John Doe', 20, 'Computer Science', 'john@example.com', '9876543210');
