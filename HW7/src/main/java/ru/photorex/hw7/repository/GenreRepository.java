package ru.photorex.hw7.repository;

import ru.photorex.hw7.model.Genre;

import java.util.List;

public interface GenreRepository {

    Genre getById(Long id);

    List<Genre> getAll();

    Genre save(Genre genre);

    boolean delete(Long id);
}
