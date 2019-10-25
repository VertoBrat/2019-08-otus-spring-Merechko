package ru.photorex.server.to.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.photorex.server.model.Author;
import ru.photorex.server.to.AuthorTo;

@Mapper(componentModel = "spring")
public interface AuthorMapper extends BaseMapper<Author, AuthorTo> {

    default Author toEntity(AuthorTo to) {
        String[] names = to.getFullName().split(" ");
        Author author = new Author();
        if (names.length == 1) {
            author.setFirstName("");
            author.setLastName(names[0]);
        } else {
            author.setFirstName(names[0]);
            author.setLastName(names[1]);
        }
        return author;
    }

    default AuthorTo toTo(Author a) {
        AuthorTo to = new AuthorTo();
        to.setFullName(a.getFirstName() + " " + a.getLastName());
        return to;
    }
}
