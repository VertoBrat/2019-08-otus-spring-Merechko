package ru.photorex.hw5.service;

import ru.photorex.hw5.model.Author;
import ru.photorex.hw5.model.Book;

import java.util.List;

public interface LibraryWormAuthorService {

    Author getAuthorById(Long id);

    List<Author> getAllAuthors();

    Author saveAuthor(Author author);

    boolean deleteAuthor(Long id);
}
