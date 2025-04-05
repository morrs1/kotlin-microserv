--liquibase formatted sql

--changeset morrs:1
CREATE TABLE users
(
    id       UUID PRIMARY KEY,
    email    VARCHAR NOT NULL,
    password VARCHAR NOT NULL
);
--changeset morrs:2
CREATE TABLE products
(
    id          UUID PRIMARY KEY,
    name        VARCHAR NOT NULL,
    description VARCHAR NOT NULL,
    price       DECIMAL NOT NULL
);
--changeset morrs:3
CREATE TABLE orders
(
    id      UUID PRIMARY KEY,
    date    DATE NOT NULL,
    status  VARCHAR,
    user_id UUID NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users (id)
);

--changeset morrs:4
CREATE TABLE products_order
(
    id                 UUID PRIMARY KEY,
    order_id           UUID NOT NULL,
    products_id        UUID NOT NULL,
    amount_of_products INT  NOT NULL,
    FOREIGN KEY (order_id) REFERENCES orders (id),
    FOREIGN KEY (products_id) REFERENCES products (id)
);
