package ru.photorex.server.to.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.photorex.server.model.Book;
import ru.photorex.server.to.BookTo;

@Mapper(componentModel = "spring",
        uses = {AuthorMapper.class, GenreMapper.class})
public interface BookMapper extends BaseMapper<Book, BookTo> {

    @Mapping(target = "comments", ignore = true)
    Book updateBook(BookTo to, @MappingTarget Book book);
}
