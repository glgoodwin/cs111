create table phone(
	id int not null references names(id),
	areacode int,
	number int,
	type varchar(50));

create table names(
	id int auto_increment not null primary key,
	firstname varchar(50),
	lastname varchar(50));

create table address(
	id int not null references names(id),
	street varchar(50),
	state varchar(50),
	city varchar(50),
	type varchar(50));




	
 