package ru.photorex.hw16.to.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import ru.photorex.hw16.model.Comment;
import ru.photorex.hw16.to.CommentTo;

@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface CommentMapper extends BaseMapper<Comment, CommentTo> {

    @Mappings( {
            @Mapping(source = "comment.book.id", target = "bookId"),
            @Mapping(source = "dateTime", dateFormat = "dd-MM-yyyy HH:mm", target = "dateTime")}
    )
    CommentTo toTo(Comment comment);

    @Mapping(target = "user", ignore = true)
    Comment toEntity(CommentTo to);
}
