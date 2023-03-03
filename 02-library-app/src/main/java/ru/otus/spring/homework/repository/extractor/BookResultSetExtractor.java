package ru.otus.spring.homework.repository.extractor;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import ru.otus.spring.homework.model.Author;
import ru.otus.spring.homework.model.Book;
import ru.otus.spring.homework.model.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BookResultSetExtractor implements ResultSetExtractor<Book> {

    @Override
    public Book extractData(ResultSet rs) throws SQLException, DataAccessException {
        Book book = null;
        if (rs.next()) {
            book = new Book(rs.getLong("id"), rs.getString("book_name"),
                    new Author(rs.getLong("author_id"), rs.getString("author_name"),
                            rs.getString("author_surname")),
                    new Genre(rs.getLong("genre_id"), rs.getString("genre_name")));
        }
        return book;
    }
}
