package ru.photorex.hw7.service;

import ru.photorex.hw7.model.Author;

import java.util.List;

public interface LibraryWormAuthorService {

    Author getAuthorById(Long id);

    List<Author> getAllAuthors();

    Author saveAuthor(Author author);

    void deleteAuthor(Long id);
}
