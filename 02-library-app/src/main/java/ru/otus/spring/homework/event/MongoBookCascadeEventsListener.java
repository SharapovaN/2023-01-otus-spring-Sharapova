package ru.otus.spring.homework.event;

import lombok.RequiredArgsConstructor;
import org.bson.Document;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.AfterDeleteEvent;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;
import ru.otus.spring.homework.model.Book;
import ru.otus.spring.homework.model.Comment;
import ru.otus.spring.homework.repository.CommentRepository;
import ru.otus.spring.homework.repository.GenreRepository;

import java.util.List;

@Component
@RequiredArgsConstructor
public class MongoBookCascadeEventsListener extends AbstractMongoEventListener<Book> {

    private final GenreRepository genreRepository;
    private final CommentRepository commentRepository;

    @Override
    public void onBeforeConvert(BeforeConvertEvent<Book> event) {
        super.onBeforeConvert(event);
        Book book = event.getSource();
        if (genreRepository.findByGenreName(book.getGenre().getGenreName()).isEmpty()) {
            genreRepository.save(book.getGenre());
        }
    }

    @Override
    public void onAfterDelete(AfterDeleteEvent<Book> event) {
        super.onAfterDelete(event);
        Document book = event.getSource();
        String bookId = book.get("_id").toString();
        List<Comment> comments = commentRepository.findAllByBookId(bookId);
        if (!comments.isEmpty()) {
            commentRepository.deleteAll(comments);
        }
    }

}
