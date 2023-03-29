package ru.otus.spring.homework.mongock.changelog;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;
import ru.otus.spring.homework.model.Author;
import ru.otus.spring.homework.model.Book;
import ru.otus.spring.homework.model.Comment;
import ru.otus.spring.homework.model.Genre;
import ru.otus.spring.homework.repository.BookRepository;
import ru.otus.spring.homework.repository.CommentRepository;

@ChangeLog
public class DatabaseChangelog {

    private Book captainsDaughter;
    private Book lotr;

    @ChangeSet(order = "001", id = "dropDb", author = "nsharapova", runAlways = true)
    public void dropDb(MongoDatabase db) {
        db.drop();
    }

    @ChangeSet(order = "002", id = "insertBooks", author = "nsharapova")
    public void insertBooks(BookRepository bookRepository) {
        captainsDaughter = bookRepository.save(new Book("642414e251c1e2380fb49ab8", "Captains daughter",
                new Author("Aleksandr Pushkin"), new Genre("Historical Novel"), null));
        lotr = bookRepository.save(new Book("642414e251c1e2380fb49abb", "Lord Of The Rings",
                new Author("John Tolkien"), new Genre("Fantasy"), null));
    }

    @ChangeSet(order = "003", id = "insertComments", author = "nsharapova")
    public void insertComments(CommentRepository commentRepository) {
        commentRepository.save(new Comment("642414e251c1e2380fb49abe", captainsDaughter, "Good book"));
        commentRepository.save(new Comment("642414e251c1e2380fb49abf", captainsDaughter, "Perfect book"));
        commentRepository.save(new Comment("642414e251c1e2380fb49abg", lotr, "The best book ever"));
        commentRepository.save(new Comment("642414e251c1e2380fb49abh", lotr, "Amazing"));
    }
}
