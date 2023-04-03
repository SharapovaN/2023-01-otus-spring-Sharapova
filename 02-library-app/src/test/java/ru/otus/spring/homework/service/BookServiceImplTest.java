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
import ru.otus.spring.homework.repository.BookRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class BookServiceImplTest {

    @InjectMocks
    private BookServiceImpl bookService;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private AuthorService authorService;

    @Test
    void getByIdIfBookExistsTest() {
        given(bookRepository.findById("642414e251c1e2380fb49ab8")).willReturn(Optional.of(new Book("642414e251c1e2380fb49ab8",
                "Captains daughter", new Author("Aleksandr", "Pushkin"), new Genre("Historical Novel"), null)));
        String book = bookService.getById("642414e251c1e2380fb49ab8");
        Assertions.assertTrue(book.contains("Captains daughter"));
    }

    @Test
    void getByIdIfBookNotExistsTest() {
        given(bookRepository.findById("642414e251c1e2380fb49ab8")).willReturn(Optional.empty());
        String book = bookService.getById("642414e251c1e2380fb49ab8");
        Assertions.assertTrue(book.contains("not found"));
    }

    @Test
    void getAllIfBooksExistsTest() {
        List<Book> books = new ArrayList<>();
        books.add(new Book("642414e251c1e2380fb49ab8", "Captains daughter",
                new Author("Aleksandr", "Pushkin"), new Genre("Historical Novel"), null));
        books.add(new Book("642414e251c1e2380fb49abb", "Lord Of The Rings",
                new Author("John", "Tolkien"), new Genre("Fantasy"), null));
        given(bookRepository.findAll()).willReturn(books);
        Assertions.assertEquals(2, bookService.getAll().size());
    }

    @Test
    void createIfOkTest() {
        given(authorService.getById("642414e251c1e2380fb49ab1")).willReturn(new Author("Aleksandr", "Pushkin"));
        Book newBook = new Book("new book",
                new Author("Aleksandr", "Pushkin"), new Genre("Historical Novel"));
        newBook.setId(null);
        given(bookRepository.save(newBook)).willReturn(newBook);
        String book = bookService.create("new book", "642414e251c1e2380fb49ab1", "Historical Novel");
        Assertions.assertTrue(book.contains("Book successfully created"));
    }

    @Test
    void createIfAuthorNotFoundTest() {
        given(authorService.getById("642414e251c1e2380fb49ab1")).willReturn(null);
        String book = bookService.create("new book", "642414e251c1e2380fb49ab1", "Historical Novel");
        Assertions.assertTrue(book.contains("Book creation failed, unknown authorId"));
    }

    @Test
    void deleteByIdIfBookExistTest() {
        given(bookRepository.existsById("642414e251c1e2380fb49ab8")).willReturn(true);
        String book = bookService.deleteById("642414e251c1e2380fb49ab8");
        Assertions.assertTrue(book.contains("Book successfully deleted"));
    }

    @Test
    void deleteByIdIfBookNotExistTest() {
        given(bookRepository.existsById("642414e251c1e2380fb49ab8")).willReturn(false);
        String book = bookService.deleteById("642414e251c1e2380fb49ab8");
        Assertions.assertTrue(book.contains("Book delete failed"));
    }

    @Test
    void updateIfBookExistTest() {
        Book bookToUpdate = new Book("bookName3");
        bookToUpdate.setId("642414e251c1e2380fb49ab8");
        bookToUpdate.setGenre(new Genre("Historical Novel"));
        bookToUpdate.setAuthor(new Author("Aleksandr", "Pushkin"));
        given(bookRepository.save(bookToUpdate))
                .willReturn(new Book("bookName3"));
        given(bookRepository.findById("642414e251c1e2380fb49ab8")).willReturn(Optional.of(bookToUpdate));
        String book = bookService.update("642414e251c1e2380fb49ab8", "bookName3", "642414e251c1e2380fb49ab1",
                "Historical Novel");
        Assertions.assertTrue(book.contains("Book successfully updated"));
    }

    @Test
    void updateIfBookNotExistTest() {
        given(bookRepository.findById("642414e251c1e2380fb49ab8")).willReturn(Optional.empty());
        String book = bookService.update("642414e251c1e2380fb49ab8", "bookName3", "642414e251c1e2380fb49ab1",
                "Historical Novel");
        Assertions.assertTrue(book.contains("Book update failed"));
    }
}