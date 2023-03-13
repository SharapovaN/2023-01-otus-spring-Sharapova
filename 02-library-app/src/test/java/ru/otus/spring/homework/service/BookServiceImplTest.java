package ru.otus.spring.homework.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.spring.homework.model.Author;
import ru.otus.spring.homework.model.Book;
import ru.otus.spring.homework.model.Genre;
import ru.otus.spring.homework.repository.BookRepositoryJpa;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class BookServiceImplTest {

    @InjectMocks
    private BookServiceImpl bookService;

    @Mock
    private BookRepositoryJpa bookRepositoryJpa;

    @Test
    void getByIdIfBookExistsTest() {
        given(bookRepositoryJpa.findById(1)).willReturn(Optional.of(new Book(1, "bookName",
                new Author(1, "name", "surname"), new Genre(1, "genre"), null)));
        String book = bookService.getById(1);
        Assertions.assertTrue(book.contains("name"));
    }

    @Test
    void getByIdIfBookNotExistsTest() {
        given(bookRepositoryJpa.findById(1)).willReturn(Optional.empty());
        String book = bookService.getById(1);
        Assertions.assertTrue(book.contains("not found"));
    }

    @Test
    void getAllIfBooksExistsTest() {
        List<Book> books = new ArrayList<>();
        books.add(new Book(1, "bookName",
                new Author(1, "name", "surname"),
                new Genre(1, "genre"),
                null));
        books.add(new Book(2, "bookName",
                new Author(1, "name", "surname"),
                new Genre(1, "genre"),
                null));
        given(bookRepositoryJpa.findAll()).willReturn(books);
        Assertions.assertEquals(2, bookService.getAll().size());
    }

    @Test
    void createIfOkTest() {
        given(bookRepositoryJpa.saveOrUpdate(new Book("name"), 1, 1))
                .willReturn(new Book(3, "name"));
        String book = bookService.create("name", 1, 1);
        Assertions.assertTrue(book.contains("Book successfully created"));
    }

    @Test
    void deleteByIdIfBookExistTest() {
        given(bookRepositoryJpa.deleteById(1)).willReturn(1);
        String book = bookService.deleteById(1);
        Assertions.assertTrue(book.contains("Book successfully deleted"));
    }

    @Test
    void deleteByIdIfBookNotExistTest() {
        given(bookRepositoryJpa.deleteById(1)).willReturn(0);
        String book = bookService.deleteById(1);
        Assertions.assertTrue(book.contains("Book delete failed"));
    }

    @Test
    void updateIfBookExistTest() {
        given(bookRepositoryJpa.saveOrUpdate(new Book(1, "bookName3"), 2, 2))
                .willReturn(new Book(1, "bookName3"));
        given(bookRepositoryJpa.checkBookExists(1)).willReturn(true);
        String book = bookService.update(1, "bookName3", 2, 2);
        Assertions.assertTrue(book.contains("Book successfully updated"));
    }

    @Test
    void updateIfBookNotExistTest() {
        given(bookRepositoryJpa.checkBookExists(3)).willReturn(false);
        String book = bookService.update(3, "bookName3", 2, 2);
        Assertions.assertTrue(book.contains("Book update failed"));
    }
}