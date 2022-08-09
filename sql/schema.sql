/*
DROP DATABASE "onlinetraining";
CREATE DATABASE "onlinetraining";

DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS roles;
DROP TABLE IF EXISTS order_info;
DROP TABLE IF EXISTS orders CASCADE;
DROP TABLE IF EXISTS courses CASCADE;
DROP TABLE IF EXISTS order_status CASCADE;
 */

create TABLE IF NOT EXISTS  order_status (
	id BIGSERIAL PRIMARY KEY,
	name varchar(20) UNIQUE NOT NULL
);

create TABLE IF NOT EXISTS  courses (
	id BIGSERIAL PRIMARY KEY,
	"name" varchar(10) UNIQUE NOT NULL,
	"cost" NUMERIC(5,2) NOT NULL,
	duration_days int4 NOT NULL,
	deleted boolean NOT NULL DEFAULT FALSE
);

create TABLE IF NOT EXISTS  orders (
	id BIGSERIAL PRIMARY KEY,
	user_id bigint NOT NULL,
	status_id bigint NOT NULL REFERENCES order_status,
	total_cost NUMERIC(5,2) NOT NULL,
	deleted boolean NOT NULL DEFAULT FALSE
);

create TABLE IF NOT EXISTS  order_info (
	id BIGSERIAL PRIMARY KEY,
	course_id bigint NOT NULL REFERENCES courses,
	order_id bigint NOT NULL REFERENCES orders,
	course_price NUMERIC(5,2) NOT NULL,
	deleted boolean NOT NULL DEFAULT FALSE
);

create TABLE IF NOT EXISTS  roles (
	id BIGSERIAL PRIMARY KEY,
	name varchar(10) UNIQUE NOT NULL
);

create TABLE IF NOT EXISTS  users (
    id BIGSERIAL PRIMARY KEY,
    firstName varchar (15) NOT NULL,
    lastName varchar (15) NOT NULL,
    "age" SMALLINT,
    email varchar (30) NOT NULL,
    password varchar (30) NOT NULL,
    role_id bigint NOT NULL REFERENCES roles,
    deleted boolean NOT NULL DEFAULT FALSE

);
