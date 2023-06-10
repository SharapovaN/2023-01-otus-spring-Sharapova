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
import ru.otus.spring.homework.model.dto.BookDto;
import ru.otus.spring.homework.model.dto.SaveBookDto;
import ru.otus.spring.homework.model.entity.Author;
import ru.otus.spring.homework.model.entity.Genre;
import ru.otus.spring.homework.service.AuthorService;
import ru.otus.spring.homework.service.BookService;
import ru.otus.spring.homework.service.GenreService;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(BookController.class)
@EnableGlobalMethodSecurity(prePostEnabled = true)
class BookControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private BookService bookService;

    @MockBean
    private AuthorService authorService;

    @MockBean
    private GenreService genreService;


    @WithMockUser(
            username = "user"
    )
    @Test
    void getAllBooksTest() throws Exception {
        List<BookDto> books = List.of(new BookDto(1, "bookName", "authorName", "genreName", null),
                new BookDto(2, "bookName2", "authorName2", "genreName2", null));

        given(bookService.getAll()).willReturn(books);

        mvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(view().name("books"))
                .andExpect(model().attribute("books", hasSize(2)));
    }

    @WithMockUser(
            username = "user",
            authorities = {"ROLE_USER"}
    )
    @Test
    void getBookTest() throws Exception {
        BookDto bookDto = new BookDto(1, "bookName", "authorName", "genreName", null);

        given(bookService.getBookDtoById(1)).willReturn(bookDto);

        mvc.perform(get("/book/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(view().name("books"))
                .andExpect(model().attribute("books", List.of(bookDto)));
    }

    @WithMockUser(
            username = "user",
            authorities = {"ROLE_USER"}
    )
    @Test
    void getBookWithCommentsTest() throws Exception {
        List<String> comments = List.of("comment", "comment1");
        BookDto bookDto = new BookDto(1, "bookName", "authorName", "genreName", comments);

        given(bookService.getBookWithComments(1)).willReturn(bookDto);

        mvc.perform(get("/book-with-comments/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(view().name("book-with-comments"))
                .andExpect(model().attribute("book", bookDto));
    }

    @WithMockUser(
            username = "user",
            authorities = {"ROLE_ADMIN"}
    )
    @Test
    void createBookPageTest() throws Exception {
        List<Author> authors = List.of(new Author(1, "name", "surname"));
        List<Genre> genres = List.of(new Genre(1, "genre"));

        given(authorService.getAuthorsList()).willReturn(authors);
        given(genreService.getGenresList()).willReturn(genres);

        mvc.perform(get("/create"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(view().name("create"))
                .andExpect(model().attribute("authors", authors));
    }

    @WithMockUser(
            username = "user",
            authorities = {"ROLE_ADMIN"}
    )
    @Test
    void editBookPageTest() throws Exception {
        List<Author> authors = List.of(new Author(1, "name", "surname"));
        List<Genre> genres = List.of(new Genre(1, "genre"));
        SaveBookDto bookDto = new SaveBookDto(1L, 1L);
        bookDto.setId(1);
        bookDto.setName("newName");

        given(authorService.getAuthorsList()).willReturn(authors);
        given(genreService.getGenresList()).willReturn(genres);
        given(bookService.getSaveBookDtoById(1)).willReturn(bookDto);

        mvc.perform(get("/edit?id=1"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(view().name("edit"))
                .andExpect(model().attribute("authors", authors));
    }

    @WithMockUser(
            username = "user",
            authorities = {"ROLE_ADMIN"}
    )
    @Test
    void deleteBookPageTest() throws Exception {
        mvc.perform(get("/delete?id=1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"));
    }

    @WithMockUser(
            username = "lib",
            authorities = {"ROLE_ADMIN"}
    )
    @Test
    void createBook() throws Exception {
        mvc.perform(post("/create").with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"));
    }

    @WithMockUser(
            username = "lib",
            authorities = {"ROLE_ADMIN"}
    )
    @Test
    void editBook() throws Exception {
        mvc.perform(post("/edit").with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"));
    }

    @Test
    void getBookUnauthorizedTest() throws Exception {
        BookDto bookDto = new BookDto(1, "bookName", "authorName", "genreName", null);

        given(bookService.getBookDtoById(1)).willReturn(bookDto);

        mvc.perform(get("/book/1"))
                .andExpect(status().is4xxClientError());
    }

}