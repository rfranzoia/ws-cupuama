-- add 1 cashflow period to the database
insert into cashflow (period, previous_balance, credits, debits, date_created, deleted) values ('202004', 0, 0, 0, now(), false);

-- add two documents
insert into document_type (id, name, date_created, deleted) values (1, 'INVOICE', now(), false);
insert into document_type (id, name, date_created, deleted) values (2, 'ORDER', now(), false);

