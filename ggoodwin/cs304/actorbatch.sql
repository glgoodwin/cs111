-- Get list of actors with search
select name, birthdate from person where name like "%John%";

-- Get movies given a single name
select movie.title, movie.release from credit inner join movie using (tt) inner join person using (nm) where name = 'Clint Eastwood';

--Get list of movies given a search name
select title, movie.release from movie where title like "%be%";

--Get list of all actors given a single title
select person.name from credit inner join person using (nm) inner join movie using (tt) where title = 'Gone with the wind';
3
--Advanced functionality
select movie.title, movie.release, person.name, person.birthdate from 

