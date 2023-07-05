package ru.otus.spring.homework.mongock.changelog;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.github.cloudyrock.mongock.driver.mongodb.springdata.v3.decorator.impl.MongockTemplate;
import com.mongodb.client.MongoDatabase;
import ru.otus.spring.homework.model.entity.Author;
import ru.otus.spring.homework.model.entity.Book;
import ru.otus.spring.homework.model.entity.Comment;
import ru.otus.spring.homework.model.entity.Genre;

@ChangeLog
public class DatabaseChangelog {

    private Book captainsDaughter;
    private Book lotr;
    private Author pushkin;
    private Author tolkien;
    private Genre fantasy;
    private Genre novel;

    @ChangeSet(order = "001", id = "dropDb", author = "nsharapova")
    public void dropDb(MongoDatabase db) {
        db.drop();
    }

    @ChangeSet(order = "002", id = "insertAuthors", author = "nsharapova")
    public void insertAuthors(MongockTemplate template) {
        pushkin = template.save(new Author(1L, "Aleksandr", "Pushkin"));
        tolkien = template.save(new Author(2L,"John", " Tolkien"));
    }

    @ChangeSet(order = "003", id = "insertGenres", author = "nsharapova")
    public void insertGenres(MongockTemplate template) {
        novel = template.save(new Genre(1L,"Historical Novel"));
        fantasy = template.save(new Genre(2L,"Fantasy"));
    }

    @ChangeSet(order = "004", id = "insertBooks", author = "nsharapova")
    public void insertBooks(MongockTemplate template) {
        captainsDaughter = template.save(new Book(1L, "Captains daughter", pushkin,
                novel, null));
        lotr = template.save(new Book(2L, "Lord Of The Rings", tolkien, fantasy, null));
    }

    @ChangeSet(order = "005", id = "insertComments", author = "nsharapova")
    public void insertComments(MongockTemplate template) {
        template.save(new Comment(1L, captainsDaughter, "Good book"));
        template.save(new Comment(2L, captainsDaughter, "Perfect book"));
        template.save(new Comment(3L, lotr, "The best book ever"));
        template.save(new Comment(4L, lotr, "Amazing"));
    }

}
