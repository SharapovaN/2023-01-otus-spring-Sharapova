package ru.otus.spring.homework.event;

import lombok.RequiredArgsConstructor;
import org.bson.Document;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.AfterDeleteEvent;
import org.springframework.data.mongodb.core.mapping.event.AfterSaveEvent;
import org.springframework.stereotype.Component;
import ru.otus.spring.homework.model.entity.Book;
import ru.otus.spring.homework.model.entity.Comment;
import ru.otus.spring.homework.repository.BookRepository;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class MongoCommentCascadeEventsListener extends AbstractMongoEventListener<Comment> {

    private final BookRepository bookRepository;

    @Override
    public void onAfterSave(AfterSaveEvent<Comment> event) {
        super.onAfterSave(event);
        Comment comment = event.getSource();
        Book book = comment.getBook();
        if (book.getComments() != null) {
            book.getComments().add(comment);
        } else {
            List<Comment> comments = new ArrayList<>();
            comments.add(comment);
            book.setComments(comments);
        }
        bookRepository.save(book);
    }

    @Override
    public void onAfterDelete(AfterDeleteEvent<Comment> event) {
        super.onAfterDelete(event);
        Document comment = event.getSource();
        String id = comment.get("_id").toString();
        bookRepository.removeCommentArrayElementById(id);
    }
}
