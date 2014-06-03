--Gabe Goodwin and Johanna Okerlund

use ggoodwin_db;

select names.firstname, names.lastname, phone.areacode, phone.number from names inner join phone using(id) where phone.areacode = 617;