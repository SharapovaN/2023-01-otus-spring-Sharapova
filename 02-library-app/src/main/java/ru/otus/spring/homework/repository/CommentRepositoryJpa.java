package ru.otus.spring.homework.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.spring.homework.model.Comment;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Component
public class CommentRepositoryJpa implements CommentRepository {

    @PersistenceContext
    private final EntityManager em;

    @Override
    public List<Comment> findAll() {
        TypedQuery<Comment> query = em.createQuery("SELECT c FROM Comment c", Comment.class);
        return query.getResultList();
    }

    @Override
    public Optional<Comment> findById(long id) {
        return Optional.ofNullable(em.find(Comment.class, id));
    }

    @Override
    public Comment saveOrUpdate(Comment comment) {
        if (comment.getId() <= 0) {
            em.persist(comment);
            return comment;
        } else {
            return em.merge(comment);
        }
    }

    @Override
    public void delete(Comment comment) {
        em.remove(em.contains(comment) ? comment : em.merge(comment));
    }
}
