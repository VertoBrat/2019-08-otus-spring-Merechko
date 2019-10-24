package ru.photorex.hw9.to.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.photorex.hw9.model.Author;
import ru.photorex.hw9.to.AuthorTo;

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

    @Mapping(target = "fullName", expression = "java(new java.lang.StringBuilder().append(a.getFirstName()).append(\" \").append(a.getLastName()).toString())")
    AuthorTo toTo(Author a);
}
