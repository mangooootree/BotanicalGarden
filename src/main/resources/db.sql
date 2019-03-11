create database bg
;

create table plant
(
	id bigserial not null
		constraint plant_pkey
			primary key,
	name varchar(255),
	type varchar(20),
	planted boolean
)
;

create table task
(
	id bigserial not null
		constraint task_pk
			primary key,
	date timestamp,
	plant_id bigint,
	done boolean,
	comment varchar(3000),
	title varchar(255)
)
;

create table usr
(
	id bigserial not null
		constraint usr_pk
			primary key,
	username varchar(255),
	password varchar(255),
	role varchar(20)
)
;


INSERT INTO public.plant (id, name, type, planted) VALUES (2, 'Ель голубая', 'TREE', false);
INSERT INTO public.plant (id, name, type, planted) VALUES (1, 'Ромашка полевая', 'FLOWER', true);
INSERT INTO public.plant (id, name, type, planted) VALUES (3, 'Боярышник обыкновенный', 'SHRUB', true);

INSERT INTO public.task (id, date, plant_id, done, comment, title) VALUES (1, '2019-03-11 00:00:00.000000', 1, true, 'complete', 'Высадка растения');
INSERT INTO public.task (id, date, plant_id, done, comment, title) VALUES (2, '2019-03-11 00:00:00.000000', 2, false, '    Криво посажена! Пересадить!', 'Высадка растения');
INSERT INTO public.task (id, date, plant_id, done, comment, title) VALUES (3, '2019-03-11 00:00:00.000000', 3, true, 'complete', 'Высадка растения');
INSERT INTO public.task (id, date, plant_id, done, comment, title) VALUES (10, '2019-03-11 13:14:55.320000', 3, false, null, 'Лечение растения');

INSERT INTO public.usr (id, username, password, role) VALUES (1, 'admin', '123', 'ADMIN');
INSERT INTO public.usr (id, username, password, role) VALUES (2, 'usr', '123', 'USER');