package ru.photorex.server.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.photorex.server.model.Comment;

public interface CommentRepository extends MongoRepository<Comment, String> {

    void removeByBookId(String bookId);
}
