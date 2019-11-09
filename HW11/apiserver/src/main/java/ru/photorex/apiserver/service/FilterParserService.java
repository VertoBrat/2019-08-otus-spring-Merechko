package ru.photorex.apiserver.service;

import ru.photorex.apiserver.model.Author;

public interface FilterParserService {

    Author parseStringToAuthor(String filterText);
}
