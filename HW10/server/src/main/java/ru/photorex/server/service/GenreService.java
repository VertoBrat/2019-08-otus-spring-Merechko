package ru.photorex.server.service;

import ru.photorex.server.to.GenreTo;

import java.util.Set;

public interface GenreService {

    Set<GenreTo> findAllGenres();
}
