package ru.photorex.hw14.batch.processors;

import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;
import ru.photorex.hw14.model.mapper.BookMapper;
import ru.photorex.hw14.model.mongo.Book;
import ru.photorex.hw14.model.sql.BookTo;

import java.util.HashSet;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class BookProcessor implements ItemProcessor<Book, BookTo> {

    private static final String BOOK_IDS = "bookIds";
    private final BookMapper bookMapper;
    private StepExecution stepExecution;

    @Override
    public BookTo process(Book book) {
        BookTo to = bookMapper.toTo(book);

        ExecutionContext stepContext = this.stepExecution.getExecutionContext();
        if (stepContext.get(BOOK_IDS) != null) {
            Set<String> ids = (Set<String>) stepContext.get(BOOK_IDS);
            ids.add(book.getId());
            stepContext.put(BOOK_IDS, ids);
        } else {
            Set<String> bookIds = new HashSet<>();
            bookIds.add(book.getId());
            stepContext.put(BOOK_IDS, bookIds);
        }
        return to;
    }

    @BeforeStep
    public void saveStepExecution(StepExecution stepExecution) {
        this.stepExecution = stepExecution;
    }
}
