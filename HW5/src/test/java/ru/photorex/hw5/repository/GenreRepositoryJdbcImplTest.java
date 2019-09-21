package ru.photorex.hw5.repository;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import ru.photorex.hw5.repository.jdbc.GenreRepositoryJdbcImpl;
import ru.photorex.hw5.repository.mapper.GenreRowMapper;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий на основе Jdbc для работы с жанрами ")
@DataJdbcTest
@Import({GenreRepositoryJdbcImpl.class, GenreRowMapper.class})
@Sql(scripts = {"classpath:data-test.sql"})
public class GenreRepositoryJdbcImplTest {

    @Autowired
    GenreRepositoryJdbcImpl repository;

    @DisplayName("должен загружать все жанры")
    @Test
    void shouldLoadAllGenres() {
        val genres = repository.getAll();
        assertThat(genres).isNotNull().hasSize(3)
                .allMatch(a -> a.getName().contains("genre"));
    }

    @DisplayName("должен вернуть жанр по индентификатору")
    @Test
    void shouldReturnAuthorByIdWithoutBooks() {
        val genre = repository.getById(1L);
        assertThat(genre).hasFieldOrPropertyWithValue("id", 1L)
                .hasFieldOrPropertyWithValue("name", "genre_1");
    }
}
