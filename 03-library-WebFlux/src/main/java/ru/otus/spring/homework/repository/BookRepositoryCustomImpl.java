package ru.otus.spring.homework.repository;

import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import ru.otus.spring.homework.model.Book;
import ru.otus.spring.homework.model.Comment;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;
import static org.springframework.data.mongodb.core.aggregation.ObjectOperators.ObjectToArray.valueOfToArray;

@RequiredArgsConstructor
public class BookRepositoryCustomImpl implements BookRepositoryCustom {

    private final MongoTemplate mongoTemplate;

    @Override
    public void removeCommentArrayElementById(String commentId) {
        Query query = Query.query(Criteria.where("$id").is(new ObjectId(commentId)));
        Update update = new Update().pull("comments", query);
        mongoTemplate.updateMulti(new Query(), update, Book.class);
    }

    @Override
    public Set<Comment> getBookCommentsById(String bookId) {
        Aggregation aggregation = newAggregation(
                match(Criteria.where("id").is(bookId))
                , unwind("comments")
                , project().andExclude("_id").and(valueOfToArray("comments")).as("comments_map")
                , project().and("comments_map").arrayElementAt(1).as("comments_id_map")
                , project().and("comments_id_map.v").as("comments_id")
                , lookup("comment", "comments_id", "_id", "comments")
                , unwind("comments")
                , project().and("comments._id").as("_id").and("comments.comment").as("comment")
        );

        mongoTemplate.aggregate(aggregation, Book.class, Comment.class).getRawResults();
        List<Comment> comments = mongoTemplate.aggregate(aggregation, Book.class, Comment.class).getMappedResults();
        return new HashSet<>(comments);
    }
}
