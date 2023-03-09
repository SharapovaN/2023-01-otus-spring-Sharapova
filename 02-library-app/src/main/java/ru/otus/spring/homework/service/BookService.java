package ru.otus.spring.homework.service;

import java.util.List;

public interface BookService {
    List<String> getAll();

    String getById(long id);

    String create(String bookName, long authorId, long genreId);

    String deleteById(long id);

    String update(long id, String bookName, long authorId, long genreId);
}
