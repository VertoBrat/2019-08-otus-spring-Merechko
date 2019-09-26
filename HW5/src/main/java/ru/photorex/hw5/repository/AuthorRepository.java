package ru.photorex.hw5.repository;

import ru.photorex.hw5.model.Author;

import java.util.List;

public interface AuthorRepository {

    Author getById(Long id);

    List<Author> getAll();

    Author save(Author author);

    boolean delete(Long id);
}
