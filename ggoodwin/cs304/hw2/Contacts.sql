--Gabe Goodwin and Johanna Okerlund

use ggoodwin_db;

drop table if exists phone;
drop table if exists address;
drop table if exists names;


--Creates tables
create table names(
	id int auto_increment not null primary key,
	firstname varchar(50),
	lastname varchar(50))
	Engine = InnoDB;

create table phone(
	id int not null,
	areacode int,
	number int,
	type varchar(50),
	Index (id),
	foreign key (id) references names (id) on delete cascade)
	engine = InnoDB;


create table address(
	id int not null,
	street varchar(50),
	state varchar(50),
	city varchar(50),
	type varchar(50),
	Index (id),
	foreign key (id) references names (id) on delete cascade)
	engine = InnoDB;

--Loads data into tables
load data LOCAL infile '~/cs304/hw2/Name.txt' into table names fields terminated by ',' lines terminated by '\r\n';
load data LOCAL infile '~/cs304/hw2/Phone.txt' into table phone fields terminated by ',' lines terminated by '\r\n';
load data LOCAL infile '~/cs304/hw2/Address.txt' into table address fields terminated by ',' lines terminated by '\r\n';