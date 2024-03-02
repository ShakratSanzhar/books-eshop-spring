--liquibase formatted sql

--changeset shakratsanzhar:1
ALTER TABLE users
    ALTER COLUMN username
        DROP NOT NULL;

--changeset shakratsanzhar:2
ALTER TABLE users
    ALTER COLUMN email
        DROP NOT NULL;

--changeset shakratsanzhar:3
ALTER TABLE users
    ALTER COLUMN password
        DROP NOT NULL;

--changeset shakratsanzhar:4
ALTER TABLE users
    ALTER COLUMN role
        DROP NOT NULL;

--changeset shakratsanzhar:5
ALTER TABLE user_details
    ALTER COLUMN name
        DROP NOT NULL;

--changeset shakratsanzhar:6
ALTER TABLE user_details
    ALTER COLUMN surname
        DROP NOT NULL;

--changeset shakratsanzhar:7
ALTER TABLE user_details
    ALTER COLUMN birthday
        DROP NOT NULL;

--changeset shakratsanzhar:8
ALTER TABLE user_details
    ALTER COLUMN phone
        DROP NOT NULL;