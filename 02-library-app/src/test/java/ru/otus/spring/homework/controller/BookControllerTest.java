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
import ru.otus.spring.homework.exception.BookNotFoundException;
import ru.otus.spring.homework.model.dto.BookDto;
import ru.otus.spring.homework.model.dto.SaveBookDto;
import ru.otus.spring.homework.service.BookService;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookController.class)
class BookControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private BookService bookService;

    @Test
    void getAllBooksTest() throws Exception {
        List<BookDto> books = List.of(new BookDto(1, "bookName", "authorName", "genreName", null),
                new BookDto(2, "bookName2", "authorName2", "genreName2", null));

        given(bookService.getAll()).willReturn(books);

        MvcResult result = mvc.perform(get("/book"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)).andReturn();

        String content = result.getResponse().getContentAsString();
        BookDto[] bookDtos = JsonTestConverter.mapFromJson(content, BookDto[].class);
        Assertions.assertEquals(2, bookDtos.length);
    }

    @Test
    void getBookIfOkTest() throws Exception {
        BookDto bookDto = new BookDto(1, "bookName", "authorName", "genreName", null);

        given(bookService.getBookDtoById(1)).willReturn(bookDto);

        MvcResult result = mvc.perform(get("/book/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)).andReturn();
        Assertions.assertTrue(result.getResponse().getContentAsString().contains("authorName"));
    }

    @Test
    void getBookIfNotOkTest() throws Exception {
        given(bookService.getBookDtoById(1)).willThrow(new BookNotFoundException(1L));

        mvc.perform(get("/book/1")).andExpect(status().isNotFound());
    }

    @Test
    void getBookWithCommentsTest() throws Exception {
        List<String> comments = List.of("comment", "comment1");
        BookDto bookDto = new BookDto(1, "bookName", "authorName", "genreName", comments);

        given(bookService.getBookWithComments(1)).willReturn(bookDto);

        MvcResult result = mvc.perform(get("/book/1/comments"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)).andReturn();
        Assertions.assertTrue(result.getResponse().getContentAsString().contains("comment"));
    }

    @Test
    void createTest() throws Exception {
        SaveBookDto book = new SaveBookDto();
        book.setName("newBookName");
        book.setAuthorId(1L);
        book.setGenreId(1L);

        given(bookService.create(book)).willReturn(book);

        MvcResult result = mvc.perform(post("/book").content(JsonTestConverter.mapToJson(book))
                .contentType(MediaType.APPLICATION_JSON)).andReturn();
        Assertions.assertTrue(result.getResponse().getContentAsString().contains("newBookName"));
    }

    @Test
    void editBookTest() throws Exception {
        SaveBookDto book = new SaveBookDto();
        book.setId(1);
        book.setName("newBookName");
        book.setAuthorId(1L);
        book.setGenreId(1L);

        given(bookService.update(book)).willReturn(book);

        MvcResult result = mvc.perform(put("/book").content(JsonTestConverter.mapToJson(book))
                .contentType(MediaType.APPLICATION_JSON)).andReturn();
        Assertions.assertTrue(result.getResponse().getContentAsString().contains("newBookName"));
    }


}