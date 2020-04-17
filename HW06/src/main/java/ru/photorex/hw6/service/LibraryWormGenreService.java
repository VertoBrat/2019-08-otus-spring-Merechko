package ru.photorex.hw6.service;

import ru.photorex.hw6.model.Genre;

import java.util.List;

public interface LibraryWormGenreService {

   Genre getGenreById(Long id);

    List<Genre> getAllGenres();

    Genre saveGenre(Genre genre);

    boolean deleteGenreById(Long id);
}
