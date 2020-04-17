package ru.photorex.hw9.to.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import ru.photorex.hw9.model.Comment;
import ru.photorex.hw9.to.CommentTo;

@Mapper(componentModel = "spring")
public interface CommentMapper extends BaseMapper<Comment, CommentTo> {

    @Mappings( {
            @Mapping(source = "comment.book.id", target = "bookId"),
            @Mapping(source = "dateTime", dateFormat = "dd-MM-yyyy HH:mm", target = "dateTime")}
    )
    CommentTo toTo(Comment comment);
}
