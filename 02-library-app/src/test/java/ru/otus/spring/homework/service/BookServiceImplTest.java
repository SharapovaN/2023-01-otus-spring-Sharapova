package ru.otus.spring.homework.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.spring.homework.exception.BookNotFoundException;
import ru.otus.spring.homework.model.dto.BookDto;
import ru.otus.spring.homework.model.dto.SaveBookDto;
import ru.otus.spring.homework.model.entity.Author;
import ru.otus.spring.homework.model.entity.Book;
import ru.otus.spring.homework.model.entity.Genre;
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

    @Mock
    private GenreService genreService;

    @Test
    void getByIdIfBookExistsTest() {
        given(bookRepository.findById(1L)).willReturn(Optional.of(new Book(1, "bookName",
                new Author(1, "name", "surname"), new Genre(1, "genre"), null)));
        BookDto book = bookService.getById(1);
        Assertions.assertNotNull(book);
        Assertions.assertEquals("bookName", book.getName());
    }

    @Test
    void getByIdIfBookNotExistsTest() {
        given(bookRepository.findById(1L)).willReturn(Optional.empty());
        Assertions.assertThrows(BookNotFoundException.class, () -> bookService.getById(1));
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
        given(bookRepository.findAll()).willReturn(books);
        Assertions.assertEquals(2, bookService.getAll().size());
    }

    @Test
    void createIfOkTest() {
        Book newBook = new Book("name");
        newBook.setGenre(new Genre(1));
        newBook.setAuthor(new Author(1));

        given(bookRepository.save(newBook))
                .willReturn(new Book(3, "name"));
        given(authorService.getById(1)).willReturn(new Author(1));
        given(genreService.getById(1)).willReturn(new Genre(1));

        SaveBookDto bookDto = new SaveBookDto();
        bookDto.setName("name");
        bookDto.setAuthorId(1L);
        bookDto.setGenreId(1L);

        Book book = bookService.create(bookDto);
        Assertions.assertEquals("name", book.getBookName());
        Assertions.assertNotNull(book);
    }

    @Test
    void updateIfBookExistTest() {
        Book bookToUpdate = new Book(1, "bookName3");
        bookToUpdate.setGenre(new Genre(2));
        bookToUpdate.setAuthor(new Author(2));

        given(bookRepository.save(bookToUpdate))
                .willReturn(new Book(1, "bookName3"));
        given(authorService.getById(2)).willReturn(new Author(2));
        given(genreService.getById(2)).willReturn(new Genre(2));
        given(bookRepository.findById(1L)).willReturn(Optional.of(bookToUpdate));

        SaveBookDto bookDto = new SaveBookDto();
        bookDto.setId(1L);
        bookDto.setName("bookName3");
        bookDto.setAuthorId(2L);
        bookDto.setGenreId(2L);

        Book book = bookService.update(bookDto);
        Assertions.assertEquals("bookName3", book.getBookName());
        Assertions.assertNotNull(book);
    }

    @Test
    void updateIfBookNotExistTest() {
        given(bookRepository.findById(1L)).willReturn(Optional.empty());

        SaveBookDto bookDto = new SaveBookDto();
        bookDto.setId(1L);
        bookDto.setName("bookName3");
        bookDto.setAuthorId(2L);
        bookDto.setGenreId(2L);

        Book book = bookService.update(bookDto);
        Assertions.assertNull(book);
    }
}