package ru.photorex.hw9.service;

import ru.photorex.hw9.to.AuthorTo;

import java.util.Set;

public interface LibraryWormAuthorService {

    Set<AuthorTo> findAllAuthors();
}
