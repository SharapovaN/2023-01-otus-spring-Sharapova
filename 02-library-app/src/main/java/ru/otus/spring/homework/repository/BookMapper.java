package ru.otus.spring.homework.repository;

import org.springframework.jdbc.core.RowMapper;
import ru.otus.spring.homework.model.Author;
import ru.otus.spring.homework.model.Book;
import ru.otus.spring.homework.model.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BookMapper implements RowMapper<Book> {

    @Override
    public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Book(rs.getLong("id"), rs.getString("book_name"),
                new Author(rs.getLong("author_id"), rs.getString("author_name"),
                        rs.getString("author_surname")),
                new Genre(rs.getLong("genre_id"), rs.getString("genre_name")));
    }
}
