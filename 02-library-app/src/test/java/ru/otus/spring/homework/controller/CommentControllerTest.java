package ru.otus.spring.homework.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.spring.homework.model.dto.CommentDto;
import ru.otus.spring.homework.model.entity.Book;
import ru.otus.spring.homework.model.entity.Comment;
import ru.otus.spring.homework.service.CommentService;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(CommentController.class)
class CommentControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CommentService commentService;

    @Test
    void getCommentsTest() throws Exception {
        List<CommentDto> comments = List.of(new CommentDto(1, 1, "book", "comment1"));

        given(commentService.getAll()).willReturn(comments);

        mvc.perform(get("/comments"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(view().name("comments"))
                .andExpect(model().attribute("comments", hasSize(1)));
    }

    @Test
    void getCommentTest() throws Exception {
        CommentDto comment = new CommentDto(1, 1, "book", "comment1");

        given(commentService.getById(1)).willReturn(comment);

        mvc.perform(get("/comment/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(view().name("comments"))
                .andExpect(model().attribute("comments", hasSize(1)));
    }

    @Test
    void getBookCommentsTest() throws Exception {
        List<CommentDto> comments = List.of(new CommentDto(1, 1, "book", "comment1"));

        given(commentService.getByBookId(1)).willReturn(comments);

        mvc.perform(get("/book-comments?bookId=1"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(view().name("comments"))
                .andExpect(model().attribute("comments", hasSize(1)));
    }

    @Test
    void getCreatePageTest() throws Exception {
        mvc.perform(get("/create-comment?bookId=1"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(view().name("create-comment"));
    }

    @Test
    void getEditPageTest() throws Exception {
        CommentDto comment = new CommentDto(1, 1, "book", "comment1");

        given(commentService.getById(1)).willReturn(comment);

        mvc.perform(get("/edit-comment?id=1"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(view().name("edit-comment"));
    }

    @Test
    void deleteCommentTest() throws Exception {
        mvc.perform(get("/delete-comment?id=1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/comments"));
    }

    @Test
    void createCommentTest() throws Exception {
        mvc.perform(post("/create-comment"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/comments"));
    }

    @Test
    void editCommentTest() throws Exception {
        mvc.perform(post("/edit-comment"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/comments"));
    }
}