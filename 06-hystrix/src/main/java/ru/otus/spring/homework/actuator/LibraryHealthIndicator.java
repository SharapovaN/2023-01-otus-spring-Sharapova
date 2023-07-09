package ru.otus.spring.homework.actuator;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;
import ru.otus.spring.homework.model.entity.Book;
import ru.otus.spring.homework.repository.BookRepository;

import java.util.List;

@Component
@RequiredArgsConstructor
public class LibraryHealthIndicator implements HealthIndicator {

    private final BookRepository bookRepository;

    @Override
    public Health health() {
        List<Book> books = bookRepository.findAll();

        if (books.size() > 0) {
            return Health.up().withDetail("message", "Everything ok. Books in the Library").build();
        } else {
            return Health.down().withDetail("message", "Something goes wrong. No books in the Library").build();
        }
    }
}
