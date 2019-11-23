package ru.photorex.hw12.service;

import ru.photorex.hw12.to.GenreTo;

import java.util.Set;

public interface LibraryWormGenreService {

    Set<GenreTo> findAllGenres();
}
