package ru.otus.spring.homework.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.homework.exception.BookNotFoundException;
import ru.otus.spring.homework.model.dto.BookDto;
import ru.otus.spring.homework.model.dto.SaveBookDto;
import ru.otus.spring.homework.model.entity.Book;
import ru.otus.spring.homework.repository.BookRepository;
import ru.otus.spring.homework.utils.ModelConverter;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AuthorService authorService;
    private final GenreService genreService;

    @HystrixCommand(commandKey = "findAllBooks", fallbackMethod = "getUndefinedBooksList")
    @Override
    public List<BookDto> getAll() {
        return bookRepository.findAll().stream().map(ModelConverter::toBookDto).collect(Collectors.toList());
    }

    @HystrixCommand(commandKey = "findBookById", fallbackMethod = "getUndefinedBook")
    @Override
    public BookDto getBookDtoById(long id) {
        return bookRepository.findById(id).map(ModelConverter::toBookDto)
                .orElseThrow(() -> new BookNotFoundException(id));
    }

    @Override
    public Book getById(long id) {
        return bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException(id));
    }

    @Override
    public SaveBookDto getSaveBookDtoById(long id) {
        return bookRepository.findById(id).map(ModelConverter::toSaveBookDto)
                .orElseThrow(() -> new BookNotFoundException(id));
    }

    @HystrixCommand(commandKey = "findBookWithComments", fallbackMethod = "getUndefinedBookWithComments")
    @Override
    @Transactional(readOnly = true)
    public BookDto getBookWithComments(long id) {
        Optional<Book> optionalBook = bookRepository.findById(id);
        return optionalBook.map(ModelConverter::toBookWithCommentsDto)
                .orElseThrow(() -> new BookNotFoundException(id));
    }

    @Override
    public Book create(SaveBookDto bookDto) {
        Book book = new Book(bookDto.getName());
        book.setAuthor(authorService.getById(bookDto.getAuthorId()));
        book.setGenre(genreService.getById(bookDto.getGenreId()));
        return bookRepository.save(book);
    }

    @Override
    public void deleteById(long id) {
        if (checkBookExists(id)) {
            bookRepository.delete(new Book(id));
        } else {
            throw new BookNotFoundException(id);
        }
    }

    @Override
    public Book update(SaveBookDto bookDto) {
        Optional<Book> optionalBook = bookRepository.findById(bookDto.getId());
        if (optionalBook.isPresent()) {
            Book updateBook = optionalBook.get();
            updateBook.setBookName(bookDto.getName());
            updateBook.setAuthor(authorService.getById(bookDto.getAuthorId()));
            updateBook.setGenre(genreService.getById(bookDto.getGenreId()));
            return bookRepository.save(updateBook);
        }
        throw new BookNotFoundException(bookDto.getId());
    }

    @Override
    public boolean checkBookExists(long id) {
        return bookRepository.existsById(id);
    }


    private List<BookDto> getUndefinedBooksList() {
        return List.of(new BookDto(0, "N/A", "N/A", "N/A", null));
    }

    private BookDto getUndefinedBook(long bookId) {
        return new BookDto(bookId, "N/A", "N/A", "N/A", null);
    }

    private BookDto getUndefinedBookWithComments(long bookId) {
        return new BookDto(bookId, "N/A", "N/A", "N/A", List.of("null"));
    }
}
