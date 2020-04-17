package ru.photorex.hw9.service;

import ru.photorex.hw9.model.Author;

public interface FilterParserService {

    Author parseStringToAuthor(String filterText);
}
