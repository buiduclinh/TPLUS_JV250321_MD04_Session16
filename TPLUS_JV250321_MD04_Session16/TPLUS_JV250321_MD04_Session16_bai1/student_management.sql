CREATE DATABASE IF NOT EXISTS student_management;
USE student_management;

CREATE TABLE students
(
    id   INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    age  INT CHECK (age > 0)
);

DELIMITER $$
CREATE PROCEDURE add_student(
    IN in_name VARCHAR(100),
    IN in_age INT
)
BEGIN
    INSERT INTO students(name, age)
    VALUES (in_name, in_age);
END $$
DELIMITER ;