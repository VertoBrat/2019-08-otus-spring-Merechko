package ru.photorex.hw14.model.mapper;

import org.mapstruct.Mapper;
import ru.photorex.hw14.model.mongo.Comment;
import ru.photorex.hw14.model.sql.CommentTo;

import java.util.List;

@Mapper(componentModel = "spring", uses = {
        BookMapper.class, UserMapper.class
})
public interface CommentMapper extends BaseMapper<Comment, CommentTo> {

    CommentTo toTo(Comment comment);

    List<CommentTo> toListTo(List<Comment> entityList);
}
