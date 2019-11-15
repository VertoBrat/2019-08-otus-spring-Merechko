package ru.photorex.hw12.service;

import ru.photorex.hw12.to.AuthorTo;

import java.util.Set;

public interface LibraryWormAuthorService {

    Set<AuthorTo> findAllAuthors();
}
