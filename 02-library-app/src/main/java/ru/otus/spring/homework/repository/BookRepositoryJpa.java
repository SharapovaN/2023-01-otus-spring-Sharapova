package ru.otus.spring.homework.repository;

import jakarta.persistence.EntityGraph;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.spring.homework.model.Author;
import ru.otus.spring.homework.model.Book;
import ru.otus.spring.homework.model.Genre;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Repository
public class BookRepositoryJpa implements BookRepository {

    @PersistenceContext
    private final EntityManager em;

    @Override
    public List<Book> findAll() {
        EntityGraph<?> entityGraph = em.getEntityGraph("book-author-genre-entity-graph");
        TypedQuery<Book> query = em.createQuery("""
                SELECT b FROM Book b
                JOIN b.author
                JOIN b.genre
                LEFT JOIN FETCH b.comments
                """, Book.class);
        query.setHint("javax.persistence.fetchgraph", entityGraph);
        return query.getResultList();
    }

    @Override
    public Optional<Book> findById(long id) {
        return Optional.ofNullable(em.find(Book.class, id));
    }

    @Override
    public Book saveOrUpdate(Book book, long authorId, long genreId) {
        Author author = em.getReference(Author.class, authorId);
        Genre genre = em.getReference(Genre.class, genreId);
        book.setAuthor(author);
        book.setGenre(genre);
        if (book.getId() <= 0) {
            em.persist(book);
            return book;
        } else {
            return em.merge(book);
        }
    }

    @Override
    public Integer deleteById(long id) {
        Query query = em.createQuery("DELETE FROM Book b WHERE b.id = :id");
        query.setParameter("id", id);
        return query.executeUpdate();
    }

    @Override
    public boolean checkBookExists(long id) {
        Query query = em.createNativeQuery("SELECT EXISTS (SELECT * FROM books WHERE id = :id)");
        query.setParameter("id", id);
        return (boolean) query.getSingleResult();
    }
}
