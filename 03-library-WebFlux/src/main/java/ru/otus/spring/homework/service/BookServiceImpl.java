package ru.otus.spring.homework.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.homework.model.Author;
import ru.otus.spring.homework.model.Book;
import ru.otus.spring.homework.model.Comment;
import ru.otus.spring.homework.model.Genre;
import ru.otus.spring.homework.repository.BookRepository;
import ru.otus.spring.homework.utils.StringUtils;

import java.util.*;

@AllArgsConstructor
@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    private final AuthorService authorService;

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
    public String create(String bookName, String authorId, String genreName) {
        Author author = authorService.getById(authorId);
        if (author == null) {
            return StringUtils.BOOK_NOT_CREATED_RESPONSE + authorId;
        }
        Book newBook = new Book(bookName);
        newBook.setId(null);
        newBook.setAuthor(author);
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
    public String update(String id, String bookName, String authorId, String genreName) {
        Optional<Book> optionalBook = bookRepository.findById(id);
        if (optionalBook.isPresent()) {
            Book updateBook = optionalBook.get();
            Author author = authorService.getById(authorId);
            updateBook.setBookName(bookName);
            updateBook.setAuthor(author);
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
                ", author = " + book.getAuthor().getAuthorName() + " " + book.getAuthor().getAuthorSurname() +
                ", genre = " + book.getGenre().getGenreName();
    }

}
