DROP TABLE IF EXISTS authors CASCADE;
DROP TABLE IF EXISTS books CASCADE;
DROP TABLE IF EXISTS genres CASCADE;
DROP TABLE IF EXISTS comments CASCADE;

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


