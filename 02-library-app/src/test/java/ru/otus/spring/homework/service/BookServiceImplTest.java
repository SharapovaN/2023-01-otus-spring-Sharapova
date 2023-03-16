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

    @Mock
    private AuthorService authorService;

    @Mock
    private GenreService genreService;

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
        Book newBook = new Book("name");
        newBook.setGenre(new Genre(1));
        newBook.setAuthor(new Author(1));
        given(bookRepositoryJpa.saveOrUpdate(newBook))
                .willReturn(new Book(3, "name"));
        given(authorService.getById(1)).willReturn(new Author(1));
        given(genreService.getById(1)).willReturn(new Genre(1));
        String book = bookService.create("name", 1, 1);
        Assertions.assertTrue(book.contains("Book successfully created"));
    }

    @Test
    void deleteByIdIfBookExistTest() {
        given(bookRepositoryJpa.checkBookExists(1)).willReturn(true);
        String book = bookService.deleteById(1);
        Assertions.assertTrue(book.contains("Book successfully deleted"));
    }

    @Test
    void deleteByIdIfBookNotExistTest() {
        given(bookRepositoryJpa.checkBookExists(1)).willReturn(false);
        String book = bookService.deleteById(1);
        Assertions.assertTrue(book.contains("Book delete failed"));
    }

    @Test
    void updateIfBookExistTest() {
        Book bookToUpdate = new Book(1, "bookName3");
        bookToUpdate.setGenre(new Genre(2));
        bookToUpdate.setAuthor(new Author(2));
        given(bookRepositoryJpa.saveOrUpdate(bookToUpdate))
                .willReturn(new Book(1, "bookName3"));
        given(authorService.getById(2)).willReturn(new Author(2));
        given(genreService.getById(2)).willReturn(new Genre(2));
        given(bookRepositoryJpa.findById(1)).willReturn(Optional.of(bookToUpdate));
        String book = bookService.update(1, "bookName3", 2, 2);
        Assertions.assertTrue(book.contains("Book successfully updated"));
    }

    @Test
    void updateIfBookNotExistTest() {
        given(bookRepositoryJpa.findById(3)).willReturn(Optional.empty());
        String book = bookService.update(3, "bookName3", 2, 2);
        Assertions.assertTrue(book.contains("Book update failed"));
    }
}