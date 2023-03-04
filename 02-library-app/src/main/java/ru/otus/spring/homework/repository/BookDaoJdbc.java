package ru.otus.spring.homework.repository;

import lombok.AllArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.spring.homework.model.Book;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Repository
public class BookDaoJdbc implements BookDao {

    private final NamedParameterJdbcOperations namedParameterJdbcOperations;

    @Override
    public List<Book> getAll() {
        return namedParameterJdbcOperations.query("SELECT b.id, b.book_name, a.id author_id, a.author_name, " +
                "a.author_surname, g.id genre_id, g.genre_name " +
                "FROM books b JOIN authors a ON a.id = b.author_id " +
                "JOIN genres g ON g.id = b.genre_id", new BookMapper());
    }

    @Override
    public Book getById(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        try {
            return namedParameterJdbcOperations.queryForObject("SELECT b.id, b.book_name, a.id author_id, a.author_name, " +
                    "a.author_surname, g.id genre_id, g.genre_name " +
                    "FROM books b JOIN authors a ON a.id = b.author_id " +
                    "JOIN genres g ON g.id = b.genre_id " +
                    "WHERE b.id = :id", params, new BookMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }

    }

    @Override
    public Integer insert(String bookName, long authorId, long genreId) {
        Map<String, Object> params = Map.of("bookName", bookName,
                "authorId", authorId, "genreId", genreId);
        return namedParameterJdbcOperations.update("INSERT INTO books (book_name, author_id, genre_id) " +
                "VALUES (:bookName, :authorId, :genreId)", params);
    }

    @Override
    public Integer deleteById(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        return namedParameterJdbcOperations.update("DELETE FROM books WHERE id = :id", params);
    }

    @Override
    public Integer update(long id, String bookName, long authorId, long genreId) {
        Map<String, Object> params = Map.of("id", id, "bookName", bookName,
                "authorId", authorId, "genreId", genreId);
        return namedParameterJdbcOperations.update("UPDATE books SET book_name = :bookName, author_id = :authorId, " +
                "genre_id = :genreId WHERE id = :id", params);
    }
}
