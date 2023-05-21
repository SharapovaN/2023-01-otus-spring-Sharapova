package ru.otus.spring.homework.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.spring.homework.model.dto.AuthorDto;
import ru.otus.spring.homework.service.AuthorService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class AuthorController {

    private final AuthorService authorService;

    @GetMapping("/author")
    public List<AuthorDto> getAllAuthors() {
        return authorService.getAuthorsList();
    }
}
