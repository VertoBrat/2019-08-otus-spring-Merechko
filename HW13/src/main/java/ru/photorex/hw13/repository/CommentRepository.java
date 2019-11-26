package ru.photorex.hw13.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import ru.photorex.hw13.model.Comment;

public interface CommentRepository extends MongoRepository<Comment, String> {

    void removeByBookId(String bookId);

    void removeByUserId(String userId);

    @PreAuthorize("hasPermission(#comment, 'DELETE')")
    void delete(Comment comment);
}
