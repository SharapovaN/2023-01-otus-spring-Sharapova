package ru.otus.spring.homework.exception;

public class BookNotFoundException extends ResourceNotFoundException {

    public BookNotFoundException(Long id) {
        super("Book not found: " + id);
    }

}
