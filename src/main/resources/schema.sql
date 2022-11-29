/*
DROP DATABASE "onlinetraining";
CREATE DATABASE "onlinetraining";

DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS lessons;
DROP TABLE IF EXISTS course_progressions;
DROP TABLE IF EXISTS authorities;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS personal_infos;
DROP TABLE IF EXISTS roles;
DROP TABLE IF EXISTS courses CASCADE;
DROP TABLE IF EXISTS order_status;
*/

CREATE TABLE IF NOT EXISTS  order_status (
	id BIGSERIAL PRIMARY KEY,
	name varchar(20) UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS  courses (
	id BIGSERIAL PRIMARY KEY,
	name varchar(10) UNIQUE NOT NULL,
	course_info text,
	cost NUMERIC(5,2) NOT NULL,
	discount NUMERIC(5,2) DEFAULT 0,
	duration_days int4,
	deleted boolean NOT NULL DEFAULT FALSE
);

CREATE TABLE IF NOT EXISTS  roles (
	id BIGSERIAL PRIMARY KEY,
	name varchar(12) UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS  personal_infos (
	id BIGSERIAL PRIMARY KEY,
    firstName varchar (15) NOT NULL,
    lastName varchar (15) NOT NULL,
    patronymic varchar (15),
    email varchar_ignorecase (35) NOT NULL,
    day_of_birth date,
    deleted boolean NOT NULL DEFAULT FALSE
);

CREATE TABLE IF NOT EXISTS  users (
    id BIGSERIAL PRIMARY KEY,
    username varchar_ignorecase(50) NOT NULL UNIQUE,
    password varchar_ignorecase(75) NOT NULL,
    personal_info_id bigint NOT NULL REFERENCES personal_infos,
    role_id int4 NOT NULL REFERENCES roles,
    enabled boolean NOT NULL DEFAULT TRUE,
    deleted boolean NOT NULL DEFAULT FALSE
);

CREATE TABLE IF NOT EXISTS  authorities (
    username varchar_ignorecase(50) NOT NULL,
    authority varchar_ignorecase(50) NOT NULL,
    constraint fk_authorities_users foreign key(username) references users(username)
);

CREATE UNIQUE index ix_auth_username on authorities (username,authority);

CREATE TABLE IF NOT EXISTS  course_progressions (
	id BIGSERIAL PRIMARY KEY,
	course_id bigint NOT NULL REFERENCES courses,
	user_id bigint NOT NULL REFERENCES users,
	completed_lessons int4 DEFAULT 0
);

CREATE TABLE IF NOT EXISTS  lessons (
	id BIGSERIAL PRIMARY KEY,
    completed boolean NOT NULL DEFAULT FALSE,
    name varchar (30) NOT NULL,
    lesson_info text,
    course_progression_id bigint NOT NULL REFERENCES course_progressions,
    deleted boolean DEFAULT FALSE
);

CREATE TABLE IF NOT EXISTS  orders (
	id BIGSERIAL PRIMARY KEY,
	user_id bigint NOT NULL,
	status_id int4 NOT NULL REFERENCES order_status,
	course_id bigint NOT NULL REFERENCES courses,
	total_cost NUMERIC(5,2) NOT NULL
);