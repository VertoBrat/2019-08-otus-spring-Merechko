package ru.photorex.hw6.repository;

import ru.photorex.hw6.model.Genre;

import java.util.List;

public interface GenreRepository {

    Genre getById(Long id);

    List<Genre> getAll();

    Genre save(Genre genre);

    boolean delete(Long id);
}
