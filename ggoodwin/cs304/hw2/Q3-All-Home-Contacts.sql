--Gabe Goodwin and Johanna Okerlund
use ggoodwin_db;

select names.firstname,names.lastname,address.street,address.state,address.city,phone.areacode,phone.number from names inner join address using(id) inner join phone using (id) where address.type='"Home"'and phone.type = '"Home"';