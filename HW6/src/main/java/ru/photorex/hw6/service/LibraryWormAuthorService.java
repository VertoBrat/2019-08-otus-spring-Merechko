package ru.photorex.hw6.service;

import ru.photorex.hw6.model.Author;

import java.util.List;

public interface LibraryWormAuthorService {

    Author getAuthorById(Long id);

    List<Author> getAllAuthors();

    Author saveAuthor(Author author);

    boolean deleteAuthor(Long id);
}
