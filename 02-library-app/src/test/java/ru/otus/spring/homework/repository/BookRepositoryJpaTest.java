package ru.otus.spring.homework.repository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.spring.homework.model.Book;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(BookRepositoryJpa.class)
class BookRepositoryJpaTest {

    @Autowired
    BookRepositoryJpa bookRepositoryJpa;

    @Autowired
    private TestEntityManager em;

    @Test
    void getByIdIfDataExistsTest() {
        Optional<Book> optionalActualBook = bookRepositoryJpa.findById(1);
        Book expectedBook = em.find(Book.class, 1);
        assertThat(optionalActualBook).isPresent().get()
                .usingRecursiveComparison().isEqualTo(expectedBook);
    }

    @Test
    void getByIdIfDataNotExistsTest() {
        Optional<Book> optionalActualBook = bookRepositoryJpa.findById(3);
        Assertions.assertFalse(optionalActualBook.isPresent());
    }

    @Test
    void getAllBooksTest() {
        List<Book> books = bookRepositoryJpa.findAll();
        Assertions.assertEquals(2, books.size());
        Assertions.assertEquals("authorName1", books.get(0).getAuthor().getAuthorName());
        Assertions.assertEquals("genreName1", books.get(0).getGenre().getGenreName());
    }

    @Test
    void saveBookIfOkTest() {
        Book savedBook = bookRepositoryJpa.saveOrUpdate(new Book("bookName3"), 1, 1);
        Book expectedBook = em.find(Book.class, 3);
        assertThat(savedBook)
                .usingRecursiveComparison().isEqualTo(expectedBook);

    }

    @Test
    void saveBookIfNotOkTest() {
        Assertions.assertThrows(PersistenceException.class,
                () -> bookRepositoryJpa.saveOrUpdate(new Book("bookName3"), 3, 3));
    }

    @Test
    void deleteByIdIfExistsTest() {
        int deletedRowsCount = bookRepositoryJpa.deleteById(1);
        Assertions.assertFalse(bookRepositoryJpa.findById(1).isPresent());
        Assertions.assertEquals(1, deletedRowsCount);
    }

    @Test
    void deleteByIdIfNotExistsTest() {
        int deletedRowsCount = bookRepositoryJpa.deleteById(3);
        Assertions.assertEquals(0, deletedRowsCount);
    }

    @Test
    void updateIfExistsTest() {
        bookRepositoryJpa.saveOrUpdate(new Book(1, "bookName3"), 2, 2);
        Book loadedBook = em.find(Book.class, 1);
        Assertions.assertEquals("bookName3", loadedBook.getBookName());
        Assertions.assertEquals(2, loadedBook.getAuthor().getId());
        Assertions.assertEquals(2, loadedBook.getGenre().getId());
    }

    @Test
    void updateIfWrongDataExistsTest() {
        Assertions.assertThrows(EntityNotFoundException.class, () ->
                bookRepositoryJpa.saveOrUpdate(new Book(3, "bookName3"), 3, 3));
    }
}