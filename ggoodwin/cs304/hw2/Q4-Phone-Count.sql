--Gabe Goodwin and Johanna Okerlund

use ggoodwin_db;

select names.firstname, names.lastname, count(phone.number) from names inner join phone using (id) group by id;
