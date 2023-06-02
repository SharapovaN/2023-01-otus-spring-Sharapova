package ru.otus.spring.homework.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.spring.homework.model.dto.BookDto;
import ru.otus.spring.homework.model.dto.SaveBookDto;
import ru.otus.spring.homework.model.entity.Author;
import ru.otus.spring.homework.model.entity.Genre;
import ru.otus.spring.homework.repository.BookRepository;
import ru.otus.spring.homework.utils.ModelConverter;

import static ru.otus.spring.homework.utils.ModelConverter.toBook;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class BookController {

    private final BookRepository bookRepository;

    @GetMapping("/book")
    public Flux<BookDto> getAllBooks() {
        return bookRepository.findAll().map(ModelConverter::toBookDto);
    }

    @GetMapping("/book/{id}")
    public Mono<BookDto> getBook(@PathVariable("id") String id) {
        return bookRepository.findById(id).map(ModelConverter::toBookDto);
    }

    @GetMapping("/book/{id}/comments")
    public Mono<BookDto> getBookWithComments(@PathVariable("id") String id) {
        return bookRepository.findById(id).map(ModelConverter::toBookWithCommentsDto);
    }

    @PostMapping("/book")
    public Mono<BookDto> create(@RequestBody SaveBookDto bookDto) {
        return bookRepository.save(toBook(bookDto)).map(ModelConverter::toBookDto);
    }

    @PutMapping("/book")
    public Mono<BookDto> edit(@RequestBody SaveBookDto bookDto) {
        return bookRepository.findById(bookDto.getId()).flatMap(oldBook -> {
            oldBook.setBookName(bookDto.getName());
            oldBook.setAuthor(new Author(bookDto.getAuthorId(), bookDto.getAuthorName(), bookDto.getAuthorSurname()));
            oldBook.setGenre(new Genre(bookDto.getGenreId(), bookDto.getGenreName()));
            oldBook.setComments(oldBook.getComments());
            return bookRepository.save(oldBook);
        }).map(ModelConverter::toBookWithCommentsDto);
    }

    @DeleteMapping("/book/{id}")
    public Mono<Void> delete(@PathVariable("id") String id) {
        return bookRepository.deleteById(id);
    }

}
