package ru.otus.spring.homework.service;

import ru.otus.spring.homework.model.dto.BookDto;
import ru.otus.spring.homework.model.dto.SaveBookDto;

import java.util.List;

public interface BookService {
    List<BookDto> getAll();

    BookDto getById(long id);

    SaveBookDto getSaveBookDtoById(long id);

    BookDto getCommentsForBook(long id);

    void create(SaveBookDto book);

    void deleteById(long id);

    void update(SaveBookDto book);

    boolean checkBookExists(long id);
}
