package ru.otus.spring.homework.mongock.changelog;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;
import reactor.core.publisher.Mono;
import ru.otus.spring.homework.model.entity.Author;
import ru.otus.spring.homework.model.entity.Book;
import ru.otus.spring.homework.model.entity.Comment;
import ru.otus.spring.homework.model.entity.Genre;
import ru.otus.spring.homework.repository.AuthorRepository;
import ru.otus.spring.homework.repository.BookRepository;
import ru.otus.spring.homework.repository.CommentRepository;

@ChangeLog
public class DatabaseChangelog {

    private Book captainsDaughter;
    private Book lotr;
    private Author pushkin;
    private Author tolkien;

    @ChangeSet(order = "001", id = "dropDb", author = "nsharapova", runAlways = true)
    public void dropDb(MongoDatabase db) {
        db.drop();
    }

    @ChangeSet(order = "002", id = "insertAuthors", author = "nsharapova")
    public void insertAuthors(AuthorRepository authorRepository) {
        pushkin = authorRepository.save(new Author("Aleksandr", "Pushkin")).block();
        tolkien = authorRepository.save(new Author("John", " Tolkien")).block();
    }

    @ChangeSet(order = "003", id = "insertBooks", author = "nsharapova")
    public void insertBooks(BookRepository bookRepository) {
        captainsDaughter = bookRepository.save(new Book("Captains daughter", pushkin,
                new Genre("Historical Novel"))).block();
        lotr = bookRepository.save(new Book("Lord Of The Rings", tolkien, new Genre("Fantasy"))).block();
    }

    @ChangeSet(order = "004", id = "insertComments", author = "nsharapova")
    public void insertComments(CommentRepository commentRepository) {
        commentRepository.save(new Comment(captainsDaughter, "Good book")).subscribe();
        commentRepository.save(new Comment(captainsDaughter, "Perfect book")).subscribe();
        commentRepository.save(new Comment(lotr, "The best book ever")).subscribe();
        commentRepository.save(new Comment(lotr, "Amazing")).subscribe();
    }

}
