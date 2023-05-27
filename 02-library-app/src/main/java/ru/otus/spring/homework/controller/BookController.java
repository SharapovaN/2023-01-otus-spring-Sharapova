package ru.otus.spring.homework.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.spring.homework.model.dto.BookDto;
import ru.otus.spring.homework.model.dto.SaveBookDto;
import ru.otus.spring.homework.model.entity.Author;
import ru.otus.spring.homework.model.entity.Genre;
import ru.otus.spring.homework.service.AuthorService;
import ru.otus.spring.homework.service.BookService;
import ru.otus.spring.homework.service.GenreService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;
    private final AuthorService authorService;
    private final GenreService genreService;

    @GetMapping("/")
    public String getAllBooks(Model model) {
        List<BookDto> books = bookService.getAll();
        model.addAttribute("books", books);
        return "books";
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping("/book/{id}")
    public String getBook(@PathVariable("id") Long id, Model model) {
        List<BookDto> books = List.of(bookService.getBookDtoById(id));
        model.addAttribute("books", books);
        return "books";
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping("/book-with-comments/{id}")
    public String getBookWithComments(@PathVariable("id") long id, Model model) {
        BookDto book = bookService.getBookWithComments(id);
        model.addAttribute("book", book);
        return "book-with-comments";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/create")
    public String createBookPage(Model model) {
        List<Author> authors = authorService.getAuthorsList();
        List<Genre> genres = genreService.getGenresList();
        model.addAttribute("book", new SaveBookDto());
        model.addAttribute("authors", authors);
        model.addAttribute("genres", genres);
        return "create";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/edit")
    public String editBookPage(@RequestParam("id") long id, Model model) {
        List<Author> authors = authorService.getAuthorsList();
        List<Genre> genres = genreService.getGenresList();
        SaveBookDto book = bookService.getSaveBookDtoById(id);
        model.addAttribute("book", book);
        model.addAttribute("authors", authors);
        model.addAttribute("genres", genres);
        return "edit";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/delete")
    public String deleteBookPage(@RequestParam("id") long id) {
        bookService.deleteById(id);
        return "redirect:/";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create")
    public String createBook(SaveBookDto book) {
        bookService.create(book);
        return "redirect:/";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/edit")
    public String editBook(SaveBookDto book) {
        bookService.update(book);
        return "redirect:/";
    }
}
