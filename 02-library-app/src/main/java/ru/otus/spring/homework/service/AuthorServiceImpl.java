package ru.otus.spring.homework.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.homework.model.entity.Author;
import ru.otus.spring.homework.repository.AuthorRepository;

import java.util.List;

@AllArgsConstructor
@Service
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;

    @Override
    public Author getById(String id) {
        return authorRepository.findById(id).orElse(null);
    }

    @Override
    public List<Author> getAuthorsList() {
        return authorRepository.findAll();
    }
}
