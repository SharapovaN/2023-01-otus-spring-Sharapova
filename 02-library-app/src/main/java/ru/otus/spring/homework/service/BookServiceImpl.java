package ru.otus.spring.homework.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.homework.model.Book;
import ru.otus.spring.homework.model.Comment;
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
    private final AuthorService authorService;
    private final GenreService genreService;

    @Override
    public List<String> getAll() {
        List<String> books = bookRepositoryJpa.findAll().stream().map(this::getBookInfo).toList();
        return books.size() != 0 ? books : new ArrayList<>(Collections.singleton(StringUtils.BOOKS_NOT_FOUND_RESPONSE));
    }

    @Override
    public String getById(long id) {
        Optional<Book> book = bookRepositoryJpa.findById(id);
        return book.map(this::getBookInfo).orElse(StringUtils.BOOK_NOT_FOUND_RESPONSE);
    }

    @Override
    @Transactional
    public List<String> getCommentsForBook(long id) {
        Optional<Book> optionalBook = bookRepositoryJpa.findById(id);
        return optionalBook.map(book -> book.getComments().stream().map(Comment::toString).toList())
                .orElse(new ArrayList<>(Collections.singleton(StringUtils.COMMENTS_NOT_FOUND_RESPONSE)));
    }

    @Transactional
    @Override
    public String create(String bookName, long authorId, long genreId) {
        Book book = new Book(bookName);
        book.setAuthor(authorService.getById(authorId));
        book.setGenre(genreService.getById(genreId));
        bookRepositoryJpa.saveOrUpdate(book);
        return StringUtils.BOOK_CREATED_RESPONSE;
    }

    @Transactional
    @Override
    public String deleteById(long id) {
        if (checkBookExists(id)) {
            bookRepositoryJpa.delete(new Book(id));
            return StringUtils.BOOK_DELETE_RESPONSE;
        }
        return StringUtils.BOOK_NOT_DELETE_RESPONSE;
    }

    @Transactional
    @Override
    public String update(long id, String bookName, long authorId, long genreId) {
        Optional<Book> optionalBook = bookRepositoryJpa.findById(id);
        if (optionalBook.isPresent()) {
            Book updateBook = optionalBook.get();
            updateBook.setBookName(bookName);
            updateBook.setAuthor(authorService.getById(authorId));
            updateBook.setGenre(genreService.getById(genreId));
            bookRepositoryJpa.saveOrUpdate(updateBook);
            return StringUtils.BOOK_UPDATED_RESPONSE;
        }
        return StringUtils.BOOK_NOT_UPDATED_RESPONSE;
    }

    @Override
    public boolean checkBookExists(long id) {
        return bookRepositoryJpa.checkBookExists(id);
    }

    private String getBookInfo(Book book) {
        return "id = " + book.getId() + ", name = " + book.getBookName() +
                ", author = " + book.getAuthor().getAuthorName() + " " + book.getAuthor().getAuthorSurname() +
                ", genre = " + book.getGenre().getGenreName();
    }

}
