package ru.otus.spring.homework.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.spring.homework.service.BookService;


@ShellComponent
@RequiredArgsConstructor
public class ApplicationCommand {

    private final BookService bookService;

    @ShellMethod(value = "Get book command", key = {"g", "get"})
    public String getBook(long id) {
        return bookService.getById(id);
    }

    @ShellMethod(value = "Create book command", key = {"c", "create"})
    public String createBook(String bookName, long authorId, long genreId) {
        return bookService.create(bookName, authorId, genreId);
    }

    @ShellMethod(value = "Update book command", key = {"u", "update"})
    public String updateBook(long id, String bookName, long authorId, long genreId) {
        return bookService.update(id, bookName, authorId, genreId);
    }

    @ShellMethod(value = "Delete book command", key = {"d", "delete"})
    public String deleteBook(long id) {
        return bookService.deleteById(id);
    }
}
