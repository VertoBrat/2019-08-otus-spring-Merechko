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

    private static final Long ENTITY_1_ID = 1L;
    private static final Long ENTITY_2_ID = 2L;
    private static final int LIST_SIZE_2 = 2;
    private static final String ENTITY_ID = "id";

    @Autowired
    CommentRepository repository;

    @DisplayName(" должен получать лист с комментариями одной книги")
    @Test
    void shouldReturnListOfCommentsByBook() {
        val comments = repository.findCommentsByBookOrderById(new Book(ENTITY_1_ID));
        assertThat(comments).hasSize(LIST_SIZE_2);
        assertThat(comments.get(0)).hasFieldOrPropertyWithValue(ENTITY_ID, ENTITY_1_ID);
        assertThat(comments.get(1)).hasFieldOrPropertyWithValue(ENTITY_ID, ENTITY_2_ID);
    }
}
