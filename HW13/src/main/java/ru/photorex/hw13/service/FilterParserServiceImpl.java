package ru.photorex.hw13.service;

import org.springframework.stereotype.Service;
import ru.photorex.hw13.model.Author;

@Service
public class FilterParserServiceImpl implements FilterParserService {
    @Override
    public Author parseStringToAuthor(String filterText) {
        Author author = new Author();
        String[] strings = filterText.split(" ");
        if (strings.length == 1) {
            author.setFirstName("");
            author.setLastName(filterText);
            return author;
        }
        author.setFirstName(strings[0]);
        author.setLastName(strings[1]);
        return author;
    }
}
