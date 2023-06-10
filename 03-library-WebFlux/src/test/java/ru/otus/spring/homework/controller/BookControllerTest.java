package ru.otus.spring.homework.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.spring.homework.model.dto.BookDto;
import ru.otus.spring.homework.model.dto.SaveBookDto;
import ru.otus.spring.homework.model.entity.Author;
import ru.otus.spring.homework.model.entity.Book;
import ru.otus.spring.homework.model.entity.Comment;
import ru.otus.spring.homework.model.entity.Genre;
import ru.otus.spring.homework.repository.BookRepository;

import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BookControllerTest {

    @MockBean
    BookRepository bookRepository;

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void getBookTest() {
        when(bookRepository.findById("1")).thenReturn(Mono.just(new Book("1", "bookName",
                new Author("1", "name", "surname"),
                new Genre("1", "genreName"), null)));

        webTestClient.get()
                .uri("/book/1")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(BookDto.class)
                .value(BookDto::getName, equalTo("bookName"));
    }

    @Test
    void getBookIfNotOkTest() {
        when(bookRepository.findById("1")).thenReturn(null);

        webTestClient.get()
                .uri("/book/1")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().is5xxServerError();
    }

    @Test
    void getAllBooksTest() {
        when(bookRepository.findAll()).thenReturn(Flux.just(new Book("1", "bookName",
                new Author("1", "name", "surname"),
                new Genre("1", "genreName"), null)));

        webTestClient.get()
                .uri("/book")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(BookDto.class)
                .hasSize(1);
    }

    @Test
    void getAllBooksIfNotOkTest() {
        when(bookRepository.findAll()).thenReturn(null);

        webTestClient.get()
                .uri("/book")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().is5xxServerError();
    }

    @Test
    void getBookWithCommentsTest() {
        when(bookRepository.findById("1")).thenReturn(Mono.just(new Book("1", "bookName",
                new Author("1", "name", "surname"),
                new Genre("1", "genreName"), List.of(new Comment()))));

        webTestClient.get()
                .uri("/book/1/comments")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(BookDto.class)
                .value(b -> b.getComments().size(), equalTo(1));
    }

    @Test
    void getBookWithCommentsIfNotOkTest() {
        when(bookRepository.findById("1")).thenReturn(null);

        webTestClient.get()
                .uri("/book/1/comments")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().is5xxServerError();
    }

    @Test
    void createBookTest() {
        SaveBookDto saveBookDto = new SaveBookDto("1", "authorName", "authorSurname",
                "1", "genreName");
        saveBookDto.setName("bookName");

        when(bookRepository.save(any(Book.class))).thenReturn(Mono.just(new Book("1", "bookName",
                new Author("1", "name", "surname"),
                new Genre("1", "genreName"), null)));

        webTestClient.post()
                .uri("/book")
                .bodyValue(saveBookDto)
                .exchange()
                .expectStatus().isOk()
                .expectBody(BookDto.class)
                .value(BookDto::getName, equalTo("bookName"));
    }

    @Test
    void updateBookTest() {
        SaveBookDto saveBookDto = new SaveBookDto("1", "authorName", "authorSurname",
                "1", "genreName");
        saveBookDto.setId("1");
        saveBookDto.setName("newBookName");

        when(bookRepository.findById("1")).thenReturn(Mono.just(new Book("1", "bookName",
                new Author("1", "name", "surname"),
                new Genre("1", "genreName"), null)));

        when(bookRepository.save(any(Book.class))).thenReturn(Mono.just(new Book("1", "newBookName",
                new Author("1", "name", "surname"),
                new Genre("1", "genreName"), null)));

        webTestClient.put()
                .uri("/book")
                .bodyValue(saveBookDto)
                .exchange()
                .expectStatus().isOk()
                .expectBody(BookDto.class)
                .value(BookDto::getName, equalTo("newBookName"));
    }

    @Test
    void updateBookIfBookNotFoundTest() {
        SaveBookDto saveBookDto = new SaveBookDto("1", "authorName", "authorSurname",
                "1", "genreName");
        saveBookDto.setId("1");
        saveBookDto.setName("newBookName");

        when(bookRepository.findById("1")).thenReturn(null);

        when(bookRepository.save(any(Book.class))).thenReturn(Mono.just(new Book("1", "newBookName",
                new Author("1", "name", "surname"),
                new Genre("1", "genreName"), null)));

        webTestClient.put()
                .uri("/book")
                .bodyValue(saveBookDto)
                .exchange()
                .expectStatus().is5xxServerError();
    }

    @Test
    void deleteBookTest() {
        when(bookRepository.deleteById("1")).thenReturn(Mono.empty());

        webTestClient.delete()
                .uri("/book/1")
                .exchange()
                .expectStatus().isOk();
    }

}