package ru.photorex.hw12.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.photorex.hw12.model.Comment;

public interface CommentRepository extends MongoRepository<Comment, String> {

    void removeByBookId(String bookId);
}
