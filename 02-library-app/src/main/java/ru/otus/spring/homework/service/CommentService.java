package ru.otus.spring.homework.service;

import ru.otus.spring.homework.model.dto.CommentDto;
import ru.otus.spring.homework.model.entity.Comment;

import java.util.List;

public interface CommentService {
    List<CommentDto> getAll();

    CommentDto getById(long id);

    List<CommentDto> getByBookId(long id);

    Comment create(CommentDto comment);

    void deleteById(long id);

    Comment update(CommentDto comment);
}
