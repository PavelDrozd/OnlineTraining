/*
TRUNCATE TABLE possibilities CASCADE;
TRUNCATE TABLE orders CASCADE;
TRUNCATE TABLE lessons CASCADE;
TRUNCATE TABLE course_progressions CASCADE;
TRUNCATE TABLE users CASCADE;
TRUNCATE TABLE roles CASCADE;
TRUNCATE TABLE courses CASCADE;
TRUNCATE TABLE order_status CASCADE;
*/


INSERT INTO order_status (name)
VALUES ('UNPAID'),
        ('CANCELED'),
        ('PAYED');

INSERT INTO courses (name, course_info, cost, discount, duration_days)
VALUES ('HISTORY', 'History info', 25.2, 0, 90),
		('BIOLOGICAL', 'Biological info', 33.2, 0, 45),
		('CHEMISTRY', 'Chemistry info', 35.1, 15.1, 60),
		('MEDICINE', 'Medicine info', 57.6, 27.6, 75);

INSERT INTO roles (name)
VALUES ('USER'),
		('STUDENT'),
		('TUTOR'),
		('ADMIN'),
		('SUPERADMIN');

INSERT INTO personal_infos (firstName, lastName, email)
VALUES('SuperAdam', 'SuperAdmin', 'superadmin@gmail.com'),
        ('Adam', 'Admin', 'admin@gmail.com'),
        ('Tutor', 'Tutor', 'tutor@gmail.com'),
        ('Stud1', 'Student1', 'student1@gmail.com'),
        ('Stud2', 'Student2', 'student2@gmail.com'),
        ('Stud3', 'Student3', 'student3@gmail.com'),
        ('Stud4', 'Student4', 'student4@gmail.com'),
        ('Us', 'User', 'user@gmail.com');

INSERT INTO users (username, password, personal_info_id, role_id)
VALUES('superadmin', 'superadmin', (SELECT id FROM personal_infos WHERE email = 'superadmin@gmail.com'), (SELECT id FROM roles WHERE name = 'SUPERADMIN')),
        ('admin', 'admin', (SELECT id FROM personal_infos WHERE email = 'admin@gmail.com'), (SELECT id FROM roles WHERE name = 'ADMIN')),
        ('tutor', 'tutor', (SELECT id FROM personal_infos WHERE email = 'tutor@gmail.com'), (SELECT id FROM roles WHERE name = 'TUTOR')),
        ('student1', 'student1', (SELECT id FROM personal_infos WHERE email = 'student1@gmail.com'), (SELECT id FROM roles WHERE name = 'STUDENT')),
        ('student2', 'student2', (SELECT id FROM personal_infos WHERE email = 'student2@gmail.com'), (SELECT id FROM roles WHERE name = 'STUDENT')),
        ('student3', 'student3', (SELECT id FROM personal_infos WHERE email = 'student3@gmail.com'), (SELECT id FROM roles WHERE name = 'STUDENT')),
        ('student4', 'student4', (SELECT id FROM personal_infos WHERE email = 'student4@gmail.com'), (SELECT id FROM roles WHERE name = 'STUDENT')),
        ('user', 'user', (SELECT id FROM personal_infos WHERE email = 'user@gmail.com'), (SELECT id FROM roles WHERE name = 'USER'));

INSERT INTO authorities (username, authority)
VALUES ((SELECT username FROM users WHERE personal_info_id = (SELECT id FROM personal_infos WHERE email = 'superadmin@gmail.com')),  'WATCH_ALL'),
        ((SELECT username FROM users WHERE personal_info_id = (SELECT id FROM personal_infos WHERE email = 'superadmin@gmail.com')), 'EDIT_ALL'),
        ((SELECT username FROM users WHERE personal_info_id = (SELECT id FROM personal_infos WHERE email = 'superadmin@gmail.com')), 'DELETE_ALL'),
        ((SELECT username FROM users WHERE personal_info_id = (SELECT id FROM personal_infos WHERE email = 'admin@gmail.com')), 'WATCH_ALL'),
        ((SELECT username FROM users WHERE personal_info_id = (SELECT id FROM personal_infos WHERE email = 'admin@gmail.com')), 'EDIT_ALL'),
        ((SELECT username FROM users WHERE personal_info_id = (SELECT id FROM personal_infos WHERE email = 'admin@gmail.com')), 'DELETE_ALL'),
        ((SELECT username FROM users WHERE personal_info_id = (SELECT id FROM personal_infos WHERE email = 'tutor@gmail.com')), 'WATCH_ALL'),
        ((SELECT username FROM users WHERE personal_info_id = (SELECT id FROM personal_infos WHERE email = 'student1@gmail.com')), 'WATCH_ALL'),
        ((SELECT username FROM users WHERE personal_info_id = (SELECT id FROM personal_infos WHERE email = 'student2@gmail.com')), 'WATCH_ALL'),
        ((SELECT username FROM users WHERE personal_info_id = (SELECT id FROM personal_infos WHERE email = 'student3@gmail.com')), 'WATCH_ALL'),
        ((SELECT username FROM users WHERE personal_info_id = (SELECT id FROM personal_infos WHERE email = 'student4@gmail.com')), 'WATCH_ALL'),
        ((SELECT username FROM users WHERE personal_info_id = (SELECT id FROM personal_infos WHERE email = 'user@gmail.com')), 'WATCH_ALL');

