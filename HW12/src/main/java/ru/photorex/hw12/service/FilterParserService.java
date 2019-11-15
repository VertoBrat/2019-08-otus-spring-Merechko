package ru.photorex.hw12.service;

import ru.photorex.hw12.model.Author;

public interface FilterParserService {

    Author parseStringToAuthor(String filterText);
}
