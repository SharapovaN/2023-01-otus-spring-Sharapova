package ru.otus.spring.homework.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import ru.otus.spring.homework.model.dto.AuthorDto;
import ru.otus.spring.homework.repository.AuthorRepository;
import ru.otus.spring.homework.utils.ModelConverter;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class AuthorController {
    private final AuthorRepository authorRepository;

    @GetMapping("/author")
    public Flux<AuthorDto> getAllAuthors() {
        return authorRepository.findAll().map(ModelConverter::toAuthorDto);
    }
}
