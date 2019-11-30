package ru.photorex.hw14.model.mapper;

import org.mapstruct.Mapper;
import ru.photorex.hw14.model.mongo.Author;
import ru.photorex.hw14.model.sql.AuthorTo;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface AuthorMapper extends BaseMapper<Author, AuthorTo> {

    default AuthorTo toTo(Author author) {
        return new AuthorTo(author);
    }

    Set<AuthorTo> toSetTo(Set<Author> entitySet);
}
