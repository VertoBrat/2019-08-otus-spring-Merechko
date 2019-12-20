package ru.photorex.hw16.service;

import ru.photorex.hw16.to.GenreTo;

import java.util.Set;

public interface LibraryWormGenreService {

    Set<GenreTo> findAllGenres();
}
