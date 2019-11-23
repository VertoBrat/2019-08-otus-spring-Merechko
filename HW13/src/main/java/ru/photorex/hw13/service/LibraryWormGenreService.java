package ru.photorex.hw13.service;

import ru.photorex.hw13.to.GenreTo;

import java.util.Set;

public interface LibraryWormGenreService {

    Set<GenreTo> findAllGenres();
}
