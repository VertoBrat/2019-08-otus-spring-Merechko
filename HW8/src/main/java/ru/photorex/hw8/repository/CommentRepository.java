package ru.photorex.hw8.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.photorex.hw8.model.Comment;

import java.util.List;

public interface CommentRepository extends MongoRepository<Comment, String>, CommentCustomRepository {

    List<Comment> findAllByBook_Id(String bookId);
}
