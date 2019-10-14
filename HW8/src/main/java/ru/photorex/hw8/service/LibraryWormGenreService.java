package ru.photorex.hw8.service;

public interface LibraryWormGenreService {

    void saveGenre(String bookId, String genre);

    void deleteGenre(String bookId, String genre);
}
