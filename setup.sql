create table if not exists ecommerce_payment
(
    payment_id          varchar(60)     primary key,
    amount              double precision not null,
    confirmation_number varchar(60)      not null,
    created_at          date             not null,
    payment_mode        varchar(60)      not null,
    payment_status      varchar(60)      not null
);

create table if not exists ecommerce_product
(
    product_id  varchar(60)     primary key,
    created_at  date            not null,
    description varchar(60)     not null,
    price       double precision not null,
    sku         varchar(60)     not null,
    title       varchar(60)     not null
);

create table if not exists ecommerce_address
(
    address_id     varchar(60) primary key,
    address1       varchar(60) not null,
    address2       varchar(60) not null,
    city           varchar(60) not null,
    created_at     date        not null,
    email          varchar(60) not null,
    phone          varchar(60) not null,
    state          varchar(60) not null,
    zip            varchar(60) not null
);

create table if not exists ecommerce_order
(
    order_id            varchar(60) PRIMARY KEY,
    created_at          timestamp,
    customer_id         varchar(60),
    order_status        varchar(60),
    shipping_charges    double precision,
    shipping_mode       varchar(60),
    sub_total           double precision,
    tax                 double precision,
    title               varchar(60),
    total_amt           double precision,
    payment_id          varchar(60),
    billing_address_id  varchar(60),
    shipping_address_id varchar(60),
    FOREIGN KEY (payment_id) REFERENCES ecommerce_payment (payment_id),
    FOREIGN KEY (billing_address_id) REFERENCES ecommerce_address (address_id),
    FOREIGN KEY (shipping_address_id) REFERENCES ecommerce_address (address_id)
);

create table if not exists ecommerce_order_item
(
    order_id       varchar(60),
    product_id     varchar(60),
    quantity       varchar(60) not null,

    PRIMARY KEY (order_id,product_id),
    FOREIGN KEY (order_id) REFERENCES ecommerce_order (order_id),
    FOREIGN KEY (product_id) REFERENCES ecommerce_product (product_id)
);

insert into ecommerce_product (product_id, created_at, description, price, sku, title)
values (109, current_date, 'Whey', '1.99', '1001', 'protein');

insert into ecommerce_product (product_id, created_at, description, price, sku, title)
values (108, current_date, 'Orgain', '5.99', '1005', 'protein');
