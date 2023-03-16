package ru.otus.spring.homework.repository;

import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.spring.homework.model.Genre;

import java.util.Optional;

@AllArgsConstructor
@Component
public class GenreRepositoryImpl implements GenreRepository {

    private final EntityManager em;

    @Override
    public Optional<Genre> findById(long id) {
        return Optional.ofNullable(em.find(Genre.class, id));
    }
}
