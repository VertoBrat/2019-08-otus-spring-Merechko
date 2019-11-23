package ru.photorex.hw13.service;

import ru.photorex.hw13.to.AuthorTo;

import java.util.Set;

public interface LibraryWormAuthorService {

    Set<AuthorTo> findAllAuthors();
}
