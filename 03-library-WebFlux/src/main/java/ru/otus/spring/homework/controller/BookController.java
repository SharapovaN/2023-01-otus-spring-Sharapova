package ru.otus.spring.homework.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.otus.spring.homework.model.dto.BookDto;
import ru.otus.spring.homework.model.dto.SaveBookDto;
import ru.otus.spring.homework.service.BookService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200",
        methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class BookController {

    private final BookService bookService;

    @GetMapping("/book")
    public List<BookDto> getAllBooks() {
        return null;
    }

    @GetMapping("/book/{id}")
    public BookDto getBook(@PathVariable("id") Long id) {
        return null;
    }

    @GetMapping("/book/{id}/comments")
    public BookDto getBookWithComments(@PathVariable("id") long id) {
        return null;
    }

    @PostMapping("/book")
    public BookDto create(@RequestBody SaveBookDto bookDto) {
        return null;
    }

    @PutMapping("/book")
    public BookDto edit(@RequestBody SaveBookDto bookDto) {
        return null;
    }

    @DeleteMapping("/book/{id}")
    public void delete(@PathVariable("id") long id) {

    }
}
