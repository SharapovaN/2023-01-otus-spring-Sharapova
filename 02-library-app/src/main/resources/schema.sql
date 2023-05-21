DROP TABLE IF EXISTS authors;
DROP TABLE IF EXISTS books;
DROP TABLE IF EXISTS genres;
DROP TABLE IF EXISTS comments;
DROP TABLE IF EXISTS users;

CREATE TABLE authors
(
    id             BIGINT NOT NULL AUTO_INCREMENT,
    author_name    VARCHAR(255),
    author_surname VARCHAR(255),
    PRIMARY KEY (id)
);

CREATE TABLE genres
(
    id         BIGINT NOT NULL AUTO_INCREMENT,
    genre_name VARCHAR(255),
    PRIMARY KEY (id)
);

CREATE TABLE books
(
    id        BIGINT       NOT NULL AUTO_INCREMENT,
    book_name VARCHAR(255) NOT NULL,
    author_id BIGINT       NOT NULL REFERENCES authors (id),
    genre_id  BIGINT       NOT NULL REFERENCES genres (id),
    PRIMARY KEY (id)
);

CREATE TABLE comments
(
    id      BIGINT NOT NULL AUTO_INCREMENT,
    book_id BIGINT NOT NULL REFERENCES books (id) ON DELETE CASCADE,
    comment VARCHAR(255),
    PRIMARY KEY (id)
);

CREATE TABLE users
(
    id       BIGINT       NOT NULL AUTO_INCREMENT,
    username VARCHAR(50)  NOT NULL,
    password VARCHAR(250) NOT NULL,
    PRIMARY KEY (id)
);