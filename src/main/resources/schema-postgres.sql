DROP DATABASE IF EXISTS cupuama;

CREATE DATABASE cupuama
    WITH 
    OWNER = postgres
    ENCODING = 'UTF8'
    LC_COLLATE = 'en_US.UTF-8'
    LC_CTYPE = 'en_US.UTF-8'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1;

GRANT TEMPORARY ON DATABASE cupuama TO cupuama;

GRANT ALL ON DATABASE cupuama TO postgres;

GRANT TEMPORARY, CONNECT ON DATABASE cupuama TO PUBLIC;

DROP TABLE IF EXISTS public.users;
DROP TABLE IF EXISTS public.user_access_level;
DROP TABLE IF EXISTS public.process_type;
DROP TABLE IF EXISTS public.supplier;
DROP TABLE IF EXISTS public.benefit;
DROP TABLE IF EXISTS public.document_type;
DROP TABLE IF EXISTS public.depot;
DROP TABLE IF EXISTS public.fruit;
DROP TABLE IF EXISTS public.cashflow;
DROP TABLE IF EXISTS public.product;
DROP TABLE IF EXISTS public.customer;
DROP TABLE IF EXISTS public.company;
DROP TABLE IF EXISTS public.department;
DROP TABLE IF EXISTS public.process_flow_type;
DROP TABLE IF EXISTS public.product_fruit_price;
DROP TABLE IF EXISTS public.product_fruit;
DROP TABLE IF EXISTS public.stocktake;
DROP TABLE IF EXISTS public.inventory;
DROP TABLE IF EXISTS public.processing;
DROP TABLE IF EXISTS public.processing_detail;
DROP TABLE IF EXISTS public.cash_transaction;
DROP TABLE IF EXISTS public.employee_benefit;
DROP TABLE IF EXISTS public.employee_salary;

-- Table: public.users
CREATE TABLE public.users
(
    login character varying(255) COLLATE pg_catalog."default" NOT NULL,
    date_created timestamp without time zone NOT NULL,
    date_updated timestamp without time zone,
    deleted boolean NOT NULL,
    date_of_birth date NOT NULL,
    first_name character varying(255) COLLATE pg_catalog."default" NOT NULL,
    last_name character varying(255) COLLATE pg_catalog."default" NOT NULL,
    password character varying(255) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT users_pkey PRIMARY KEY (login)
)

TABLESPACE pg_default;

ALTER TABLE public.users
    OWNER to cupuama;


