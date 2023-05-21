package ru.otus.spring.homework.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.otus.spring.homework.model.dto.CommentDto;
import ru.otus.spring.homework.service.CommentService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @GetMapping("/comment")
    public List<CommentDto> getComments() {
        return commentService.getAll();
    }

    @GetMapping("/comment/{id}")
    public CommentDto getComment(@PathVariable("id") long id) {
        return commentService.getById(id);
    }

    @GetMapping("/comment/book/{bookId}")
    public List<CommentDto> getBookComments(@PathVariable("bookId") long bookId) {
        return commentService.getByBookId(bookId);
    }

    @PostMapping("/comment")
    public CommentDto create(@RequestBody CommentDto comment) {
        return commentService.create(comment);
    }

    @PutMapping("/comment")
    public CommentDto edit(@RequestBody CommentDto comment) {
        return commentService.update(comment);
    }

    @DeleteMapping("/comment/{id}")
    public void delete(@PathVariable("id") long id) {
        commentService.deleteById(id);
    }

}
