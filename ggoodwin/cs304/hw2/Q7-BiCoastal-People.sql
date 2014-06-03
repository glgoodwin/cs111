--Gabe Goodwin and Johanna Okerlund

use ggoodwin_db;

create table address2 like ggoodwin_db.address;
insert address2 select * from ggoodwin_db.address;

select names.firstname, names.lastname, address.street, address.state, address.city, address2.street, address2.state, address2.city from address inner join address2 using (id) inner join names using(id) where address.state='"MA"' and address2.state = '"CA"';

drop table address2;