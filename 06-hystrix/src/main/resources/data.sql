INSERT INTO authors (author_name, author_surname)
VALUES ('Aleksandr', 'Pushkin'),
       ('John', 'Tolkien');

INSERT INTO genres (genre_name)
VALUES ('Historical Novel'),
       ('Fantasy');

INSERT INTO books (book_name, author_id, genre_id)
VALUES ('Captains daughter', 1, 1),
       ('Lord Of The Rings', 2, 2);

INSERT INTO comments (book_id, comment)
VALUES (1, 'Good book'),
       (1, 'Perfect book'),
       (2, 'The best book ever'),
       (2, 'Amazing');


