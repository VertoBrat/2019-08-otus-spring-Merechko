package ru.photorex.hw7;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.jdbc.Sql;
import ru.photorex.hw7.model.Author;
import ru.photorex.hw7.repository.BookRepository;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий на основе Data Jpa для работы с книгами")
@DataJpaTest
@Sql(scripts = {"classpath:data-test.sql"})
public class BookRepositoryTest {

    private static final Long AUTHOR_1_ID = 1L;

    @Autowired
    BookRepository repository;

    @Autowired
    TestEntityManager em;

    @DisplayName(" должен получать лист с книгами одного автора")
    @Test
    void shouldReturnListOfBooksOneAuthor() {
        val books = repository.findBooksByAuthor(new Author(AUTHOR_1_ID));
        assertThat(books).hasSize(2);
    }
}
