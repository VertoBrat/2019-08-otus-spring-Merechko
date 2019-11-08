package ru.photorex.apiserver.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;
import ru.photorex.apiserver.model.Comment;

public interface CommentRepository extends ReactiveMongoRepository<Comment, String> {

    Mono<Void> removeByBook_Id(String bookId);
}
