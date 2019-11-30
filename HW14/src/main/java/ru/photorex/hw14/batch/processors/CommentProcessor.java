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

@Component
@RequiredArgsConstructor
public class CommentProcessor implements ItemProcessor<Comment, CommentTo> {

    private Map<String, Long> books;
    private Map<String, Long> users;



    @Override
    public CommentTo process(Comment comment) {
        CommentTo to = new CommentTo();
        to.setText(comment.getText());
        to.setDateTime(comment.getDateTime());
        UserTo user = new UserTo(users.get(comment.getUser().getUserName()));
        to.setUser(user);
        BookTo book = new BookTo(books.get(comment.getBook().getIsbn()));
        to.setBook(book);
        return to;
    }

    @BeforeStep
    public void retrieveInterStepData(StepExecution stepExecution) {
        JobExecution jobExecution = stepExecution.getJobExecution();
        ExecutionContext jobContext = jobExecution.getExecutionContext();
        this.books =(Map<String, Long>) jobContext.get("books");
        this.users = (Map<String, Long>) jobContext.get("users");
    }

}
