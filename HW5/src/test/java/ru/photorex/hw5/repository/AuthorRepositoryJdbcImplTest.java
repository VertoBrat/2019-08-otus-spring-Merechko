package ru.photorex.hw5.repository;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import ru.photorex.hw5.model.Author;
import ru.photorex.hw5.repository.jdbc.AuthorRepositoryJdbcImpl;
import ru.photorex.hw5.repository.mapper.AuthorRowMapper;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий на основе Jdbc для работы с авторами ")
@DataJdbcTest
@Import({AuthorRepositoryJdbcImpl.class, AuthorRowMapper.class})
@Sql(scripts = {"classpath:data-test.sql"})
public class AuthorRepositoryJdbcImplTest {

    @Autowired
    AuthorRepositoryJdbcImpl repository;

    @DisplayName("должен загружать всех авторов без их книг")
    @Test
    void shouldLoadAllAuthorsWithoutTheirBooks() {
        val authors = repository.getAll();
        assertThat(authors).isNotNull().hasSize(3)
                .allMatch(a -> a.getFirstName().contains("first_name"))
                .allMatch(a -> a.getLastName().contains("last_name"));
    }

    @DisplayName("должен вернуть автора по индентификатору без книг")
    @Test
    void shouldReturnAuthorByIdWithoutBooks() {
        val author = repository.getById(1L);
        assertThat(author).hasFieldOrPropertyWithValue("id", 1L)
                .hasFieldOrPropertyWithValue("firstName", "author_1_first_name")
                .hasFieldOrPropertyWithValue("lastName", "author_1_last_name");
    }

    @DisplayName("должен записывать в базу нового автора")
    @Test
    void shouldLoadAuthor() {
        Author author = new Author();
        author.setFirstName("author_4_first_name");
        author.setLastName("author_4_last_name");
        Author dbAuthor = repository.save(author);
        assertThat(dbAuthor.getId()).isNotNull().isGreaterThan(0L);
    }

    @DisplayName("должен обновлять информацию об авторе")
    @Test
    void shouldUpdateAuthorProperties() {
        Author author = new Author(1L, "update", "update");
        Author dbAuthor = repository.save(author);
        assertThat(dbAuthor.getFirstName()).isEqualTo("update");
    }

    @DisplayName("должен удалять автора из базы")
    @Test
    void shouldDeleteAuthorById() {
        repository.delete(3L);
        assertThat(repository.getAll()).hasSize(2);
    }
}
