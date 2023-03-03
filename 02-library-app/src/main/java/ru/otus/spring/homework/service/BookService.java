package ru.otus.spring.homework.service;

public interface BookService {
    String getById(long id);

    String create(String bookName, long authorId, long genreId);

    String deleteById(long id);

    String update(long id, String bookName, long authorId, long genreId);
}
