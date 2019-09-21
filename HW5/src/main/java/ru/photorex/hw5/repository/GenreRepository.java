package ru.photorex.hw5.repository;

import ru.photorex.hw5.model.Genre;

import java.util.List;

public interface GenreRepository {

    Genre getById(Long id);

    List<Genre> getAll();
}
