package ru.otus.spring.homework.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.spring.homework.model.dto.CommentDto;
import ru.otus.spring.homework.service.CommentService;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CommentController.class)
@EnableGlobalMethodSecurity(prePostEnabled = true)
class CommentControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CommentService commentService;

    @WithMockUser(
            username = "user",
            authorities = {"ROLE_USER"}
    )
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

    @WithMockUser(
            username = "user",
            authorities = {"ROLE_USER"}
    )
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

    @WithMockUser(
            username = "user",
            authorities = {"ROLE_USER"}
    )
    @Test
    void getBookCommentsTest() throws Exception {
        List<CommentDto> comments = List.of(new CommentDto(1, 1, "book", "comment1"));

        given(commentService.getByBookId(1)).willReturn(comments);

        mvc.perform(get("/book/comments?bookId=1"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(view().name("comments"))
                .andExpect(model().attribute("comments", hasSize(1)));
    }

    @WithMockUser(
            username = "user",
            authorities = {"ROLE_USER"}
    )
    @Test
    void getCreatePageTest() throws Exception {
        mvc.perform(get("/comment/create?bookId=1"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(view().name("create-comment"));
    }

    @WithMockUser(
            username = "user",
            authorities = {"ROLE_USER"}
    )
    @Test
    void getEditPageTest() throws Exception {
        CommentDto comment = new CommentDto(1, 1, "book", "comment1");

        given(commentService.getById(1)).willReturn(comment);

        mvc.perform(get("/comment/edit?id=1"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(view().name("edit-comment"));
    }

    @WithMockUser(
            username = "user",
            authorities = {"ROLE_ADMIN"}
    )
    @Test
    void deleteCommentTest() throws Exception {
        mvc.perform(get("/comment/delete?id=1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/comments"));
    }

    @WithMockUser(
            username = "user",
            authorities = {"ROLE_USER"}
    )
    @Test
    void createCommentTest() throws Exception {
        mvc.perform(post("/comment/create").with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/comments"));
    }

    @WithMockUser(
            username = "user",
            authorities = {"ROLE_USER"}
    )
    @Test
    void editCommentTest() throws Exception {
        mvc.perform(post("/comment/edit").with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/comments"));
    }

    @Test
    void getCommentUnauthorizedTest() throws Exception {
        CommentDto comment = new CommentDto(1, 1, "book", "comment1");

        given(commentService.getById(1)).willReturn(comment);

        mvc.perform(get("/comment/1"))
                .andExpect(status().is4xxClientError());
    }

    @WithMockUser(
            username = "user",
            authorities = {"ROLE_ADMIN"}
    )
    @Test
    void getEditPageWrongRoleTest() throws Exception {
        CommentDto comment = new CommentDto(1, 1, "book", "comment1");

        given(commentService.getById(1)).willReturn(comment);

        mvc.perform(get("/comment/edit?id=1"))
                .andExpect(status().is4xxClientError());
    }
}