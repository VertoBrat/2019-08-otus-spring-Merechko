package ru.photorex.server.service;

import ru.photorex.server.to.AuthorTo;

import java.util.Set;

public interface AuthorService {

    Set<AuthorTo> findAllAuthors();
}
