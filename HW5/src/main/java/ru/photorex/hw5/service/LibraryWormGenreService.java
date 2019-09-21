package ru.photorex.hw5.service;

import ru.photorex.hw5.model.Genre;

import java.util.List;

public interface LibraryWormGenreService {

   Genre getGenreById(Long id);

    List<Genre> getAllGenres();
}
