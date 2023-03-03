package ru.otus.spring.homework.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.homework.model.Book;
import ru.otus.spring.homework.repository.BookDaoJdbc;
import ru.otus.spring.homework.utils.StringUtils;

@AllArgsConstructor
@Service
public class BookServiceImpl implements BookService {

    private final BookDaoJdbc bookDaoJdbc;

    @Override
    public String getById(long id) {
        Book book = bookDaoJdbc.getById(id);
        return book != null ? book.toString() : StringUtils.BOOK_NOT_FOUND_RESPONSE;
    }

    @Override
    public String create(String bookName, long authorId, long genreId) {
        int createdBooks = bookDaoJdbc.insert(bookName, authorId, genreId);
        return createdBooks > 0 ? StringUtils.BOOK_CREATED_RESPONSE : StringUtils.BOOK_NOT_CREATED_RESPONSE;
    }

    @Override
    public String deleteById(long id) {
        int deletedBooks = bookDaoJdbc.deleteById(id);
        return deletedBooks > 0 ? StringUtils.BOOK_DELETE_RESPONSE : StringUtils.BOOK_NOT_DELETE_RESPONSE;
    }

    @Override
    public String update(long id, String bookName, long authorId, long genreId) {
        int updatedBooks = bookDaoJdbc.update(id, bookName, authorId, genreId);
        return updatedBooks > 0 ? StringUtils.BOOK_UPDATED_RESPONSE : StringUtils.BOOK_NOT_UPDATED_RESPONSE;
    }
}
