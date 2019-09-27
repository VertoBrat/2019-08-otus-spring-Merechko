package ru.photorex.hw6.repository;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import ru.photorex.hw6.model.Genre;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Репозиторий на основе JPA для работы с жанрами ")
@DataJpaTest
@Import({GenreRepositoryJpaImpl.class})
@Sql(scripts = {"classpath:data-test.sql"})
public class GenreRepositoryJpaImplTest {

    private static final Long GENRE_1_ID = 1L;
    private static final Long NEW_GENRE_ID = 5L;
    private static final String GENRE_NAME = "genre";

    @Autowired
    GenreRepositoryJpaImpl repository;

    @Autowired
    TestEntityManager em;

    @DisplayName(" должен возвращать жанр из базы по id")
    @Test
    void shouldReturnGenreById() {
        val actualGenre = repository.getById(GENRE_1_ID);
        val expectedGenre = em.find(Genre.class, GENRE_1_ID);
        assertThat(actualGenre).isEqualTo(expectedGenre);
    }

    @DisplayName(" должен возвращать все жанры из базы данных")
    @Test
    void shouldReturnAllGenres() {
        val genres = repository.getAll();
        assertThat(genres).hasSize(4)
                .allMatch(g -> g.getName().contains("genre"));
    }

    @DisplayName(" должен сохранять жанр в базу данных")
    @Test
    void shouldSaveGenre() {
        val actualGenre = repository.save(new Genre(null, GENRE_NAME));
        val expectedGenre = em.find(Genre.class, NEW_GENRE_ID);
        assertThat(actualGenre.getId()).isEqualTo(NEW_GENRE_ID);
        assertThat(actualGenre).isNotNull().isEqualTo(expectedGenre);
    }

    @DisplayName(" должен удалять жанр, у которого нет книг, из базы")
    @Test
    void shouldDeleteGenreIfItDontHaveAnyBooks() {
        em.persist(new Genre(null, GENRE_NAME));
        boolean deleted = repository.delete(NEW_GENRE_ID);
        assertTrue(deleted);
        assertThat(em.find(Genre.class, NEW_GENRE_ID)).isNull();
    }

    @DisplayName(" не должен удалять жанр, у которого есть книги")
    @Test
    void shouldNotDeleteGenreWithBooks() {

    }
}
