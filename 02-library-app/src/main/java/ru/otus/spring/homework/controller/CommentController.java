package ru.otus.spring.homework.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.spring.homework.model.dto.CommentDto;
import ru.otus.spring.homework.service.CommentService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping("/comments")
    public String getComments(Model model) {
        List<CommentDto> comments = commentService.getAll();
        model.addAttribute("comments", comments);
        return "comments";
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping("/comment/{id}")
    public String getComment(@PathVariable("id") long id, Model model) {
        List<CommentDto> comments = List.of(commentService.getById(id));
        model.addAttribute("comments", comments);
        return "comments";
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping("/book/comments")
    public String getBookComments(@RequestParam("bookId") long bookId, Model model) {
        List<CommentDto> comments = commentService.getByBookId(bookId);
        model.addAttribute("comments", comments);
        return "comments";
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/comment/create")
    public String getCreatePage(@RequestParam("bookId") long bookId, Model model) {
        CommentDto comment = new CommentDto();
        comment.setBookId(bookId);
        model.addAttribute("comment", comment);
        return "create-comment";
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/comment/edit")
    public String getEditPage(@RequestParam("id") long id, Model model) {
        CommentDto comment = commentService.getById(id);
        model.addAttribute("comment", comment);
        return "edit-comment";
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping("/comment/delete")
    public String deleteComment(@RequestParam("id") long id) {
        commentService.deleteById(id);
        return "redirect:/comments";
    }

    @PostMapping("/comment/create")
    public String createComment(CommentDto commentDto) {
        commentService.create(commentDto);
        return "redirect:/comments";
    }

    @PostMapping("/comment/edit")
    public String editComment(CommentDto commentDto) {
        commentService.update(commentDto);
        return "redirect:/comments";
    }
}
