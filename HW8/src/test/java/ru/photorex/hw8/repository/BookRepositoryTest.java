package ru.photorex.hw8.repository;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import ru.photorex.hw8.model.Author;
import ru.photorex.hw8.model.Book;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.in;

@DisplayName("Репозиторий на основе mongoDb для работы с книгами ")
public class BookRepositoryTest extends AbstractRepositoryTest {

    private static final Author AUTHOR_1 = new Author("FirstName#1", "LastName#1");
    private static final String GENRE_1 = "Genre#1";
    private static final int LIST_SIZE_2 = 2;

    @Autowired
    MongoOperations mongoOperations;

    @Autowired
    BookRepository bookRepository;

    @DisplayName(" должен получать все книги одного автора")
    @Test
    void shouldReturnListBooksByAuthor() {
        List<Book> books = bookRepository.findAllByAuthors(AUTHOR_1);
        assertThat(books).hasSize(LIST_SIZE_2);
    }

    @DisplayName(" должен получать все книги по жанру")
    @Test
    void shouldReturnListBooksByGenre() {
        List<Book> books = bookRepository.findAllByGenres(GENRE_1);
        assertThat(books).hasSize(LIST_SIZE_2);
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
}
