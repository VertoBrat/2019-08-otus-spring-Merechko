package ru.photorex.hw8.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.photorex.hw8.model.Comment;

public interface CommentRepository extends MongoRepository<Comment, String>, CommentCustomRepository {
}
