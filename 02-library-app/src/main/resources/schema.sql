CREATE TABLE authors(
    id BIGINT NOT NULL AUTO_INCREMENT,
    author_name VARCHAR(255),
    author_surname VARCHAR(255),
    PRIMARY KEY (id)
);

CREATE TABLE genres(
    id BIGINT NOT NULL AUTO_INCREMENT,
    genre_name VARCHAR(255),
    PRIMARY KEY (id)
);

CREATE TABLE books(
    id BIGINT NOT NULL AUTO_INCREMENT,
    book_name VARCHAR(255) NOT NULL,
    author_id BIGINT NOT NULL REFERENCES authors(id),
    genre_id BIGINT NOT NULL REFERENCES genres(id),
    PRIMARY KEY (id)
);