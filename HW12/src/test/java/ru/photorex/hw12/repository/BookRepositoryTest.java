package ru.photorex.hw12.repository;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.core.MongoOperations;
import ru.photorex.hw12.model.Author;
import ru.photorex.hw12.model.Book;
import ru.photorex.hw12.model.Comment;
import ru.photorex.hw12.model.User;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий на основе mongoDb для работы с книгами ")
@DataMongoTest
@EnableConfigurationProperties
@ComponentScan({"ru.photorex.hw12.config", "ru.photorex.hw12.repository", "ru.photorex.hw12.events"})
public class BookRepositoryTest {

    private static final Author AUTHOR_1 = new Author("FirstName#1", "LastName#1");
    private static final Author AUTHOR_2 = new Author("FirstName#2", "LastName#2");
    private static final Author AUTHOR_3 = new Author("FirstName#3", "LastName#3");
    private static final Author AUTHOR_4 = new Author("FirstName#4", "LastName#4");
    private static final String GENRE_1 = "Genre#1";
    private static final String GENRE_2 = "Genre#2";
    private static final String GENRE_3 = "Genre#3";
    private static final int LIST_SIZE_2 = 2;

    @Autowired
    MongoOperations mongoOperations;

    @Autowired
    BookRepository bookRepository;

    @DisplayName(" должен сохранять комментарий в книге")
    @Test
    void shouldSaveCommentInBookCommentsArray() {
        val books = mongoOperations.findAll(Book.class);
        val book = books.get(1);
        val user = mongoOperations.findAll(User.class).get(0);
        int commentsSize = book.getComments().size();
        int expectedCommentsSize = commentsSize + 1;
        Comment comment = new Comment("text", book, user);
        mongoOperations.save(comment);
        val bookAfterInsertComment = mongoOperations.findAll(Book.class).get(1);
        assertThat(bookAfterInsertComment.getComments().size()).isEqualTo(expectedCommentsSize);
    }

    @DisplayName(" должен удалять комментарии из книг при удалении комментария из коллекции")
    @Test
    void shouldDeleteCommentInsideBook() {
        val books = mongoOperations.findAll(Book.class);
        val book = books.get(1);
        int commentsSize = book.getComments().size();
        mongoOperations.remove(book.getComments().get(0));
        int expectedCommentsSize = commentsSize - 1;
        val bookAfterCommentDeleted = mongoOperations.findAll(Book.class).get(1);
        assertThat(bookAfterCommentDeleted.getComments().size()).isEqualTo(expectedCommentsSize);
    }

    @DisplayName(" должен возвращать все книги по фильтру жанра")
    @Test
    void shouldReturnFilteredBooksByGenreName() {
        val books = bookRepository.findAllFilteredPerGenre(GENRE_1);
        assertThat(books).hasSize(LIST_SIZE_2);
    }

    @DisplayName(" должен возвращать все книги по фильтру автора")
    @Test
    void shouldReturnFilteredBooksByAuthor() {
        val books = bookRepository.findAllFilteredPerAuthors(AUTHOR_1);
        assertThat(books).hasSize(LIST_SIZE_2);
    }

    @DisplayName(" должен получать все жанры")
    @Test
    void shouldReturnAllGenres() {
        Set<String> expectedGenres = Set.of(GENRE_1, GENRE_2, GENRE_3);
        Set<String> activeGenres = bookRepository.findAllGenres();
        assertThat(activeGenres).hasSameSizeAs(expectedGenres).isEqualTo(expectedGenres);
    }

    @DisplayName(" должен получать всех авторов")
    @Test
    void shouldReturnAllAuthors() {
        Set<Author> expectedAuthors = Set.of(AUTHOR_1, AUTHOR_2, AUTHOR_3, AUTHOR_4);
        Set<Author> activeAuthors = bookRepository.findAllAuthors();
        assertThat(activeAuthors).hasSameSizeAs(expectedAuthors).isEqualTo(expectedAuthors);
    }
}
