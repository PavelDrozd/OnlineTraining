/*
TRUNCATE TABLE order_info CASCADE;
TRUNCATE TABLE orders CASCADE;
TRUNCATE TABLE users CASCADE;
TRUNCATE TABLE roles CASCADE;
TRUNCATE TABLE courses CASCADE;
TRUNCATE TABLE order_status CASCADE;
*/


INSERT INTO order_status ("name")
VALUES ('UNPAID'),
        ('CANCELED'),
        ('PAYED');

INSERT INTO courses ("name", "cost", duration_days)
VALUES ('HISTORY', 25.2, 90),
		('BIOLOGICAL', 33.2, 45),
		('CHEMISTRY', 35.1, 60),
		('MEDICINE', 57.6, 75);

INSERT INTO roles ("name")
VALUES ('USER'),
		('STUDENT'),
		('TEACHER'),
		('ADMIN');

INSERT INTO users (firstName, lastName, age, email, "password", role_id)
VALUES ('Adam', 'Admin', 30, 'admin@gmail.com', 'D033E22AE348AEB5660FC2140AEC35850C4DA997', (SELECT id FROM roles WHERE name = 'ADMIN')),
        ('Teach', 'Teacher', 35, 'teacher@gmail.com', 'D033E22AE348AEB5660FC2140AEC35850C4DA997', (SELECT id FROM roles WHERE name = 'TEACHER')),
        ('Stud', 'Student', 35, 'student@gmail.com', 'D033E22AE348AEB5660FC2140AEC35850C4DA997', (SELECT id FROM roles WHERE name = 'STUDENT')),
        ('Us', 'User', 35, 'user@gmail.com', 'D033E22AE348AEB5660FC2140AEC35850C4DA997', (SELECT id FROM roles WHERE name = 'USER'));


INSERT INTO orders (user_id, status_id, total_cost)
VALUES ((SELECT id FROM users WHERE email = 'student@gmail.com'), (SELECT id FROM order_status WHERE name = 'UNPAID'), 23.9);

INSERT INTO order_info (course_id, order_id, course_price)
VALUES ((SELECT id FROM courses WHERE name = 'MEDICINE'), (SELECT id FROM orders WHERE user_id = (SELECT id FROM users WHERE email = 'student@gmail.com')), 22.5);