INSERT INTO course_progressions (course_id, user_id, completed_lessons)
VALUES ((SELECT id FROM courses WHERE name = 'HISTORY'), (SELECT id FROM users WHERE personal_info_id = (SELECT id FROM personal_infos WHERE email = 'student1@gmail.com')), 0),
        ((SELECT id FROM courses WHERE name = 'BIOLOGICAL'), (SELECT id FROM users WHERE personal_info_id = (SELECT id FROM personal_infos WHERE email = 'student2@gmail.com')), 3),
        ((SELECT id FROM courses WHERE name = 'CHEMISTRY'), (SELECT id FROM users WHERE personal_info_id = (SELECT id FROM personal_infos WHERE email = 'student3@gmail.com')), 4),
        ((SELECT id FROM courses WHERE name = 'MEDICINE'), (SELECT id FROM users WHERE personal_info_id = (SELECT id FROM personal_infos WHERE email = 'student4@gmail.com')), 5);

INSERT INTO lessons (name, lesson_info, course_progression_id)
VALUES ('lesson1', 'lesson1_info', (SELECT id FROM course_progressions WHERE course_id = (SELECT id FROM courses WHERE name = 'BIOLOGICAL'))),
        ('lesson2', 'lesson2_info', (SELECT id FROM course_progressions WHERE course_id = (SELECT id FROM courses WHERE name = 'BIOLOGICAL'))),
        ('lesson3', 'lesson3_info', (SELECT id FROM course_progressions WHERE course_id = (SELECT id FROM courses WHERE name = 'BIOLOGICAL'))),
        ('lesson4', 'lesson4_info', (SELECT id FROM course_progressions WHERE course_id = (SELECT id FROM courses WHERE name = 'BIOLOGICAL'))),
        ('lesson5', 'lesson5_info', (SELECT id FROM course_progressions WHERE course_id = (SELECT id FROM courses WHERE name = 'BIOLOGICAL'))),
        ('lesson1', 'lesson1_info', (SELECT id FROM course_progressions WHERE course_id = (SELECT id FROM courses WHERE name = 'CHEMISTRY'))),
        ('lesson2', 'lesson2_info', (SELECT id FROM course_progressions WHERE course_id = (SELECT id FROM courses WHERE name = 'CHEMISTRY'))),
        ('lesson3', 'lesson3_info', (SELECT id FROM course_progressions WHERE course_id = (SELECT id FROM courses WHERE name = 'CHEMISTRY'))),
        ('lesson4', 'lesson4_info', (SELECT id FROM course_progressions WHERE course_id = (SELECT id FROM courses WHERE name = 'CHEMISTRY'))),
        ('lesson5', 'lesson5_info', (SELECT id FROM course_progressions WHERE course_id = (SELECT id FROM courses WHERE name = 'CHEMISTRY'))),
        ('lesson6', 'lesson6_info', (SELECT id FROM course_progressions WHERE course_id = (SELECT id FROM courses WHERE name = 'CHEMISTRY'))),
        ('lesson7', 'lesson7_info', (SELECT id FROM course_progressions WHERE course_id = (SELECT id FROM courses WHERE name = 'CHEMISTRY'))),
        ('lesson1', 'lesson1_info', (SELECT id FROM course_progressions WHERE course_id = (SELECT id FROM courses WHERE name = 'MEDICINE'))),
        ('lesson2', 'lesson2_info', (SELECT id FROM course_progressions WHERE course_id = (SELECT id FROM courses WHERE name = 'MEDICINE'))),
        ('lesson3', 'lesson3_info', (SELECT id FROM course_progressions WHERE course_id = (SELECT id FROM courses WHERE name = 'MEDICINE'))),
        ('lesson4', 'lesson4_info', (SELECT id FROM course_progressions WHERE course_id = (SELECT id FROM courses WHERE name = 'MEDICINE'))),
        ('lesson5', 'lesson5_info', (SELECT id FROM course_progressions WHERE course_id = (SELECT id FROM courses WHERE name = 'MEDICINE'))),
        ('lesson6', 'lesson6_info', (SELECT id FROM course_progressions WHERE course_id = (SELECT id FROM courses WHERE name = 'MEDICINE'))),
        ('lesson7', 'lesson7_info', (SELECT id FROM course_progressions WHERE course_id = (SELECT id FROM courses WHERE name = 'MEDICINE'))),
        ('lesson8', 'lesson8_info', (SELECT id FROM course_progressions WHERE course_id = (SELECT id FROM courses WHERE name = 'MEDICINE')));

INSERT INTO orders (user_id, status_id, course_id, total_cost)
VALUES ((SELECT id FROM users WHERE personal_info_id = (SELECT id FROM personal_infos WHERE email = 'student1@gmail.com')), (SELECT id FROM order_status WHERE name = 'UNPAID'), (SELECT id FROM courses WHERE name = 'HISTORY'), 25.2),
        ((SELECT id FROM users WHERE personal_info_id = (SELECT id FROM personal_infos WHERE email = 'student2@gmail.com')), (SELECT id FROM order_status WHERE name = 'PAYED'), (SELECT id FROM courses WHERE name = 'BIOLOGICAL'), 33.2),
        ((SELECT id FROM users WHERE personal_info_id = (SELECT id FROM personal_infos WHERE email = 'student3@gmail.com')), (SELECT id FROM order_status WHERE name = 'PAYED'), (SELECT id FROM courses WHERE name = 'CHEMISTRY'), 20.0),
        ((SELECT id FROM users WHERE personal_info_id = (SELECT id FROM personal_infos WHERE email = 'student4@gmail.com')), (SELECT id FROM order_status WHERE name = 'PAYED'), (SELECT id FROM courses WHERE name = 'MEDICINE'), 30.0);
