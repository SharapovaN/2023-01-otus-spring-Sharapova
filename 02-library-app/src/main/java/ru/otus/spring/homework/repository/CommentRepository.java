package ru.otus.spring.homework.repository;

import ru.otus.spring.homework.model.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentRepository {
    List<Comment> findAll();

    Optional<Comment> findById(long id);

    Comment saveOrUpdate(Comment comment);

    Integer deleteById(long id);
}
