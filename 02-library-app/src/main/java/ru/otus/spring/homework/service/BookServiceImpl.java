package ru.otus.spring.homework.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.homework.model.Author;
import ru.otus.spring.homework.model.Book;
import ru.otus.spring.homework.model.Comment;
import ru.otus.spring.homework.model.Genre;
import ru.otus.spring.homework.repository.BookRepository;
import ru.otus.spring.homework.utils.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@AllArgsConstructor
@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    @Override
    public List<String> getAll() {
        List<String> books = bookRepository.findAll().stream().map(this::getBookInfo).toList();
        return books.size() != 0 ? books : new ArrayList<>(Collections.singleton(StringUtils.BOOKS_NOT_FOUND_RESPONSE));
    }

    @Override
    public String getById(String id) {
        Optional<Book> book = bookRepository.findById(id);
        return book.map(this::getBookInfo).orElse(StringUtils.BOOK_NOT_FOUND_RESPONSE);
    }

    @Override
    public List<String> getCommentsForBook(String id) {
        Set<Comment> comments = bookRepository.getBookCommentsById(id);
        return comments.isEmpty() ? new ArrayList<>(Collections.singleton(StringUtils.COMMENTS_NOT_FOUND_RESPONSE)) :
                comments.stream().map(Comment::getComment).toList();
    }

    @Override
    public String create(String bookName, String authorName, String genreName) {
        Book newBook = new Book(bookName);
        newBook.setId(null);
        newBook.setAuthor(new Author(authorName));
        newBook.setGenre(new Genre(genreName));
        Book book = bookRepository.save(newBook);
        return StringUtils.BOOK_CREATED_RESPONSE + book.getId();
    }

    @Override
    public String deleteById(String id) {
        if (checkBookExists(id)) {
            Book book = new Book();
            book.setId(id);
            bookRepository.delete(book);
            return StringUtils.BOOK_DELETE_RESPONSE;
        }
        return StringUtils.BOOK_NOT_DELETE_RESPONSE;
    }

    @Override
    public String update(String id, String bookName, String authorName, String genreName) {
        Optional<Book> optionalBook = bookRepository.findById(id);
        if (optionalBook.isPresent()) {
            Book updateBook = optionalBook.get();
            updateBook.setBookName(bookName);
            updateBook.setAuthor(new Author(authorName));
            updateBook.setGenre(new Genre(genreName));
            bookRepository.save(updateBook);
            return StringUtils.BOOK_UPDATED_RESPONSE;
        }
        return StringUtils.BOOK_NOT_UPDATED_RESPONSE;
    }

    @Override
    public boolean checkBookExists(String id) {
        return bookRepository.existsById(id);
    }

    @Override
    public Book getBookById(String id) {
        return bookRepository.findById(id).orElse(null);
    }


    private String getBookInfo(Book book) {
        return "id = " + book.getId() + ", name = " + book.getBookName() +
                ", author = " + book.getAuthor().getAuthorName() +
                ", genre = " + book.getGenre().getGenreName();
    }

}
