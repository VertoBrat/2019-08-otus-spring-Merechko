package ru.photorex.hw14.batch.writers;

import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.stereotype.Component;
import ru.photorex.hw14.model.sql.BookTo;
import ru.photorex.hw14.repository.BookRepository;

import javax.persistence.EntityManagerFactory;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class BookWriter extends JpaItemWriter<BookTo> {

    private static final String BOOKS = "books";
    private final BookRepository bookRepository;
    private StepExecution stepExecution;

    public BookWriter(BookRepository bookRepository, EntityManagerFactory factory) {
        this.bookRepository = bookRepository;
        this.setEntityManagerFactory(factory);
    }

    @Override
    public void write(List<? extends BookTo> items) {
        if (!items.isEmpty()) {
            List<BookTo> dbList = new ArrayList<>(bookRepository.saveAll(items));
            ExecutionContext stepContext = this.stepExecution.getExecutionContext();
            Map<String, Long> bookMap = new HashMap<>();
            if (stepContext.get(BOOKS) != null) {
                bookMap = (Map<String, Long>) stepContext.get(BOOKS);
            }
            fillBookMap(dbList, bookMap);
            stepContext.put(BOOKS, bookMap);
        }
    }

    @BeforeStep
    public void saveStepExecution(StepExecution stepExecution) {
        this.stepExecution = stepExecution;
    }

    private void fillBookMap(List<BookTo> list, Map<String, Long> map) {
        for (BookTo b : list) {
            map.put(b.getIsbn(), b.getId());
        }
    }
}
