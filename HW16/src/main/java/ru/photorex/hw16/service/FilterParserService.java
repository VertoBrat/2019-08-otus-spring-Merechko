package ru.photorex.hw16.service;

import ru.photorex.hw16.model.Author;

public interface FilterParserService {

    Author parseStringToAuthor(String filterText);
}
