package ru.photorex.hw8.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import ru.photorex.hw8.model.Book;
import ru.photorex.hw8.model.Comment;

import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.*;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;
import static org.springframework.data.mongodb.core.aggregation.ObjectOperators.ObjectToArray.valueOfToArray;

@RequiredArgsConstructor
public class CommentCustomRepositoryImpl implements CommentCustomRepository {

    private final MongoOperations mongoOperations;

    @Override
    public void removeCommentsOfDeletedBook(String bookId) {
        Aggregation aggregation = newAggregation(
                match(where("id").is(bookId)),
                unwind("comments"),
                project().andExclude("_id").and(valueOfToArray("comments")).as("comments_map"),
                project().and("comments_map").arrayElementAt(1).as("comment_id_map"),
                project().and("comment_id_map.v").as("comment_id"),
                lookup("comments", "comment_id", "_id", "comment"),
                project().and("comment._id").as("_id")
                        .and("comment.commentText").as("commentText")
        );

        List<Comment> comments = mongoOperations.aggregate(aggregation, Book.class, Comment.class).getMappedResults();
        comments.forEach(mongoOperations::remove);
    }
}
