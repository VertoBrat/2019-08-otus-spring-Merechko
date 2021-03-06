package ru.photorex.hw7.repository;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.jdbc.Sql;
import ru.photorex.hw7.model.Author;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий на основе Data Jpa для работы с авторами")
@DataJpaTest
@Sql(scripts = {"classpath:data-test.sql"})
public class AuthorRepositoryTest {

    private static final String TEST_FIRST_NAME = "first_name";
    private static final String TEST_LAST_NAME = "last_name";
    private static final String AUTHOR_1_FIRST_NAME = "author_1_first_name";
    private static final String AUTHOR_1_LAST_NAME = "author_1_last_name";
    private static final Long AUTHOR_ID_1 = 1L;
    private static final Long AUTHOR_ID_5 = 5L;

    @Autowired
    AuthorRepository repository;

    @Autowired
    TestEntityManager em;

    @DisplayName(" должен сохранять автора в базе данных")
    @Test
    void shouldSaveAuthor() {
        val testAuthor = new Author(null, TEST_FIRST_NAME, TEST_LAST_NAME);
        val actualAuthor = repository.save(testAuthor);
        val expectedAuthor = em.find(Author.class, AUTHOR_ID_5);
        assertThat(actualAuthor).isEqualTo(expectedAuthor);
    }

    @DisplayName(" должен получать автора из базы данных")
    @Test
    void shouldReturnAuthorById() {
        Optional<Author> author = repository.findById(AUTHOR_ID_1);
        assertThat(author).isPresent().hasValueSatisfying( a -> {
            assertThat(a.getId()).isEqualTo(AUTHOR_ID_1);
            assertThat(a.getFirstName()).isEqualTo(AUTHOR_1_FIRST_NAME);
            assertThat(a.getLastName()).isEqualTo(AUTHOR_1_LAST_NAME);
        });
    }
}
