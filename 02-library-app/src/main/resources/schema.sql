DROP TABLE IF EXISTS authors CASCADE;
DROP TABLE IF EXISTS books CASCADE;
DROP TABLE IF EXISTS genres CASCADE;
DROP TABLE IF EXISTS comments CASCADE;
DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS roles CASCADE;
DROP TABLE IF EXISTS users_roles CASCADE;

CREATE TABLE authors
(
    id             BIGSERIAL,
    author_name    VARCHAR(255),
    author_surname VARCHAR(255),
    PRIMARY KEY (id)
);

CREATE TABLE genres
(
    id         BIGSERIAL,
    genre_name VARCHAR(255),
    PRIMARY KEY (id)
);

CREATE TABLE books
(
    id        BIGSERIAL,
    book_name VARCHAR(255) NOT NULL,
    author_id BIGINT       NOT NULL REFERENCES authors (id),
    genre_id  BIGINT       NOT NULL REFERENCES genres (id),
    PRIMARY KEY (id)
);

CREATE TABLE comments
(
    id      BIGSERIAL,
    book_id BIGINT NOT NULL REFERENCES books (id) ON DELETE CASCADE,
    comment VARCHAR(255),
    PRIMARY KEY (id)
);

CREATE TABLE users
(
    id       BIGSERIAL,
    username VARCHAR(50)  NOT NULL,
    password VARCHAR(250) NOT NULL,
    PRIMARY KEY (id),
    constraint uq1 unique (username)
);

CREATE TABLE roles
(
    id   BIGSERIAL,
    name VARCHAR(50) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE users_roles
(
    users_id BIGINT NOT NULL,
    roles_id BIGINT NOT NULL,
    CONSTRAINT users_id
        FOREIGN KEY (users_id)
            REFERENCES users (id),
    CONSTRAINT roles_id
        FOREIGN KEY (roles_id)
            REFERENCES roles (id)
);