-- Table: public.user_access_level
CREATE TABLE public.user_access_level
(
    id bigint NOT NULL DEFAULT nextval('user_access_level_id_seq'::regclass),
    date_created timestamp without time zone NOT NULL,
    date_updated timestamp without time zone,
    deleted boolean NOT NULL,
    access_level character varying(255) COLLATE pg_catalog."default",
    from_date timestamp without time zone,
    to_date timestamp without time zone,
    user_id character varying(255) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT user_access_level_pkey PRIMARY KEY (id),
    CONSTRAINT fki8wgs94sw5yqkc8up10ft9mkq FOREIGN KEY (user_id)
        REFERENCES public.users (login) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

TABLESPACE pg_default;

ALTER TABLE public.user_access_level
    OWNER to cupuama;


-- Table: public.process_type
CREATE TABLE public.process_type
(
    id bigint NOT NULL DEFAULT nextval('process_type_id_seq'::regclass),
    date_created timestamp without time zone NOT NULL,
    date_updated timestamp without time zone,
    deleted boolean NOT NULL,
    flow_type_model character varying(255) COLLATE pg_catalog."default" NOT NULL,
    name character varying(255) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT process_type_pkey PRIMARY KEY (id),
    CONSTRAINT uc_process_type_name UNIQUE (name)
)

TABLESPACE pg_default;

ALTER TABLE public.process_type
    OWNER to cupuama;


-- Table: public.supplier
CREATE TABLE public.supplier
(
    id bigint NOT NULL DEFAULT nextval('supplier_id_seq'::regclass),
    date_created timestamp without time zone NOT NULL,
    date_updated timestamp without time zone,
    deleted boolean NOT NULL,
    date_of_birth date NOT NULL,
    first_name character varying(255) COLLATE pg_catalog."default" NOT NULL,
    last_name character varying(255) COLLATE pg_catalog."default" NOT NULL,
    city character varying(255) COLLATE pg_catalog."default" NOT NULL,
    country character varying(255) COLLATE pg_catalog."default" NOT NULL,
    postal_code character varying(255) COLLATE pg_catalog."default",
    region character varying(255) COLLATE pg_catalog."default" NOT NULL,
    street character varying(255) COLLATE pg_catalog."default" NOT NULL,
    company_name character varying(255) COLLATE pg_catalog."default",
    phone character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT supplier_pkey PRIMARY KEY (id)
)

TABLESPACE pg_default;

ALTER TABLE public.supplier
    OWNER to cupuama;


-- Table: public.benefit
CREATE TABLE public.benefit
(
    id bigint NOT NULL DEFAULT nextval('benefit_id_seq'::regclass),
    date_created timestamp without time zone NOT NULL,
    date_updated timestamp without time zone,
    deleted boolean NOT NULL,
    name character varying(255) COLLATE pg_catalog."default" NOT NULL,
    valid_until date NOT NULL,
    CONSTRAINT benefit_pkey PRIMARY KEY (id),
    CONSTRAINT uc_benefit_name UNIQUE (name)
)

TABLESPACE pg_default;

ALTER TABLE public.benefit
    OWNER to cupuama;



-- Table: public.document_type
CREATE TABLE public.document_type
(
    id bigint NOT NULL DEFAULT nextval('document_type_id_seq'::regclass),
    date_created timestamp without time zone NOT NULL,
    date_updated timestamp without time zone,
    deleted boolean NOT NULL,
    name character varying(255) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT document_type_pkey PRIMARY KEY (id),
    CONSTRAINT uc_doctype_name UNIQUE (name)
)

TABLESPACE pg_default;

ALTER TABLE public.document_type
    OWNER to cupuama;


-- Table: public.depot
CREATE TABLE public.depot
(
    id bigint NOT NULL DEFAULT nextval('depot_id_seq'::regclass),
    date_created timestamp without time zone NOT NULL,
    date_updated timestamp without time zone,
    deleted boolean NOT NULL,
    keep_stock boolean NOT NULL,
    name character varying(255) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT depot_pkey PRIMARY KEY (id),
    CONSTRAINT uc_depot_name UNIQUE (name)
)

TABLESPACE pg_default;

ALTER TABLE public.depot
    OWNER to cupuama;


-- Table: public.fruit
CREATE TABLE public.fruit
(
    id bigint NOT NULL DEFAULT nextval('fruit_id_seq'::regclass),
    date_created timestamp without time zone NOT NULL,
    date_updated timestamp without time zone,
    deleted boolean NOT NULL,
    harvest character varying(255) COLLATE pg_catalog."default",
    initials character varying(255) COLLATE pg_catalog."default" NOT NULL,
    name character varying(255) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT fruit_pkey PRIMARY KEY (id),
    CONSTRAINT uc_fruit_name UNIQUE (name)
)

TABLESPACE pg_default;

ALTER TABLE public.fruit
    OWNER to cupuama;
    

-- Table: public.cashflow    
CREATE TABLE public.cashflow
(
    period character varying(255) COLLATE pg_catalog."default" NOT NULL,
    date_created timestamp without time zone NOT NULL,
    date_updated timestamp without time zone,
    deleted boolean NOT NULL,
    credits double precision NOT NULL,
    debits double precision NOT NULL,
    previous_balance double precision NOT NULL,
    CONSTRAINT cashflow_pkey PRIMARY KEY (period)
)

TABLESPACE pg_default;

ALTER TABLE public.cashflow
    OWNER to cupuama;
    
    
-- Table: public.product
CREATE TABLE public.product
(
    id bigint NOT NULL DEFAULT nextval('product_id_seq'::regclass),
    date_created timestamp without time zone NOT NULL,
    date_updated timestamp without time zone,
    deleted boolean NOT NULL,
    name character varying(255) COLLATE pg_catalog."default" NOT NULL,
    unit character varying(255) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT product_pkey PRIMARY KEY (id),
    CONSTRAINT uc_product_name UNIQUE (name)
)

TABLESPACE pg_default;

ALTER TABLE public.product
    OWNER to cupuama;


-- Table: public.customer    
CREATE TABLE public.customer
(
    id bigint NOT NULL DEFAULT nextval('customer_id_seq'::regclass),
    date_created timestamp without time zone NOT NULL,
    date_updated timestamp without time zone,
    deleted boolean NOT NULL,
    date_of_birth date NOT NULL,
    first_name character varying(255) COLLATE pg_catalog."default" NOT NULL,
    last_name character varying(255) COLLATE pg_catalog."default" NOT NULL,
    city character varying(255) COLLATE pg_catalog."default" NOT NULL,
    country character varying(255) COLLATE pg_catalog."default" NOT NULL,
    postal_code character varying(255) COLLATE pg_catalog."default",
    region character varying(255) COLLATE pg_catalog."default" NOT NULL,
    street character varying(255) COLLATE pg_catalog."default" NOT NULL,
    company_name character varying(255) COLLATE pg_catalog."default",
    phone character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT customer_pkey PRIMARY KEY (id)
)

TABLESPACE pg_default;

ALTER TABLE public.customer
    OWNER to cupuama;



-- Table: public.stocktake
CREATE TABLE public.stocktake
(
    id bigint NOT NULL DEFAULT nextval('stocktake_id_seq'::regclass),
    date_created timestamp without time zone NOT NULL,
    date_updated timestamp without time zone,
    deleted boolean NOT NULL,
    amount double precision NOT NULL,
    stocktake_date timestamp without time zone NOT NULL,
    stocktake_inout character varying(255) COLLATE pg_catalog."default" NOT NULL,
    depot_id bigint NOT NULL,
    fruit_id bigint NOT NULL,
    product_id bigint NOT NULL,
    CONSTRAINT stocktake_pkey PRIMARY KEY (id),
    CONSTRAINT fk216nwr4ajq1j3bpxfmkgwcrb6 FOREIGN KEY (depot_id)
        REFERENCES public.depot (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fk2lmvt9u8g933p3rbwrp1soj3a FOREIGN KEY (fruit_id)
        REFERENCES public.fruit (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fk5yrl51oidvqa5wuodeuc0vup0 FOREIGN KEY (product_id)
        REFERENCES public.product (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

TABLESPACE pg_default;

ALTER TABLE public.stocktake
    OWNER to cupuama;


-- Table: public.product_fruit_price
CREATE TABLE public.product_fruit_price
(
    id bigint NOT NULL DEFAULT nextval('product_fruit_price_id_seq'::regclass),
    date_created timestamp without time zone NOT NULL,
    date_updated timestamp without time zone,
    deleted boolean NOT NULL,
    price double precision NOT NULL,
    price_expiration_date timestamp without time zone NOT NULL,
    fruit_id bigint NOT NULL,
    product_id bigint NOT NULL,
    CONSTRAINT product_fruit_price_pkey PRIMARY KEY (id),
    CONSTRAINT fkfu84brya1m81pq3vsny6asf6p FOREIGN KEY (fruit_id)
        REFERENCES public.fruit (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fkt8a8wbamorbsg8squ9l479uf1 FOREIGN KEY (product_id)
        REFERENCES public.product (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

TABLESPACE pg_default;

ALTER TABLE public.product_fruit_price
    OWNER to cupuama;


-- Table: public.product_fruit
CREATE TABLE public.product_fruit
(
    date_created timestamp without time zone NOT NULL,
    date_updated timestamp without time zone,
    deleted boolean NOT NULL,
    fruit_id bigint NOT NULL,
    product_id bigint NOT NULL,
    CONSTRAINT product_fruit_pkey PRIMARY KEY (fruit_id, product_id),
    CONSTRAINT fkkl8s9866dx6lfifw6r5m6o81a FOREIGN KEY (fruit_id)
        REFERENCES public.fruit (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fkqvd1h1c6m1oekc3vqmepjvw1l FOREIGN KEY (product_id)
        REFERENCES public.product (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

TABLESPACE pg_default;

ALTER TABLE public.product_fruit
    OWNER to cupuama;



-- Table: public.processing_detail
CREATE TABLE public.processing_detail
(
    id bigint NOT NULL DEFAULT nextval('processing_detail_id_seq'::regclass),
    date_created timestamp without time zone NOT NULL,
    date_updated timestamp without time zone,
    deleted boolean NOT NULL,
    amount double precision NOT NULL,
    discount double precision,
    price double precision NOT NULL,
    stocktake_in_out character varying(255) COLLATE pg_catalog."default" NOT NULL,
    depot_id bigint NOT NULL,
    fruit_id bigint NOT NULL,
    processing_id bigint NOT NULL,
    product_id bigint NOT NULL,
    CONSTRAINT processing_detail_pkey PRIMARY KEY (id),
    CONSTRAINT fk4il3reswblju690scjmbu8ggu FOREIGN KEY (product_id)
        REFERENCES public.product (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fk6b7pd36f7rssx2hy3oclb7o86 FOREIGN KEY (fruit_id)
        REFERENCES public.fruit (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fk97tu7eec0281hb0uit2l9wx4 FOREIGN KEY (processing_id)
        REFERENCES public.processing (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fkc9fykb2y10eofuf41lbr4kc7x FOREIGN KEY (depot_id)
        REFERENCES public.depot (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

TABLESPACE pg_default;

ALTER TABLE public.processing_detail
    OWNER to cupuama;


-- Table: public.processing
CREATE TABLE public.processing
(
    id bigint NOT NULL DEFAULT nextval('processing_id_seq'::regclass),
    date_created timestamp without time zone NOT NULL,
    date_updated timestamp without time zone,
    deleted boolean NOT NULL,
    document_reference character varying(255) COLLATE pg_catalog."default",
    processing_date timestamp without time zone NOT NULL,
    status character varying(255) COLLATE pg_catalog."default" NOT NULL,
    remarks character varying(255) COLLATE pg_catalog."default",
    customer_id bigint,
    process_type_id bigint NOT NULL,
    supplier_id bigint,
    CONSTRAINT processing_pkey PRIMARY KEY (id),
    CONSTRAINT fkervbi0oe1h5jvf3yhq8ixd1oe FOREIGN KEY (process_type_id)
        REFERENCES public.process_type (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fkkea7hh6xc6u797tj9yj2on5tj FOREIGN KEY (supplier_id)
        REFERENCES public.supplier (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fkplxapbloe2gn6c2jfkju05scl FOREIGN KEY (customer_id)
        REFERENCES public.customer (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

TABLESPACE pg_default;

ALTER TABLE public.processing
    OWNER to cupuama;



-- Table: public.process_flow_type
CREATE TABLE public.process_flow_type
(
    stocktake_in_out character varying(255) COLLATE pg_catalog."default" NOT NULL,
    date_created timestamp without time zone NOT NULL,
    date_updated timestamp without time zone,
    deleted boolean NOT NULL,
    process_type_id bigint NOT NULL,
    product_id bigint NOT NULL,
    depot_in_id bigint,
    depot_out_id bigint,
    CONSTRAINT process_flow_type_pkey PRIMARY KEY (process_type_id, product_id, stocktake_in_out),
    CONSTRAINT fk6q1tijorcjt04p391l64py8qk FOREIGN KEY (process_type_id)
        REFERENCES public.process_type (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fkakq9b23vl0ls0j1rcqnw4sdwg FOREIGN KEY (depot_in_id)
        REFERENCES public.depot (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fkej61hq2xn176tn0nb12j31eq1 FOREIGN KEY (depot_out_id)
        REFERENCES public.depot (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fknb7jj92n5e0xlxb1dksc3pxng FOREIGN KEY (product_id)
        REFERENCES public.product (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

TABLESPACE pg_default;

ALTER TABLE public.process_flow_type
    OWNER to cupuama;


-- Table: public.inventory
CREATE TABLE public.inventory
(
    period character varying(255) COLLATE pg_catalog."default" NOT NULL,
    date_created timestamp without time zone NOT NULL,
    date_updated timestamp without time zone,
    deleted boolean NOT NULL,
    initial_stock double precision NOT NULL,
    stock_in double precision NOT NULL,
    stock_out double precision NOT NULL,
    fruit_id bigint NOT NULL,
    depot_id bigint NOT NULL,
    product_id bigint NOT NULL,
    CONSTRAINT inventory_pkey PRIMARY KEY (depot_id, fruit_id, period, product_id),
    CONSTRAINT fklg2gc7ovas855qu5f4wh9ckn0 FOREIGN KEY (fruit_id)
        REFERENCES public.fruit (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fkp7gj4l80fx8v0uap3b2crjwp5 FOREIGN KEY (product_id)
        REFERENCES public.product (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fksc43kahu3c8exa5u1g5syajap FOREIGN KEY (depot_id)
        REFERENCES public.depot (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

TABLESPACE pg_default;

ALTER TABLE public.inventory
    OWNER to cupuama;


-- Table: public.company    
CREATE TABLE public.company
(
    id bigint NOT NULL DEFAULT nextval('company_id_seq'::regclass),
    date_created timestamp without time zone NOT NULL,
    date_updated timestamp without time zone,
    deleted boolean NOT NULL,
    city character varying(255) COLLATE pg_catalog."default" NOT NULL,
    country character varying(255) COLLATE pg_catalog."default" NOT NULL,
    postal_code character varying(255) COLLATE pg_catalog."default",
    region character varying(255) COLLATE pg_catalog."default" NOT NULL,
    street character varying(255) COLLATE pg_catalog."default" NOT NULL,
    name character varying(255) COLLATE pg_catalog."default" NOT NULL,
    date_of_birth date NOT NULL,
    first_name character varying(255) COLLATE pg_catalog."default" NOT NULL,
    last_name character varying(255) COLLATE pg_catalog."default" NOT NULL,
    company_role character varying(255) COLLATE pg_catalog."default" NOT NULL,
    fired_date date,
    hired_date date NOT NULL,
    company_id bigint NOT NULL,
    department_id bigint NOT NULL,
    manager_employee_id bigint,
    CONSTRAINT company_pkey PRIMARY KEY (id),
    CONSTRAINT uc_company_name UNIQUE (name),
    CONSTRAINT fk19re5anl1fqt4a070a8rst7p FOREIGN KEY (department_id)
        REFERENCES public.department (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fk2lxqdb13jlcrjl00kh2sxtn5y FOREIGN KEY (company_id)
        REFERENCES public.company (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fkfguhwu7k6mv2ge7s93l1onccv FOREIGN KEY (manager_employee_id)
        REFERENCES public.company (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

TABLESPACE pg_default;

ALTER TABLE public.company
    OWNER to cupuama;

    
-- Table: public.employee_salary
CREATE TABLE public.employee_salary
(
    id bigint NOT NULL DEFAULT nextval('employee_salary_id_seq'::regclass),
    date_created timestamp without time zone NOT NULL,
    date_updated timestamp without time zone,
    deleted boolean NOT NULL,
    from_date date,
    to_date date,
    employee_id bigint NOT NULL,
    CONSTRAINT employee_salary_pkey PRIMARY KEY (id),
    CONSTRAINT fkd3kcx5hw45agmd6r0s3d5kbux FOREIGN KEY (employee_id)
        REFERENCES public.company (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

TABLESPACE pg_default;

ALTER TABLE public.employee_salary
    OWNER to cupuama;

-- Table: public.employee_benefit
CREATE TABLE public.employee_benefit
(
    id bigint NOT NULL DEFAULT nextval('employee_benefit_id_seq'::regclass),
    date_created timestamp without time zone NOT NULL,
    date_updated timestamp without time zone,
    deleted boolean NOT NULL,
    from_date date NOT NULL,
    to_date date,
    benefit_id bigint NOT NULL,
    employee_id bigint NOT NULL,
    CONSTRAINT employee_benefit_pkey PRIMARY KEY (id),
    CONSTRAINT uc_employee_benefit_ids UNIQUE (employee_id, benefit_id),
    CONSTRAINT fkadxj3xicjjo67gw67lvg61foc FOREIGN KEY (employee_id)
        REFERENCES public.company (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fki0mammuh2qwnrvb61ie489dbi FOREIGN KEY (benefit_id)
        REFERENCES public.benefit (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

TABLESPACE pg_default;

ALTER TABLE public.employee_benefit
    OWNER to cupuama;




-- Table: public.department
CREATE TABLE public.department
(
    id bigint NOT NULL DEFAULT nextval('department_id_seq'::regclass),
    date_created timestamp without time zone NOT NULL,
    date_updated timestamp without time zone,
    deleted boolean NOT NULL,
    name character varying(255) COLLATE pg_catalog."default" NOT NULL,
    company_id bigint NOT NULL,
    CONSTRAINT department_pkey PRIMARY KEY (id),
    CONSTRAINT uc_department_name UNIQUE (name),
    CONSTRAINT fkh1m88q0f7sc0mk76kju4kcn6f FOREIGN KEY (company_id)
        REFERENCES public.company (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

TABLESPACE pg_default;

ALTER TABLE public.department
    OWNER to cupuama;



-- Table: public.cash_transaction
CREATE TABLE public.cash_transaction
(
    id bigint NOT NULL DEFAULT nextval('cash_transaction_id_seq'::regclass),
    date_created timestamp without time zone NOT NULL,
    date_updated timestamp without time zone,
    deleted boolean NOT NULL,
    description character varying(255) COLLATE pg_catalog."default",
    document_number character varying(255) COLLATE pg_catalog."default" NOT NULL,
    item_date timestamp without time zone NOT NULL,
    type character varying(255) COLLATE pg_catalog."default",
    value double precision,
    document_type_id bigint,
    CONSTRAINT cash_transaction_pkey PRIMARY KEY (id),
    CONSTRAINT fk6b5dq3ltucsnve8ual8d82qsj FOREIGN KEY (document_type_id)
        REFERENCES public.document_type (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

TABLESPACE pg_default;

ALTER TABLE public.cash_transaction
    OWNER to cupuama;


    
