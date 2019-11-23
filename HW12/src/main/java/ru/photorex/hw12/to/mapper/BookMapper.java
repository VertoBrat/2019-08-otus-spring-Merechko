package ru.photorex.hw12.to.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.photorex.hw12.model.Book;
import ru.photorex.hw12.to.BookTo;

@Mapper(componentModel = "spring",
        uses = {AuthorMapper.class, CommentMapper.class})
public interface BookMapper extends BaseMapper<Book, BookTo> {

    @Mapping(target = "comments", ignore = true)
    Book updateBook(BookTo to, @MappingTarget Book book);
}
