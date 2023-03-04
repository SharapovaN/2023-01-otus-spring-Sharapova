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
import ru.otus.spring.homework.repository.BookDaoJdbc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class BookServiceImplTest {

    @InjectMocks
    private BookServiceImpl bookService;

    @Mock
    private BookDaoJdbc bookDaoJdbc;

    @Test
    void getByIdIfBookExistsTest() {
        given(bookDaoJdbc.getById(1)).willReturn(new Book(1, "bookName",
                new Author(1, "name", "surname"), new Genre(1, "genre")));
        String book = bookService.getById(1);
        Assertions.assertFalse(book.isEmpty());
        Assertions.assertTrue(book.contains("name"));
    }

    @Test
    void getByIdIfBookNotExistsTest() {
        given(bookDaoJdbc.getById(1)).willReturn(null);
        String book = bookService.getById(1);
        Assertions.assertFalse(book.isEmpty());
        Assertions.assertTrue(book.contains("not found"));
    }

    @Test
    void getAllIfBooksExistsTest() {
        List<Book> books = new ArrayList<>();
        books.add(new Book(1, "bookName",
                new Author(1, "name", "surname"),
                new Genre(1, "genre")));
        books.add(new Book(2, "bookName",
                new Author(1, "name", "surname"),
                new Genre(1, "genre")));
        given(bookDaoJdbc.getAll()).willReturn(books);
        Assertions.assertEquals(2, bookService.getAll().size());
    }

    @Test
    void createIfOkTest() {
        given(bookDaoJdbc.insert("name", 1, 1)).willReturn(1);
        String book = bookService.create("name", 1, 1);
        Assertions.assertFalse(book.isEmpty());
        Assertions.assertTrue(book.contains("Book successfully created"));
    }

    @Test
    void createIfNotOkTest() {
        given(bookDaoJdbc.insert("name", 1, 1)).willReturn(0);
        String book = bookService.create("name", 1, 1);
        Assertions.assertFalse(book.isEmpty());
        Assertions.assertTrue(book.contains("Book create failed"));
    }

    @Test
    void deleteByIdIfBookExistTest() {
        given(bookDaoJdbc.deleteById(1)).willReturn(1);
        String book = bookService.deleteById(1);
        Assertions.assertFalse(book.isEmpty());
        Assertions.assertTrue(book.contains("Book successfully deleted"));
    }

    @Test
    void deleteByIdIfBookNotExistTest() {
        given(bookDaoJdbc.deleteById(1)).willReturn(0);
        String book = bookService.deleteById(1);
        Assertions.assertFalse(book.isEmpty());
        Assertions.assertTrue(book.contains("Book delete failed"));
    }

    @Test
    void updateIfBookExistTest() {
        given(bookDaoJdbc.update(1, "bookName3", 2, 2)).willReturn(1);
        String book = bookService.update(1, "bookName3", 2, 2);
        Assertions.assertFalse(book.isEmpty());
        Assertions.assertTrue(book.contains("Book successfully updated"));
    }

    @Test
    void updateIfBookNotExistTest() {
        given(bookDaoJdbc.update(1, "bookName3", 2, 2)).willReturn(0);
        String book = bookService.update(1, "bookName3", 2, 2);
        Assertions.assertFalse(book.isEmpty());
        Assertions.assertTrue(book.contains("Book update failed"));
    }
}