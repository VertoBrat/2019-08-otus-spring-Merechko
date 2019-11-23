package ru.photorex.hw12.repository;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.core.MongoOperations;
import ru.photorex.hw12.model.Book;
import ru.photorex.hw12.model.Comment;
import ru.photorex.hw12.model.User;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий на основе mongoDb для работы с комментариями ")
@DataMongoTest
@EnableConfigurationProperties
@ComponentScan({"ru.photorex.hw12.config", "ru.photorex.hw12.repository", "ru.photorex.hw12.events"})
public class CommentRepositoryTest {

    private static final int ZERO_ELEMENT = 0;

    @Autowired
    MongoOperations mongoOperations;

    @Autowired
    CommentRepository commentRepository;

    @DisplayName(" должен удалять комментарий из коллекции и книги")
    @Test
    void shouldDeleteCommentByBookId() {
        int commentSize = mongoOperations.findAll(Comment.class).size();
        val comment = mongoOperations.findAll(Comment.class).get(ZERO_ELEMENT);
        val book = mongoOperations.findById(comment.getBook().getId(), Book.class);
        int expectedSizeBookComment = book.getComments().size() - 1;
        commentRepository.delete(comment);
        int expectedCommentSize = commentSize - 1;
        int activeCommentSize = mongoOperations.findAll(Comment.class).size();
        assertThat(activeCommentSize).isEqualTo(expectedCommentSize);
        val book1 = mongoOperations.findById(comment.getBook().getId(), Book.class);
        assertThat(book1.getComments().size()).isEqualTo(expectedSizeBookComment);
    }

    @DisplayName(" должен сохранить комментарий в коллекцию")
    @Test
    void shouldSaveCommentIntoCollection() {
        val books = mongoOperations.findAll(Book.class);
        val user = mongoOperations.findAll(User.class).get(0);
        int commentsSize = books.get(ZERO_ELEMENT).getComments().size();
        int expectedSize = commentsSize + 1;
        Comment comment = new Comment("text", books.get(ZERO_ELEMENT), user);
        commentRepository.save(comment);
        val booksAfterSavingComment = mongoOperations.findAll(Book.class);
        int activeCommentsSize = booksAfterSavingComment.get(ZERO_ELEMENT).getComments().size();
        assertThat(activeCommentsSize).isEqualTo(expectedSize);
    }
}
