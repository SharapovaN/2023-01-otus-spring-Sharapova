package ru.otus.spring.homework.exception;

public class CommentNotFoundException extends ResourceNotFoundException {

    public CommentNotFoundException(Long id) {
        super("Comment not found: " + id);
    }

}
