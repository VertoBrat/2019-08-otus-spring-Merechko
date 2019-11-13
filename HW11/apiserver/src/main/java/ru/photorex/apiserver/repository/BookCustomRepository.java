package ru.photorex.apiserver.repository;

import com.mongodb.client.result.UpdateResult;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.photorex.apiserver.model.Author;
import ru.photorex.apiserver.model.Book;

public interface BookCustomRepository {

    Mono<UpdateResult> removeCommentsFromArrayById(String id);

    Mono<UpdateResult> addCommentToArray(String commentId, String bookId);

    Flux<String> findAllGenres();

    Flux<Author> findAllAuthors();

    Flux<Book> findAllFilteredPerAuthors(Author author);

    Flux<Book> findAllFilteredPerGenre(String genreName);
}
