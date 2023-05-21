package ru.otus.spring.homework.service;

import ru.otus.spring.homework.model.dto.CommentDto;

import java.util.List;

public interface CommentService {
    List<CommentDto> getAll();

    CommentDto getById(long id);

    List<CommentDto> getByBookId(long id);

    CommentDto create(CommentDto comment);

    void deleteById(long id);

    CommentDto update(CommentDto comment);
}
