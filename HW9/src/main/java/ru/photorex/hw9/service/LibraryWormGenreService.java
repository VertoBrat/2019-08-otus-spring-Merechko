package ru.photorex.hw9.service;

import java.util.Set;

public interface LibraryWormGenreService {

    void saveGenre(String bookId, String genre);

    void deleteGenre(String bookId, String genre);

    Set<String> findAllGenres();
}
