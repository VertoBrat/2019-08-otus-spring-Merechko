package ru.photorex.hw16.service;

import ru.photorex.hw16.to.AuthorTo;

import java.util.Set;

public interface LibraryWormAuthorService {

    Set<AuthorTo> findAllAuthors();
}
