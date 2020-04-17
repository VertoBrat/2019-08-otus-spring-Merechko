package ru.photorex.hw9.to.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.photorex.hw9.model.Book;
import ru.photorex.hw9.to.BookTo;

@Mapper(componentModel = "spring",
        uses = {AuthorMapper.class, CommentMapper.class})
public interface BookMapper extends BaseMapper<Book, BookTo> {

    @Mapping(target = "comments", ignore = true)
    Book updateBook(BookTo to, @MappingTarget Book book);
}
