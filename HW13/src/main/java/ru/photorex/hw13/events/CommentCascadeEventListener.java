package ru.photorex.hw13.events;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.data.mongodb.core.aggregation.Fields;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.AfterDeleteEvent;
import org.springframework.data.mongodb.core.mapping.event.AfterSaveEvent;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;
import ru.photorex.hw13.model.Comment;
import ru.photorex.hw13.repository.BookRepository;

import java.time.LocalDateTime;

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
        if (source.get(Fields.UNDERSCORE_ID) != null) {
            val id = source.get(Fields.UNDERSCORE_ID).toString();
            bookRepository.removeCommentsFromArrayById(id);
        }
    }

    @Override
    public void onBeforeConvert(BeforeConvertEvent<Comment> event) {
        super.onBeforeConvert(event);
        val comment = event.getSource();
        comment.setDateTime(LocalDateTime.now());
    }
}
