package ru.photorex.hw16.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.photorex.hw16.model.Comment;

public interface CommentRepository extends MongoRepository<Comment, String> {

    void removeByBookId(String bookId);

    void removeByUserId(String userId);
}
