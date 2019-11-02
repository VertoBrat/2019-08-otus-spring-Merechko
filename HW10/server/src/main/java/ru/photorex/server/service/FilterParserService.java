package ru.photorex.server.service;

import ru.photorex.server.model.Author;

public interface FilterParserService {

    Author parseStringToAuthor(String filterText);
}
