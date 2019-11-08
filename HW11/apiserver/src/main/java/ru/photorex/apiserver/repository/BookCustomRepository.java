package ru.photorex.apiserver.repository;

import reactor.core.publisher.Flux;
import ru.photorex.apiserver.model.Author;
import ru.photorex.apiserver.model.Book;

public interface BookCustomRepository {

    void removeCommentsFromArrayById(String id);

    void addCommentToArray(String commentId, String bookId);

    Flux<String> findAllGenres();

    Flux<Author> findAllAuthors();

    Flux<Book> findAllFilteredPerAuthors(Author author);

    Flux<Book> findAllFilteredPerGenre(String genreName);
}
