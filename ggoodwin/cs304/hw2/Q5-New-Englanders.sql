--Gabe Goodwin & Johanna Okerlund

use ggoodwin_db;

select names.firstname, names.lastname from names inner join address using (id) where (address.state = '"MA"' or address.state= '"CT"'or address.state =  '"VT"' or address.state =  '"NH"') and address.type = '"Home"';