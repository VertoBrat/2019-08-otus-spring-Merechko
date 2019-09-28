package ru.photorex.hw6.repository;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import ru.photorex.hw6.model.Author;

import javax.persistence.PersistenceException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;


@DisplayName("Репозиторий на основе JPA для работы с авторами ")
@DataJpaTest
@Import({AuthorRepositoryJpaImpl.class})
@Sql(scripts = {"classpath:data-test.sql"})
public class AuthorRepositoryJpaImplTest {

    private static final Long AUTHOR_1_ID = 1L;
    private static final String ENDING_AUTHOR_FIRST_NAME = "_first_name";
    private static final String ENDING_AUTHOR_LAST_NAME = "_last_name";
    private static final String NEW_AUTHOR_NAME = "author_5";

    @Autowired
    AuthorRepositoryJpaImpl repository;

    @Autowired
    TestEntityManager em;

    @DisplayName(" должен получать автора по заданному id из базы данных")
    @Test
    void shouldReturnAuthorById() {
        val actualAuthor = repository.getById(AUTHOR_1_ID);
        val expectedAuthor = em.find(Author.class, AUTHOR_1_ID);
        assertThat(actualAuthor).isEqualTo(expectedAuthor);
    }

    @DisplayName(" должен возвращать всех авторов из базы данных")
    @Test
    void shouldReturnAllAuthors() {
        val actualAuthors = repository.getAll();
        assertThat(actualAuthors).hasSize(4)
                .allMatch(a -> a.getFirstName().contains(ENDING_AUTHOR_FIRST_NAME))
                .allMatch(a -> a.getLastName().contains(ENDING_AUTHOR_LAST_NAME));
    }

    @DisplayName(" должен записывать нового автора в базу данных")
    @Test
    void shouldSaveAuthorInDb() {
        Author author = new Author(null, NEW_AUTHOR_NAME + ENDING_AUTHOR_FIRST_NAME, NEW_AUTHOR_NAME + ENDING_AUTHOR_LAST_NAME);
        val newDbAuthor = repository.save(author);
        assertThat(newDbAuthor.getId()).isNotNull().isEqualTo(5L);
    }

    @DisplayName(" должен удалять автора, у которого нет книг, из базы данных по id")
    @Test
    void shouldDeleteAuthorFromDbByIdIfHeDontHaveAnyBooks() {
        em.persist(new Author(null, NEW_AUTHOR_NAME + ENDING_AUTHOR_FIRST_NAME, NEW_AUTHOR_NAME + ENDING_AUTHOR_LAST_NAME));
        boolean deleted = repository.delete(5L);
        assertTrue(deleted);
        em.clear();
        assertThat(em.find(Author.class, 5L)).isNull();
    }

    @DisplayName(" не должен удалять автора, у которого есть книги")
    @Test
    void shouldNotDeleteAuthorWithBooks() {
        assertThrows(PersistenceException.class, () -> repository.delete(1L));
    }
}
