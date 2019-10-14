package ru.photorex.hw8.events;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.AfterDeleteEvent;
import org.springframework.data.mongodb.core.mapping.event.AfterSaveEvent;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;
import ru.photorex.hw8.model.Comment;
import ru.photorex.hw8.repository.BookRepository;

@Component
@RequiredArgsConstructor
public class CommentCascadeEventListener extends AbstractMongoEventListener<Comment> {

    private final BookRepository bookRepository;

    @Override
    public void onAfterSave(AfterSaveEvent<Comment> event) {
        super.onAfterSave(event);
        val comment = event.getSource();
        bookRepository.addCommentToArray(comment.getId(), comment.getBook().getId());
    }

    @Override
    public void onAfterDelete(AfterDeleteEvent<Comment> event) {
        super.onAfterDelete(event);
        val source = event.getSource();
        val id = source.get("_id").toString();
        bookRepository.removeCommentsFromArrayById(id);
    }
}
