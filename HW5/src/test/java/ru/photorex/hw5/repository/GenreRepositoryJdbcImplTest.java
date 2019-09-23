package ru.photorex.hw5.repository;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.jdbc.Sql;
import ru.photorex.hw5.config.Appconfig;
import ru.photorex.hw5.model.Genre;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Репозиторий на основе Jdbc для работы с жанрами ")
@DataJdbcTest
@Import({Appconfig.class, TestConfig.class})
@Sql(scripts = {"classpath:data-test.sql"})
public class GenreRepositoryJdbcImplTest {

    private static final Long ID_1 = 1L;
    private static final Long ID_3 = 3L;
    private static final Long ID_4 = 4L;
    private static final int LIST_SIZE_3 = 3;
    private static final int LIST_SIZE_4 = 4;
    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String GENRE = "genre";
    private static final String GENRE_1 = "genre_1";

    @Autowired
    GenreRepository repository;

    @DisplayName("должен загружать все жанры")
    @Test
    void shouldLoadAllGenres() {
        val genres = repository.getAll();
        assertThat(genres).isNotNull().hasSize(LIST_SIZE_4)
                .allMatch(a -> a.getName().contains(GENRE));
    }

    @DisplayName("должен вернуть жанр по индентификатору")
    @Test
    void shouldReturnAuthorByIdWithoutBooks() {
        val genre = repository.getById(ID_1);
        assertThat(genre).hasFieldOrPropertyWithValue(ID, ID_1)
                .hasFieldOrPropertyWithValue(NAME, GENRE_1);
    }

    @DisplayName("должен сохранять жанр в базу")
    @Test
    void shouldSaveGenre() {
        Genre genre = new Genre(null, "newGenre");
        Genre dbGenre = repository.save(genre);
        assertThat(dbGenre.getId()).isNotNull().isGreaterThan(ID_4);
    }

    @DisplayName("должен удалять жанр из базы, если на него не ссылаются книги")
    @Test
    void shouldDeleteGenre() {
        repository.delete(ID_4);
        assertThat(repository.getAll()).hasSize(LIST_SIZE_3);
    }

    @DisplayName("должен бросать исключение при попытке удалить жанр, на который ссылаются книги")
    @Test
    void shouldThrowExceptionIfBooksLinksGenre() {
        assertThrows(DataIntegrityViolationException.class, () -> repository.delete(ID_1));
    }
}
