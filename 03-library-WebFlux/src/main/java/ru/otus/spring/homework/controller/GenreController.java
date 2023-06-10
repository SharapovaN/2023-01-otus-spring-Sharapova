package ru.otus.spring.homework.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import ru.otus.spring.homework.model.dto.GenreDto;
import ru.otus.spring.homework.repository.GenreRepository;
import ru.otus.spring.homework.utils.ModelConverter;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class GenreController {

    private final GenreRepository genreRepository;

    @GetMapping("/genre")
    public Flux<GenreDto> getAllGenres() {
        return genreRepository.findAll().map(ModelConverter::toGenreDto);
    }
}
