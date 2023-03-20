package ru.otus.spring.homework.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.homework.model.Book;
import ru.otus.spring.homework.model.Comment;
import ru.otus.spring.homework.repository.BookRepository;
import ru.otus.spring.homework.utils.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AuthorService authorService;
    private final GenreService genreService;

    @Override
    public List<String> getAll() {
        List<String> books = bookRepository.findAll().stream().map(this::getBookInfo).toList();
        return books.size() != 0 ? books : new ArrayList<>(Collections.singleton(StringUtils.BOOKS_NOT_FOUND_RESPONSE));
    }

    @Override
    public String getById(long id) {
        Optional<Book> book = bookRepository.findById(id);
        return book.map(this::getBookInfo).orElse(StringUtils.BOOK_NOT_FOUND_RESPONSE);
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> getCommentsForBook(long id) {
        Optional<Book> optionalBook = bookRepository.findById(id);
        return optionalBook.map(book -> book.getComments().stream().map(Comment::toString).toList())
                .orElse(new ArrayList<>(Collections.singleton(StringUtils.COMMENTS_NOT_FOUND_RESPONSE)));
    }

    @Transactional
    @Override
    public String create(String bookName, long authorId, long genreId) {
        Book book = new Book(bookName);
        book.setAuthor(authorService.getById(authorId));
        book.setGenre(genreService.getById(genreId));
        bookRepository.save(book);
        return StringUtils.BOOK_CREATED_RESPONSE;
    }

    @Transactional
    @Override
    public String deleteById(long id) {
        if (checkBookExists(id)) {
            bookRepository.delete(new Book(id));
            return StringUtils.BOOK_DELETE_RESPONSE;
        }
        return StringUtils.BOOK_NOT_DELETE_RESPONSE;
    }

    @Transactional
    @Override
    public String update(long id, String bookName, long authorId, long genreId) {
        Optional<Book> optionalBook = bookRepository.findById(id);
        if (optionalBook.isPresent()) {
            Book updateBook = optionalBook.get();
            updateBook.setBookName(bookName);
            updateBook.setAuthor(authorService.getById(authorId));
            updateBook.setGenre(genreService.getById(genreId));
            bookRepository.save(updateBook);
            return StringUtils.BOOK_UPDATED_RESPONSE;
        }
        return StringUtils.BOOK_NOT_UPDATED_RESPONSE;
    }

    @Override
    public boolean checkBookExists(long id) {
        return bookRepository.existsById(id);
    }

    private String getBookInfo(Book book) {
        return "id = " + book.getId() + ", name = " + book.getBookName() +
                ", author = " + book.getAuthor().getAuthorName() + " " + book.getAuthor().getAuthorSurname() +
                ", genre = " + book.getGenre().getGenreName();
    }
}
