package ru.photorex.hw9.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.photorex.hw9.model.Comment;

import java.util.List;

public interface CommentRepository extends MongoRepository<Comment, String> {

    void removeByBookId(String bookId);
}
