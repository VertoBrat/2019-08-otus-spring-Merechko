package ru.photorex.server.to.mapper;

import org.mapstruct.*;
import ru.photorex.server.model.Book;
import ru.photorex.server.to.BookTo;

@Mapper(componentModel = "spring",
        uses = {AuthorMapper.class,
                GenreMapper.class,
                CommentMapper.class})
public interface BookMapper extends BaseMapper<Book, BookTo> {

    @Mappings({
            @Mapping(target = "comments", ignore = true),
            @Mapping(target = "id", ignore = true)
    })
    Book updateBook(BookTo to, @MappingTarget Book book);
}
