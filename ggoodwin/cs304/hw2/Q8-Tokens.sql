--Gabe Goodwin & Johanna Okerlund

use ggoodwin_db;

select names.firstname, names.lastname, address.street, address.city, address.state from names inner join address using (id) group by (id) having count(address.state)=1;