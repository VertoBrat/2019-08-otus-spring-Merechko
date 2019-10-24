package ru.photorex.hw9.events;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeDeleteEvent;
import org.springframework.stereotype.Component;
import ru.photorex.hw9.model.Book;
import ru.photorex.hw9.repository.CommentRepository;

@Component
@RequiredArgsConstructor
public class BookCascadeDeleteListener extends AbstractMongoEventListener<Book> {

    private final CommentRepository commentRepository;

    @Override
    public void onBeforeDelete(BeforeDeleteEvent<Book> event) {
        super.onBeforeDelete(event);
        val source = event.getSource();
        String id = source.get("_id").toString();
        commentRepository.removeByBookId(id);
    }
}
