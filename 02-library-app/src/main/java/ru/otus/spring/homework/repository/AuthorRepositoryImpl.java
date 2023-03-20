package ru.otus.spring.homework.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.spring.homework.model.Author;

import java.util.Optional;

@AllArgsConstructor
@Component
public class AuthorRepositoryImpl implements AuthorRepository {

    @PersistenceContext
    private final EntityManager em;

    @Override
    public Optional<Author> findById(long id) {
        return Optional.ofNullable(em.find(Author.class, id));
    }
}
