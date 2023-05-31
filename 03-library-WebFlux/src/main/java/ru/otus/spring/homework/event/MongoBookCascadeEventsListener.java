package ru.otus.spring.homework.event;

import lombok.RequiredArgsConstructor;
import org.bson.Document;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.AfterDeleteEvent;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import ru.otus.spring.homework.model.entity.Book;
import ru.otus.spring.homework.model.entity.Comment;
import ru.otus.spring.homework.repository.CommentRepository;

@Component
@RequiredArgsConstructor
public class MongoBookCascadeEventsListener extends AbstractMongoEventListener<Book> {

    private final CommentRepository commentRepository;

    @Override
    public void onAfterDelete(AfterDeleteEvent<Book> event) {
        super.onAfterDelete(event);
        Document document = event.getSource();
        Flux<Comment> commentFlux = commentRepository.findAllByBookId(document.get("_id").toString());
        commentRepository.deleteAll(commentFlux).subscribe();
    }

}
