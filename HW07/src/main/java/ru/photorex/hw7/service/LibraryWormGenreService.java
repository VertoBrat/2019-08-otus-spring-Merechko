package ru.photorex.hw7.service;

import ru.photorex.hw7.model.Genre;

import java.util.List;

public interface LibraryWormGenreService {

   Genre getGenreById(Long id);

    List<Genre> getAllGenres();

    Genre saveGenre(Genre genre);

    Genre updateGenre(Genre genre);

    void deleteGenreById(Long id);
}
