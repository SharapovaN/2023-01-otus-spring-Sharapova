package ru.otus.spring.homework.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.homework.model.Book;
import ru.otus.spring.homework.repository.BookRepositoryJpa;
import ru.otus.spring.homework.utils.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class BookServiceImpl implements BookService {

    private final BookRepositoryJpa bookRepositoryJpa;

    @Override
    public List<String> getAll() {
        List<String> books = bookRepositoryJpa.findAll().stream().map(Book::toString).toList();
        return books.size() != 0 ? books : new ArrayList<>(Collections.singleton(StringUtils.BOOKS_NOT_FOUND_RESPONSE));
    }

    @Override
    public String getById(long id) {
        Optional<Book> book = bookRepositoryJpa.findById(id);
        return book.isPresent() ? book.get().toString() : StringUtils.BOOK_NOT_FOUND_RESPONSE;
    }

    @Transactional
    @Override
    public String create(String bookName, long authorId, long genreId) {
        bookRepositoryJpa.saveOrUpdate(new Book(bookName), authorId, genreId);
        return StringUtils.BOOK_CREATED_RESPONSE;
    }

    @Transactional
    @Override
    public String deleteById(long id) {
        int deletedBooks = bookRepositoryJpa.deleteById(id);
        return deletedBooks > 0 ? StringUtils.BOOK_DELETE_RESPONSE : StringUtils.BOOK_NOT_DELETE_RESPONSE;
    }

    @Transactional
    @Override
    public String update(long id, String bookName, long authorId, long genreId) {
        Book updatedBook = null;
        if (checkBookExists(id)) {
            updatedBook = bookRepositoryJpa.saveOrUpdate(new Book(id, bookName), authorId, genreId);
        }
        return updatedBook != null ? StringUtils.BOOK_UPDATED_RESPONSE : StringUtils.BOOK_NOT_UPDATED_RESPONSE;
    }

    @Override
    public boolean checkBookExists(long id) {
        return bookRepositoryJpa.checkBookExists(id);
    }
}
