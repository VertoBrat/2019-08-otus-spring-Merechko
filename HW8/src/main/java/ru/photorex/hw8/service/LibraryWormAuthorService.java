package ru.photorex.hw8.service;

import ru.photorex.hw8.model.Author;

public interface LibraryWormAuthorService {
    void saveAuthor(String bookId, Author author);

    void deleteAuthor(String bookId, Author author);
}
