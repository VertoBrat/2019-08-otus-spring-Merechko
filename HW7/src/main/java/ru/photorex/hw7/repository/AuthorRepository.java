package ru.photorex.hw7.repository;

import ru.photorex.hw7.model.Author;

import java.util.List;

public interface AuthorRepository {

    Author getById(Long id);

    List<Author> getAll();

    Author save(Author author);

    boolean delete(Long id);
}
