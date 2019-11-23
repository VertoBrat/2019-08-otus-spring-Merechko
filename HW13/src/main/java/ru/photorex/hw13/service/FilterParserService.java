package ru.photorex.hw13.service;

import ru.photorex.hw13.model.Author;

public interface FilterParserService {

    Author parseStringToAuthor(String filterText);
}
