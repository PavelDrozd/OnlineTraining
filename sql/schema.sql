/*
DROP DATABASE "onlinetraining";
CREATE DATABASE "onlinetraining";

DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS lessons;
DROP TABLE IF EXISTS course_progressions;
DROP TABLE IF EXISTS possibilities;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS personal_infos;
DROP TABLE IF EXISTS roles;
DROP TABLE IF EXISTS courses CASCADE;
DROP TABLE IF EXISTS order_status;
*/

create TABLE IF NOT EXISTS  order_status (
	id BIGSERIAL PRIMARY KEY,
	"name" varchar(20) UNIQUE NOT NULL
);

create TABLE IF NOT EXISTS  courses (
	id BIGSERIAL PRIMARY KEY,
	"name" varchar(10) UNIQUE NOT NULL,
	course_info text,
	"cost" NUMERIC(5,2) NOT NULL,
	discount NUMERIC(5,2) DEFAULT 0,
	duration_days int4,
	deleted boolean DEFAULT FALSE
);

create TABLE IF NOT EXISTS  roles (
	id BIGSERIAL PRIMARY KEY,
	name varchar(12) UNIQUE NOT NULL
);

create TABLE IF NOT EXISTS  personal_infos (
	id BIGSERIAL PRIMARY KEY,
    firstName varchar (15) NOT NULL,
    lastName varchar (15) NOT NULL,
    patronymic varchar (15),
    email varchar (35) NOT NULL,
    day_of_birth date,
    deleted boolean DEFAULT FALSE
);

create TABLE IF NOT EXISTS  users (
    id BIGSERIAL PRIMARY KEY,
    login varchar (35) NOT NULL,
    password varchar (50) NOT NULL,
    personal_info_id bigint REFERENCES personal_infos,
    role_id int4 NOT NULL REFERENCES roles,
    deleted boolean DEFAULT FALSE
);

create TABLE IF NOT EXISTS  possibilities (
	id BIGSERIAL PRIMARY KEY,
    user_id bigint NOT NULL REFERENCES users,

	watch_users boolean DEFAULT FALSE,
	delete_users boolean DEFAULT FALSE,
	change_role_users boolean DEFAULT FALSE,
	change_users_parameters boolean DEFAULT FALSE,

	create_courses boolean DEFAULT FALSE,
	delete_courses boolean DEFAULT FALSE,
	change_courses boolean DEFAULT FALSE,
	watch_deleted_courses boolean DEFAULT FALSE,

	watch_users_orders boolean DEFAULT FALSE,
	change_orders_status boolean DEFAULT FALSE,

	create_lessons boolean DEFAULT FALSE,
	watch_lessons boolean DEFAULT FALSE,
	watch_deleted_lessons boolean DEFAULT FALSE,
	delete_lessons boolean DEFAULT FALSE,
	change_lessons boolean DEFAULT FALSE
);

create TABLE IF NOT EXISTS  course_progressions (
	id BIGSERIAL PRIMARY KEY,
	course_id bigint NOT NULL REFERENCES courses,
	user_id bigint NOT NULL REFERENCES users,
	completed_lessons int4 DEFAULT 0
);

create TABLE IF NOT EXISTS  lessons (
	id BIGSERIAL PRIMARY KEY,
    completed boolean DEFAULT FALSE,
    name varchar (30) NOT NULL,
    lesson_info text,
    course_progression_id bigint NOT NULL REFERENCES course_progressions,
    deleted boolean DEFAULT FALSE
);

create TABLE IF NOT EXISTS  orders (
	id BIGSERIAL PRIMARY KEY,
	user_id bigint NOT NULL,
	status_id int4 NOT NULL REFERENCES order_status,
	course_id bigint NOT NULL REFERENCES courses,
	total_cost NUMERIC(5,2) NOT NULL
);