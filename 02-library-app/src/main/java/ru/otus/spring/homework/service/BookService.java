package ru.otus.spring.homework.service;

import ru.otus.spring.homework.model.dto.BookDto;
import ru.otus.spring.homework.model.dto.SaveBookDto;
import ru.otus.spring.homework.model.entity.Book;

import java.util.List;

public interface BookService {
    List<BookDto> getAll();

    BookDto getBookDtoById(long id);

    Book getById(long id);

    BookDto getBookWithComments(long id);

    BookDto create(SaveBookDto book);

    void deleteById(long id);

    BookDto update(SaveBookDto book);

    boolean checkBookExists(long id);
}
