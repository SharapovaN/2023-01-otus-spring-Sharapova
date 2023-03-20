INSERT INTO authors (author_name, author_surname)
VALUES ('authorName1', 'authorSurname1'), ('authorName2', 'authorSurname2');

INSERT INTO genres (genre_name)
VALUES ('genreName1'), ('genreName2');

INSERT INTO books (book_name, author_id, genre_id)
VALUES ('bookName1', 1, 1), ('bookName2', 2, 2);

INSERT INTO comments (book_id, comment)
VALUES (1, '1commentForBook1'), (1, '2commentForBook1'),
        (2, '1commentForBook2'), (2, '2commentForBook2');