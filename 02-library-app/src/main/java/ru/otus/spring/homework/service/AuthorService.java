package ru.otus.spring.homework.service;

import ru.otus.spring.homework.model.dto.AuthorDto;
import ru.otus.spring.homework.model.entity.Author;

import java.util.List;

public interface AuthorService {
    Author getById(long id);

    List<AuthorDto> getAuthorsList();
}
