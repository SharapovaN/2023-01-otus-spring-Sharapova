package ru.otus.spring.homework.repository;

import ru.otus.spring.homework.model.Comment;

import java.util.Set;

public interface BookRepositoryCustom {

    void removeCommentArrayElementById(String commentId);

    Set<Comment> getBookCommentsById(String bookId);
}
