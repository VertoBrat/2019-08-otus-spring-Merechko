package ru.photorex.apiserver.to.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import ru.photorex.apiserver.model.Book;
import ru.photorex.apiserver.to.BookTo;

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
