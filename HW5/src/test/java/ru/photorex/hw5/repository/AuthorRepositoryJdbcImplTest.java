package ru.photorex.hw5.repository;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.jdbc.Sql;
import ru.photorex.hw5.model.Author;
import ru.photorex.hw5.repository.jdbc.AuthorRepositoryJdbcImpl;
import ru.photorex.hw5.repository.mapper.AuthorRowMapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Репозиторий на основе Jdbc для работы с авторами ")
@DataJdbcTest
@Import({AuthorRepositoryJdbcImpl.class, AuthorRowMapper.class})
@Sql(scripts = {"classpath:data-test.sql"})
public class AuthorRepositoryJdbcImplTest {

    private static final String ID = "id";
    private static final String ENDIND_FIRST_NAME = "first_name";
    private static final String ENDIND_LAST_NAME = "last_name";
    private static final String AUTHOR_1 = "author_1_";
    private static final String AUTHOR_5 = "author_5_";
    private static final String FIRST_NAME = "firstName";
    private static final String LAST_NAME = "lastName";
    private static final String UPDATE_AUTHOR = "someName";
    private static final Long ID_1 = 1L;
    private static final Long ID_4 = 4L;
    private static final Long ZERO = 0L;
    private static final int LIST_SIZE_3 = 3;
    private static final int LIST_SIZE_4 = 4;

    @Autowired
    AuthorRepositoryJdbcImpl repository;

    @DisplayName("должен загружать всех авторов без их книг")
    @Test
    void shouldLoadAllAuthorsWithoutTheirBooks() {
        val authors = repository.getAll();
        assertThat(authors).isNotNull().hasSize(LIST_SIZE_4)
                .allMatch(a -> a.getFirstName().contains(ENDIND_FIRST_NAME))
                .allMatch(a -> a.getLastName().contains(ENDIND_LAST_NAME));
    }

    @DisplayName("должен вернуть автора по индентификатору без книг")
    @Test
    void shouldReturnAuthorByIdWithoutBooks() {
        val author = repository.getById(ID_1);
        assertThat(author).hasFieldOrPropertyWithValue(ID, ID_1)
                .hasFieldOrPropertyWithValue(FIRST_NAME, AUTHOR_1+ENDIND_FIRST_NAME)
                .hasFieldOrPropertyWithValue(LAST_NAME, AUTHOR_1+ENDIND_LAST_NAME);
    }

    @DisplayName("должен записывать в базу нового автора")
    @Test
    void shouldLoadAuthor() {
        Author author = new Author();
        author.setFirstName(AUTHOR_5 +ENDIND_FIRST_NAME);
        author.setLastName(AUTHOR_5 +ENDIND_LAST_NAME);
        Author dbAuthor = repository.save(author);
        assertThat(dbAuthor.getId()).isNotNull().isGreaterThan(ZERO);
    }

    @DisplayName("должен обновлять информацию об авторе")
    @Test
    void shouldUpdateAuthorProperties() {
        Author author = new Author(ID_1, UPDATE_AUTHOR, UPDATE_AUTHOR);
        Author dbAuthor = repository.save(author);
        assertThat(dbAuthor.getFirstName()).isEqualTo(UPDATE_AUTHOR);
    }

    @DisplayName("должен удалять автора из базы, если у него нет книг")
    @Test
    void shouldDeleteAuthorById() {
        repository.delete(ID_4);
        assertThat(repository.getAll()).hasSize(LIST_SIZE_3);
    }

    @DisplayName("должен бросать исключение при попытке удалить автора с книгами")
    @Test
    void shouldThrowExceptionIfAuthorHasBooks() {
        assertThrows(DataIntegrityViolationException.class, () -> repository.delete(ID_1));
    }
}
