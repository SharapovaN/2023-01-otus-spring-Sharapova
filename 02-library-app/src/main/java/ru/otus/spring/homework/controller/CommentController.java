package ru.otus.spring.homework.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.spring.homework.model.dto.CommentDto;
import ru.otus.spring.homework.service.CommentService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @GetMapping("/comments")
    public String getComments(Model model) {
        List<CommentDto> comments = commentService.getAll();
        model.addAttribute("comments", comments);
        return "comments";
    }

    @GetMapping("/comment")
    public String getComment(@RequestParam("id") long id, Model model) {
        List<CommentDto> comments = List.of(commentService.getById(id));
        model.addAttribute("comments", comments);
        return "comments";
    }

    @GetMapping("/book-comments")
    public String getBookComments(@RequestParam("bookId") long bookId, Model model) {
        List<CommentDto> comments = commentService.getByBookId(bookId);
        model.addAttribute("comments", comments);
        return "comments";
    }

    @GetMapping("/create-comment")
    public String getCreatePge(@RequestParam("bookId") long bookId, Model model) {
        CommentDto comment = new CommentDto();
        comment.setBookId(bookId);
        model.addAttribute("comment", comment);
        return "create-comment";
    }

    @GetMapping("/edit-comment")
    public String getEditPge(@RequestParam("id") long id, Model model) {
        CommentDto comment = commentService.getById(id);
        model.addAttribute("comment", comment);
        return "edit-comment";
    }

    @GetMapping("/delete-comment")
    public String deleteComment(@RequestParam("id") long id) {
        commentService.deleteById(id);
        return "redirect:/comments";
    }

    @PostMapping("/create-comment")
    public String createComment(CommentDto commentDto) {
        commentService.create(commentDto);
        return "redirect:/comments";
    }

    @PostMapping("/edit-comment")
    public String editComment(CommentDto commentDto) {
        commentService.update(commentDto);
        return "redirect:/comments";
    }
}
