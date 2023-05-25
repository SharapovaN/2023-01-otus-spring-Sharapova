package ru.otus.spring.homework.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.spring.homework.model.dto.BookDto;
import ru.otus.spring.homework.model.dto.SaveBookDto;
import ru.otus.spring.homework.model.entity.Book;
import ru.otus.spring.homework.repository.AuthorRepository;
import ru.otus.spring.homework.repository.BookRepository;
import ru.otus.spring.homework.repository.GenreRepository;
import ru.otus.spring.homework.utils.ModelConverter;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class BookController {

    private final BookRepository bookRepository;

    private final AuthorRepository authorRepository;

    private final GenreRepository genreRepository;

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
        return bookRepository.save(toBook(bookDto)).map(ModelConverter::toBookDto);
    }

    @DeleteMapping("/book/{id}")
    public void delete(@PathVariable("id") String id) {
        bookRepository.findById(id).doOnNext(bookRepository::delete).subscribe();
    }

    private Book toBook(SaveBookDto bookDto) {
        Book book = new Book(bookDto.getName());
        book.setId(book.getId());
        authorRepository.findById(bookDto.getAuthorId()).doOnNext(book::setAuthor).subscribe();
        genreRepository.findById(bookDto.getGenreId()).doOnNext(book::setGenre).subscribe();
        return book;
    }
}
