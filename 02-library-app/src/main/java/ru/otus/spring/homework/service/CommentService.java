package ru.otus.spring.homework.service;

import java.util.List;

public interface CommentService {
    List<String> getAll();

    String getById(long id);

    String create(long bookId, String comment);

    String deleteById(long id);

    String update(long id, long bookId, String comment);
}
