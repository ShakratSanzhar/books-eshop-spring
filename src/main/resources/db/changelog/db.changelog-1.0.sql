--liquibase formatted sql

--changeset shakratsanzhar:1
CREATE TABLE IF NOT EXISTS users
(
    id       BIGSERIAL PRIMARY KEY,
    username VARCHAR(128)  NOT NULL,
    email    VARCHAR(128) UNIQUE NOT NULL,
    password VARCHAR(128)  NOT NULL,
    role     VARCHAR(128)        NOT NULL
    );

--changeset shakratsanzhar:2
CREATE TABLE IF NOT EXISTS user_details
(
    user_id           BIGINT REFERENCES users (id) ON DELETE CASCADE,
    name              VARCHAR(128)        NOT NULL,
    surname           VARCHAR(128)        NOT NULL,
    birthday          DATE                NOT NULL,
    phone             VARCHAR(128) UNIQUE NOT NULL,
    registration_date TIMESTAMP           NOT NULL,
    PRIMARY KEY (user_id)
    );

--changeset shakratsanzhar:3
CREATE TABLE IF NOT EXISTS category
(
    id        SERIAL PRIMARY KEY,
    name      VARCHAR(256) UNIQUE NOT NULL,
    parent_id INT REFERENCES category (id) ON DELETE CASCADE
    );

--changeset shakratsanzhar:4
CREATE TABLE IF NOT EXISTS product
(
    id               BIGSERIAL PRIMARY KEY,
    category_id      INT NOT NULL REFERENCES category (id) ON DELETE CASCADE,
    name             VARCHAR(256) UNIQUE          NOT NULL,
    description      TEXT,
    author           VARCHAR(128)                 NOT NULL,
    image            VARCHAR(256)                 NOT NULL,
    price            INT                          NOT NULL,
    remaining_amount INT,
    created_at       TIMESTAMP                    NOT NULL
    );

--changeset shakratsanzhar:5
CREATE TABLE IF NOT EXISTS cart
(
    id      BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL REFERENCES users (id)  ON DELETE CASCADE,
    price   INT
    );

--changeset shakratsanzhar:6
CREATE TABLE IF NOT EXISTS cart_product
(
    id         BIGSERIAL PRIMARY KEY,
    cart_id    BIGINT REFERENCES cart (id) ON DELETE CASCADE,
    product_id BIGINT REFERENCES product (id) ON DELETE CASCADE,
    quantity   INT
    );

--changeset shakratsanzhar:7
CREATE TABLE IF NOT EXISTS orders
(
    id         BIGSERIAL PRIMARY KEY,
    user_id    BIGINT  NOT NULL REFERENCES users (id) ON DELETE CASCADE,
    status     VARCHAR(128)                 NOT NULL,
    price      INT,
    created_at TIMESTAMP                    NOT NULL,
    closed_at  TIMESTAMP
    );

--changeset shakratsanzhar:8
CREATE TABLE IF NOT EXISTS order_product
(
    id         BIGSERIAL PRIMARY KEY,
    order_id   BIGINT REFERENCES orders (id) ON DELETE CASCADE,
    product_id BIGINT REFERENCES product (id) ON DELETE CASCADE,
    quantity   INT
    );