-- add 1 cashflow period to the database
insert into cashflow (period, previous_balance, credits, debits, date_created, deleted) values ('202004', 0, 0, 0, now(), false);

-- add two documents
insert into document_type (name, date_created, deleted) values ('INVOICE', now(), false);
insert into document_type (name, date_created, deleted) values ('ORDER', now(), false);

-- add two products
insert into product (name, unit, date_created, deleted) values ('Polpa 500g', 'PCT', now(), false);
insert into product (name, unit, date_created, deleted) values ('Polpa 10kg', 'Lata',now(), false);

-- add two fruits
insert into fruit (name, initials, harvest, date_created, deleted) values ('Cupuacu', 'CUPU', 'Agosto', now(), false);
insert into fruit (name, initials, harvest, date_created, deleted) values ('Maracuja', 'MARA', 'Ano Inteiro', now(), false);

-- add one depot
insert into depot (name, keep_stock, date_created, deleted) values ('Manaus', true, now(), false);
