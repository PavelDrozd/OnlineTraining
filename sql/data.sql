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
VALUES ('ADMIN'),
		('TEACHER'),
		('STUDENT'),
		('USER');

INSERT INTO users (firstName, lastName, age, email, "password", role_id)
VALUES ('Adam', 'Admin', 30, 'admin@gmail.com', 'admin', (SELECT id FROM roles WHERE name = 'ADMIN')),
        ('Teach', 'Teacher', 35, 'teacher@gmail.com', 'teacher', (SELECT id FROM roles WHERE name = 'TEACHER')),
        ('Stud', 'Student', 35, 'student@gmail.com', 'student', (SELECT id FROM roles WHERE name = 'STUDENT')),
        ('Us', 'User', 35, 'user@gmail.com', 'user', (SELECT id FROM roles WHERE name = 'USER')),
        ('Brian', 'Garrett', 22, 'brianGarrett672@mail.ru', '2GV16H', (SELECT id FROM roles WHERE name = 'USER')),
		('Camilla', 'Wright', 23, 'camillaWright52@gmail.com', '85te8l', (SELECT id FROM roles WHERE name = 'USER')),
		('Dwayne', 'Bell', 25, 'dwayneBell5272@yandex.by', 'lOeGA5', (SELECT id FROM roles WHERE name = 'TEACHER')),
		('Norah', 'Gallagher', 33, 'norahGallagher444@yandex.ru', 'umlk4', (SELECT id FROM roles WHERE name = 'USER')),
		('Lucinda', 'Lee', 16, 'lucindaLee111@mail.com', 'vl7fV', (SELECT id FROM roles WHERE name = 'USER')),
		('Nancy', 'Cain', 18, 'nancyCain89@tut.by', 'y2JoT', (SELECT id FROM roles WHERE name = 'USER')),
		('John', 'Lester', 32, 'johnLester632@mail.com', 'IjGLq', (SELECT id FROM roles WHERE name = 'USER')),
		('Dinash', 'Greene', 21, 'dinashGreene312@yandex.ru', 'vNaXK', (SELECT id FROM roles WHERE name = 'TEACHER')),
		('Denis', 'Clark', 20, 'denisClark5231@yandex.by', 'ToiCi', (SELECT id FROM roles WHERE name = 'USER')),
		('Clifton', 'Flynn', 42, 'cliftonFlynn73@tut.by', 'mhkyhT', (SELECT id FROM roles WHERE name = 'USER')),
		('Beryl', 'Willis', 36, 'berylWillis222@yandex.by', 'mbtW6p', (SELECT id FROM roles WHERE name = 'USER')),
		('Hollie', 'Lucas', 31, 'hollieLucas512@yandex.com', 'IQqbQ', (SELECT id FROM roles WHERE name = 'TEACHER')),
		('Mary', 'Scott', 23, 'maryScott2@yandex.ru', '0S5NV', (SELECT id FROM roles WHERE name = 'USER')),
		('Debra', 'Ray', 27, 'debraRay723@gmail.com', 'FFq2Fk', (SELECT id FROM roles WHERE name = 'USER')),
		('Nancy', 'Stevenson', 22, 'nancyStevenson612@gmail.com', 'XdI1Hi', (SELECT id FROM roles WHERE name = 'USER')),
		('Kelley', 'Walker', 25, 'kelleyWalker1@yandex.by', 'W1jl96', (SELECT id FROM roles WHERE name = 'USER')),
		('Timothy', 'Sims', 46, 'timothySims8343@mail.ru', 'c0FDc', (SELECT id FROM roles WHERE name = 'USER')),
		('Patricia', 'Conley', 35, 'patriciaConley51@mail.ru', '3yxCNi', (SELECT id FROM roles WHERE name = 'USER')),
		('Preston', 'Lindsey', 33, 'prestonLindsey123@yandex.by', '9T8R2', (SELECT id FROM roles WHERE name = 'ADMIN')),
		('Juniper', 'Adams', 28, 'juniperAdams125@gmail.com', 'ccDa8O', (SELECT id FROM roles WHERE name = 'ADMIN'));


INSERT INTO orders (user_id, status_id, total_cost)
VALUES ((SELECT id FROM users WHERE email = 'camillaWright52@gmail.com'), (SELECT id FROM order_status WHERE name = 'UNPAID'), 23.9);

INSERT INTO order_info (course_id, order_id, course_price)
VALUES ((SELECT id FROM courses WHERE name = 'MEDICINE'), (SELECT id FROM orders WHERE user_id = (SELECT id FROM users WHERE email = 'camillaWright52@gmail.com')), 22.5);

