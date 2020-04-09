-- add 1 cashflow period to the database
insert into cashflow (period, previous_balance, credits, debits, date_created, deleted) values ('202004', 0, 0, 0, now(), false);

-- add two documents
insert into document_type (id, name, date_created, deleted) values (1, 'INVOICE', now(), false);
insert into document_type (id, name, date_created, deleted) values (2, 'ORDER', now(), false);

-- add two products
insert into product (id, name, unit, date_created, deleted) values (1, 'Polpa 500g', 'PCT', now(), false);
insert into product (id, name, unit, date_created, deleted) values (2, 'Polpa 10kg', 'Lata',now(), false);

-- add two fruits
insert into fruit (id, name, initials, harvest, date_created, deleted) values (1, 'Cupuacu', 'CUPU', 'Agosto', now(), false);
insert into fruit (id, name, initials, harvest, date_created, deleted) values (2, 'Maracuja', 'MARA', 'Ano Inteiro', now(), false);

