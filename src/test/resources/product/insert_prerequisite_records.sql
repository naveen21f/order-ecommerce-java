create table if not exists ecommerce_product
(
    product_id      varchar(60)     primary key,
    created_at  date             not null,
    description varchar(60)     not null,
    price       double precision not null,
    sku         varchar(60)     not null,
    title       varchar(60)     not null
    );

insert into ecommerce_product (product_id, created_at, description, price, sku, title)
values (106, '"2022-10-17', 'Coke', '5.99', '1006', 'SoftDrink');
