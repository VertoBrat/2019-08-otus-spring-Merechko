package ru.photorex.hw12.events;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.data.mongodb.core.aggregation.Fields;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.AfterDeleteEvent;
import org.springframework.stereotype.Component;
import ru.photorex.hw12.model.User;
import ru.photorex.hw12.repository.CommentRepository;

@Component
@RequiredArgsConstructor
public class UserCascadeEventListener extends AbstractMongoEventListener<User> {

    private final CommentRepository commentRepository;

    @Override
    public void onAfterDelete(AfterDeleteEvent<User> event) {
        super.onAfterDelete(event);
        val source = event.getSource();
        if (source.get(Fields.UNDERSCORE_ID) != null) {
            val id = source.get(Fields.UNDERSCORE_ID).toString();
            commentRepository.removeByUserId(id);
        }
    }
}
