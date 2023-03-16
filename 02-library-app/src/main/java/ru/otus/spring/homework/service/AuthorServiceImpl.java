package ru.otus.spring.homework.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.homework.model.Author;
import ru.otus.spring.homework.repository.AuthorRepository;

@AllArgsConstructor
@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    @Override
    public Author getById(long id) {
        return authorRepository.findById(id).orElse(null);
    }
}
