package ru.otus.spring.homework.repository;

import ru.otus.spring.homework.model.Book;

public interface BookDao {
    Book getById(long id);

    Integer insert(String bookName, long authorId, long genreId);

    Integer deleteById(long id);

    Integer update(long id, String bookName, long authorId, long genreId);
}
