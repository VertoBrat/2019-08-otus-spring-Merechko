package ru.photorex.hw7;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import ru.photorex.hw7.model.Book;
import ru.photorex.hw7.repository.CommentRepository;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий на основе Data Jpa для работы с комментариями")
@DataJpaTest
@Sql(scripts = {"classpath:data-test.sql"})
public class CommentRepositoryTest {

    private static final Long BOOK_1_ID = 1L;

    @Autowired
    CommentRepository repository;

    @DisplayName(" должен получать лист с комментариями одной книги")
    @Test
    void shouldReturnListOfCommentsByBook() {
        val comments = repository.findCommentsByBookOrderById(new Book(BOOK_1_ID));
        assertThat(comments).hasSize(2);
    }
}
