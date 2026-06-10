CREATE DATABASE student_system;


USE student_system;

CREATE TABLE students(
    id INT NOT NULL AUTO_INCREMENT,
    student_id VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    PRIMARY KEY(id),
    UNIQUE(student_id)
);

--@block
SELECT 
    id,
    student_id AS 'Student ID',
    password AS 'Password'
FROM students;



INSERT INTO students(student_id, password)
VALUES("Angel Venice Z. Ong","ILOVEYOUin2026");

--@block Clear
SET FOREIGN_KEY_CHECKS = 0;
TRUNCATE TABLE students;
SET FOREIGN_KEY_CHECKS = 1;
