package ru.photorex.hw6.repository;


import ru.photorex.hw6.model.Author;

import java.util.List;

public interface AuthorRepository {

    Author getById(Long id);

    List<Author> getAll();

    Author save(Author author);

    boolean delete(Long id);
}
