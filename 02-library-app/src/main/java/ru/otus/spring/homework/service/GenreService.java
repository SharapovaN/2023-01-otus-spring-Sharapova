package ru.otus.spring.homework.service;

import ru.otus.spring.homework.model.dto.GenreDto;
import ru.otus.spring.homework.model.entity.Genre;

import java.util.List;

public interface GenreService {
    Genre getById(long id);

    List<GenreDto> getGenresList();
}
