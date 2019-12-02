package ru.photorex.hw14.batch.processors;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;
import ru.photorex.hw14.model.mongo.Comment;
import ru.photorex.hw14.model.sql.BookTo;
import ru.photorex.hw14.model.sql.CommentTo;
import ru.photorex.hw14.model.sql.UserTo;

import java.util.Map;

import static ru.photorex.hw14.batch.BatchConfig.BOOKS;
import static ru.photorex.hw14.batch.BatchConfig.USERS;

@Component
@RequiredArgsConstructor
public class CommentProcessor implements ItemProcessor<Comment, CommentTo> {

    private Map<String, Long> booksMap;
    private Map<String, Long> usersMap;

    @Override
    public CommentTo process(Comment comment) {
        CommentTo to = new CommentTo();
        to.setText(comment.getText());
        to.setDateTime(comment.getDateTime());
        UserTo user = new UserTo(usersMap.get(comment.getUser().getUserName()));
        to.setUser(user);
        BookTo book = new BookTo(booksMap.get(comment.getBook().getIsbn()));
        to.setBook(book);
        return to;
    }

    @BeforeStep
    public void retrieveInterStepData(StepExecution stepExecution) {
        JobExecution jobExecution = stepExecution.getJobExecution();
        ExecutionContext jobContext = jobExecution.getExecutionContext();
        this.booksMap =(Map<String, Long>) jobContext.get(BOOKS);
        this.usersMap = (Map<String, Long>) jobContext.get(USERS);
    }

}
