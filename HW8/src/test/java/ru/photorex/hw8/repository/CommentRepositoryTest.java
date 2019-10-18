package ru.photorex.hw8.repository;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.core.MongoOperations;
import ru.photorex.hw8.model.Book;
import ru.photorex.hw8.model.Comment;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий на основе mongoDb для работы с комментариями ")
@DataMongoTest
@EnableConfigurationProperties
@ComponentScan({"ru.photorex.hw8.config", "ru.photorex.hw8.repository", "ru.photorex.hw8.events"})
public class CommentRepositoryTest {

    private static final int ZERO_ELEMENT_COLLECTION = 0;
    private static final int FIRST_ELEMENT_COLLECTION = 1;
    private static final int LIST_SIZE_1 = 1;
    private static final int LIST_SIZE_2 = 2;
    private static final String COMMENT_TEXT = "text";

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    MongoOperations mongoOperations;

    @DisplayName(" должен получать все комментарии к одной книге")
    @Test
    void shouldReturnCommentsByBook() {
        val books = mongoOperations.findAll(Book.class);
        val book = books.get(ZERO_ELEMENT_COLLECTION);
        val bookId = book.getId();
        List<Comment> comments = commentRepository.findAllByBook_Id(bookId);
        assertThat(comments).hasSize(LIST_SIZE_1);
        assertThat(comments.get(ZERO_ELEMENT_COLLECTION)).hasFieldOrPropertyWithValue(COMMENT_TEXT, "Text#1");
    }

    @DisplayName(" должен сохранять комментарий в коллекции")
    @Test
    void shouldSaveComment() {
        val books = mongoOperations.findAll(Book.class);
        val book = books.get(ZERO_ELEMENT_COLLECTION);
        Comment comment = new Comment("TestComment", book);
        commentRepository.save(comment);
        val bookWithNewComment = mongoOperations.findById(book.getId(), Book.class);
        assertThat(bookWithNewComment.getComments()).hasSize(LIST_SIZE_2);
        assertThat(bookWithNewComment.getComments().get(FIRST_ELEMENT_COLLECTION)).hasFieldOrPropertyWithValue(COMMENT_TEXT, "TestComment");
    }

    @DisplayName(" должен удалять комментарии при удалении книги")
    @Test
    void shouldDeleteCommentOfDeletedBook() {
        val books = mongoOperations.findAll(Book.class);
        val book = books.get(FIRST_ELEMENT_COLLECTION);
        mongoOperations.remove(book);
        List<Comment> comments = commentRepository.findAll();
        assertThat(comments).hasSize(LIST_SIZE_2);
    }
}
