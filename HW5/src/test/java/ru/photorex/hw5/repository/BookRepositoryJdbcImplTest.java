package ru.photorex.hw5.repository;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import ru.photorex.hw5.model.Author;
import ru.photorex.hw5.model.Book;
import ru.photorex.hw5.model.Genre;
import ru.photorex.hw5.repository.jdbc.BookRepositoryJdbcImpl;
import ru.photorex.hw5.repository.mapper.BookRowMapper;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий на основе Jdbc для работы с книгами ")
@DataJdbcTest
@Import({BookRepositoryJdbcImpl.class, BookRowMapper.class})
@Sql(scripts = {"classpath:data-test.sql"})
public class BookRepositoryJdbcImplTest {

    @Autowired
    BookRepositoryJdbcImpl repository;

    @DisplayName("должен возвращать книгу по идентификатору вместе с жанрами и авторами")
    @Test
    void shouldReturnBookByIdWithGenres() {
        Book book = repository.getById(1L);
        assertThat(book).isNotNull().hasFieldOrPropertyWithValue("title", "title_1");
        assertThat(book.getGenre()).hasFieldOrPropertyWithValue("name", "genre_1");
        assertThat(book.getAuthor()).hasSize(3);
    }

    @DisplayName("должан возвращать все книги")
    @Test
    void shouldReturnAllGenres() {
        val books = repository.getAll();
        assertThat(books).hasSize(3).allMatch(b -> b.getTitle().contains("title"));
    }

    @DisplayName("должен вернуть все книги одного автора")
    @Test
    void shouldReturnBooksOfAuthor() {
        List<Book> book = repository.getByAuthor(new Author(3L));
        assertThat(book).hasSize(2);
    }

    @DisplayName("должен сохранить книгу с жанром и автором")
    @Test
    void shouldSaveBook() {
        Book book = new Book();
        book.setTitle("newTitle");
        book.setGenre(new Genre("newGenre"));
        book.setAuthor(List.of(new Author(1L)));
        Book dbBook = repository.save(book);
        assertThat(dbBook.getId()).isNotNull();
    }

    @DisplayName("должен удалить книгу из базы")
    @Test
    void shouldDeleteBook() {
        repository.delete(1L);
        assertThat(repository.getAll()).hasSize(2);
    }
}
