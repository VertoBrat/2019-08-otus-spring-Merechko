package ru.photorex.hw8.service;

import ru.photorex.hw8.model.Author;

import java.util.Set;

public interface LibraryWormAuthorService {
    void saveAuthor(String bookId, Author author);

    void deleteAuthor(String bookId, Author author);

    Set<Author> findAllAuthors();
}
