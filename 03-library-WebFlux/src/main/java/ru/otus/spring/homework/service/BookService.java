package ru.otus.spring.homework.service;

import ru.otus.spring.homework.model.Book;

import java.util.List;

public interface BookService {
    List<String> getAll();

    String getById(String id);

    List<String> getCommentsForBook(String id);

    String create(String bookName, String authorId, String genreName);

    String deleteById(String id);

    String update(String id, String bookName, String authorId, String genreName);

    boolean checkBookExists(String id);

    Book getBookById(String id);
}
