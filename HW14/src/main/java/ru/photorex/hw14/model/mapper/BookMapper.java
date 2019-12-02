package ru.photorex.hw14.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.photorex.hw14.model.mongo.Book;
import ru.photorex.hw14.model.sql.BookTo;

@Mapper(componentModel = "spring", uses = {
        AuthorMapper.class, GenreMapper.class
})
public interface BookMapper extends BaseMapper<Book, BookTo> {

    @Mapping(target = "id", ignore = true)
    BookTo toTo(Book book);
}
