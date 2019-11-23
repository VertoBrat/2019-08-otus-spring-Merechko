package ru.photorex.hw13.repository;

import ru.photorex.hw13.model.Author;
import ru.photorex.hw13.model.Book;

import java.util.List;
import java.util.Set;

public interface BookCustomRepository {

    void removeCommentsFromArrayById(String id);

    void addCommentToArray(String commentId, String bookId);

    Set<String> findAllGenres();

    Set<Author> findAllAuthors();

    List<Book> findAllFilteredPerAuthors(Author author);

    List<Book> findAllFilteredPerGenre(String genreName);
}
