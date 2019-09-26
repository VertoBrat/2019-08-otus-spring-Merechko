package ru.photorex.hw5.repository;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import ru.photorex.hw5.config.Appconfig;
import ru.photorex.hw5.model.Author;
import ru.photorex.hw5.model.Book;
import ru.photorex.hw5.model.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий на основе Jdbc для работы с книгами ")
@DataJdbcTest
@Import({Appconfig.class, TestConfig.class})
@Sql(scripts = {"classpath:data-test.sql"})
public class BookRepositoryJdbcImplTest {

    private static final Long ID_1 = 1L;
    private static final Long ID_3 = 3L;
    private static final int LIST_SIZE = 2;
    private static final String TITLE = "title";
    private static final String TITLE_1 = "title_1";
    private static final String GENRE_1 = "genre_1";
    private static final String NAME = "name";

    @Autowired
    BookRepository repository;

    @DisplayName("должен возвращать книгу по идентификатору вместе с жанрами и авторами")
    @Test
    void shouldReturnBookByIdWithGenres() {
        Book book = repository.getById(ID_1);
        assertThat(book).isNotNull().hasFieldOrPropertyWithValue(TITLE, TITLE_1);
        assertThat(book.getGenre()).hasFieldOrPropertyWithValue(NAME, GENRE_1);
        assertThat(book.getAuthor()).hasSize(3);
    }

    @DisplayName("должан возвращать все книги")
    @Test
    void shouldReturnAllGenres() {
        val books = repository.getAll();
        assertThat(books).hasSize(3).allMatch(b -> b.getTitle().contains(TITLE));
    }

    @DisplayName("должен вернуть все книги одного автора")
    @Test
    void shouldReturnBooksOfAuthor() {
        List<Book> book = repository.getByAuthor(new Author(ID_3));
        assertThat(book).hasSize(LIST_SIZE);
    }

    @DisplayName("должен сохранить книгу с жанром и автором")
    @Test
    void shouldSaveBook() {
        Book book = new Book(null, "newTitle", new Genre(ID_1, null));
        book.setAuthor(List.of(new Author(ID_1)));
        Book dbBook = repository.save(book);
        assertThat(dbBook.getId()).isNotNull();
    }

    @DisplayName("должен удалить книгу из базы")
    @Test
    void shouldDeleteBook() {
        repository.delete(ID_1);
        assertThat(repository.getAll()).hasSize(LIST_SIZE);
    }
}
