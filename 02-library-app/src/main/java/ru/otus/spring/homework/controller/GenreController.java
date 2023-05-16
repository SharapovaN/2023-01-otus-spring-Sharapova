package ru.otus.spring.homework.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.spring.homework.model.dto.GenreDto;
import ru.otus.spring.homework.service.GenreService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class GenreController {

    private final GenreService genreService;

    @GetMapping("/genre")
    public List<GenreDto> getAllGenres() {
        return genreService.getGenresList();
    }
}
