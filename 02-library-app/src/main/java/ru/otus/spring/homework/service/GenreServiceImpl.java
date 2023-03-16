package ru.otus.spring.homework.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.homework.model.Genre;
import ru.otus.spring.homework.repository.GenreRepository;

@AllArgsConstructor
@Service
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;

    @Override
    public Genre getById(long id) {
        return genreRepository.findById(id).orElse(null);
    }
}
