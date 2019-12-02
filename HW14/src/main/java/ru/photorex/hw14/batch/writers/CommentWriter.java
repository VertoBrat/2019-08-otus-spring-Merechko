package ru.photorex.hw14.batch.writers;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.AfterStep;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;
import ru.photorex.hw14.model.sql.CommentTo;
import ru.photorex.hw14.repository.CommentRepository;

import java.util.List;

import static ru.photorex.hw14.batch.BatchConfig.*;

@Component
@RequiredArgsConstructor
public class CommentWriter implements ItemWriter<CommentTo> {

    private final CommentRepository commentRepository;

    @Override
    public void write(List<? extends CommentTo> list) throws Exception {
        if (!list.isEmpty()) {
            commentRepository.saveAll(list);
        }
    }

    @AfterStep
    public void removeCustomMapsFromContext(StepExecution stepExecution) {
        ExecutionContext stepContext = stepExecution.getExecutionContext();
        stepContext.remove(BOOKS);
        stepContext.remove(BOOK_IDS);
        stepContext.remove(USERS);
    }
}
