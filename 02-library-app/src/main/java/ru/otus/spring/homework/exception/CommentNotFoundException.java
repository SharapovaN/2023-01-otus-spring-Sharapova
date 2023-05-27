package ru.otus.spring.homework.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class CommentNotFoundException extends ResourceNotFoundException {

    public CommentNotFoundException(Long id) {
        super("Comment not found: " + id);
    }

}
