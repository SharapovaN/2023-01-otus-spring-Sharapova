package ru.otus.spring.homework.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.homework.model.dto.GenreDto;
import ru.otus.spring.homework.model.entity.Genre;
import ru.otus.spring.homework.repository.GenreRepository;
import ru.otus.spring.homework.utils.ModelConverter;

import java.util.List;

@AllArgsConstructor
@Service
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;

    @Override
    public Genre getById(long id) {
        return genreRepository.findById(id).orElse(null);
    }

    @Override
    public List<GenreDto> getGenresList() {
        return genreRepository.findAll().stream().map(ModelConverter::toGenreDto).toList();
    }
}
