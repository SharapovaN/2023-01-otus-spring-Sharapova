package ru.otus.spring.homework.repository;

import ru.otus.spring.homework.model.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository {
    List<Book> findAll();

    Optional<Book> findById(long id);

    Book saveOrUpdate(Book book, long authorId, long genreId);

    Integer deleteById(long id);
}
