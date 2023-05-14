package ru.otus.spring.homework.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.homework.model.dto.AuthorDto;
import ru.otus.spring.homework.model.entity.Author;
import ru.otus.spring.homework.repository.AuthorRepository;
import ru.otus.spring.homework.utils.ModelConverter;

import java.util.List;

@AllArgsConstructor
@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    @Override
    public Author getById(long id) {
        return authorRepository.findById(id).orElse(null);
    }

    @Override
    public List<AuthorDto> getAuthorsList() {
        return authorRepository.findAll().stream().map(ModelConverter::toAuthorDto).toList();
    }
}
