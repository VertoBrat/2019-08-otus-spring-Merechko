package ru.photorex.hw9.service;

import ru.photorex.hw9.to.GenreTo;

import java.util.Set;

public interface LibraryWormGenreService {

    Set<GenreTo> findAllGenres();
}
