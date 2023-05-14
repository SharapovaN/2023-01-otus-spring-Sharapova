package ru.otus.spring.homework.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import ru.otus.spring.homework.JsonTestConverter;
import ru.otus.spring.homework.model.dto.CommentDto;
import ru.otus.spring.homework.service.CommentService;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

        MvcResult result = mvc.perform(get("/comment"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)).andReturn();

        String content = result.getResponse().getContentAsString();
        CommentDto[] bookDtos = JsonTestConverter.mapFromJson(content, CommentDto[].class);
        Assertions.assertEquals(1, bookDtos.length);
    }

    @Test
    void getCommentTest() throws Exception {
        CommentDto comment = new CommentDto(1, 1, "book", "comment1");

        given(commentService.getById(1)).willReturn(comment);

        MvcResult result = mvc.perform(get("/comment/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)).andReturn();
        Assertions.assertTrue(result.getResponse().getContentAsString().contains("comment1"));
    }

    @Test
    void getBookCommentsTest() throws Exception {
        List<CommentDto> comments = List.of(new CommentDto(1, 1, "book", "comment1"));

        given(commentService.getByBookId(1)).willReturn(comments);

        MvcResult result = mvc.perform(get("/comment/book/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)).andReturn();

        String content = result.getResponse().getContentAsString();
        CommentDto[] bookDtos = JsonTestConverter.mapFromJson(content, CommentDto[].class);
        Assertions.assertEquals(1, bookDtos.length);
    }

    @Test
    void createTest() throws Exception {
        CommentDto comment = new CommentDto();
        comment.setComment("newComment");

        given(commentService.create(comment)).willReturn(comment);

        MvcResult result = mvc.perform(post("/comment").content(JsonTestConverter.mapToJson(comment))
                .contentType(MediaType.APPLICATION_JSON)).andReturn();
        Assertions.assertTrue(result.getResponse().getContentAsString().contains("newComment"));
    }

    @Test
    void editTest() throws Exception {
        CommentDto comment = new CommentDto(1, 1, "book", "newComment");

        given(commentService.update(comment)).willReturn(comment);

        MvcResult result = mvc.perform(put("/comment").content(JsonTestConverter.mapToJson(comment))
                .contentType(MediaType.APPLICATION_JSON)).andReturn();
        Assertions.assertTrue(result.getResponse().getContentAsString().contains("newComment"));
    }

}