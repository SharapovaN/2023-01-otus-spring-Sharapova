package ru.otus.spring.homework.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.otus.spring.homework.model.Book;

@JdbcTest
@Import(BookDaoJdbc.class)
class BookDaoJdbcTest {

    @Autowired
    BookDaoJdbc bookDaoJdbc;

    @Test
    void getByIdIfDataExistsTest() {
        Book book = bookDaoJdbc.getById(1);
        Assertions.assertNotNull(book);
        Assertions.assertNotNull(book.getAuthor());
        Assertions.assertNotNull(book.getGenre());
        Assertions.assertEquals("bookName1", book.getBookName());
    }

    @Test
    void getByIdIfDataNotExistsTest() {
        Book book = bookDaoJdbc.getById(3);
        Assertions.assertNull(book);
    }

    @Test
    void insertBookIfOkTest() {
        int insertedRowCount = bookDaoJdbc.insert("bookName3", 1, 1);
        Assertions.assertEquals(1, insertedRowCount);
        Assertions.assertEquals("bookName3", bookDaoJdbc.getById(3).getBookName());
    }

    @Test
    void deleteByIdIfExistsTest() {
        int deletedRowCount = bookDaoJdbc.deleteById(2);
        Assertions.assertEquals(1, deletedRowCount);
        Assertions.assertNull(bookDaoJdbc.getById(2));
    }

    @Test
    void deleteByIdIfNotExistsTest() {
        int deletedRowCount = bookDaoJdbc.deleteById(4);
        Assertions.assertEquals(0, deletedRowCount);
    }

    @Test
    void updateIfExistsTest() {
        int updatedRowCount = bookDaoJdbc.update(1, "bookName3", 2, 2);
        Assertions.assertEquals(1, updatedRowCount);
        Assertions.assertEquals("bookName3", bookDaoJdbc.getById(1).getBookName());
        Assertions.assertEquals(2, bookDaoJdbc.getById(1).getAuthor().getId());
        Assertions.assertEquals(2, bookDaoJdbc.getById(1).getGenre().getId());
    }

    @Test
    void updateIfNotExistsTest() {
        int updatedRowCount = bookDaoJdbc.update(4, "bookName3", 3, 3);
        Assertions.assertEquals(0, updatedRowCount);
    }
}