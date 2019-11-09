package ru.photorex.apiserver.to.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import ru.photorex.apiserver.model.Book;
import ru.photorex.apiserver.model.Comment;
import ru.photorex.apiserver.to.CommentTo;

@Mapper(componentModel = "spring")
public interface CommentMapper extends BaseMapper<Comment, CommentTo> {

    @Mappings( {
            @Mapping(source = "comment.book.id", target = "bookId"),
            @Mapping(source = "dateTime", dateFormat = "dd-MM-yyyy HH:mm", target = "dateTime")}
    )
    CommentTo toTo(Comment comment);

    default Comment toEntity(CommentTo to) {
        Comment comment = new Comment();
        Book book = new Book();
        comment.setId(to.getId());
        comment.setText(to.getText());
        book.setId(to.getBookId());
        comment.setBook(book);
        return comment;
    }
}
