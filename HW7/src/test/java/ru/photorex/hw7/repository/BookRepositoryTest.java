package ru.photorex.hw7.repository;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.jdbc.Sql;
import ru.photorex.hw7.model.Author;
import ru.photorex.hw7.model.Book;
import ru.photorex.hw7.model.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий на основе Data Jpa для работы с книгами")
@DataJpaTest
@Sql(scripts = {"classpath:data-test.sql"})
public class BookRepositoryTest {

    private static final Long ENTITY_1_ID = 1L;
    private static final Long ENTITY_2_ID = 2L;
    private static final Long ENTITY_4_ID = 4L;
    private static final int FIRST_ELEMENT_LIST = 0;
    private static final int SECOND_ELEMENT_LIST = 1;
    private static final int LIST_SIZE_1 = 1;
    private static final int LIST_SIZE_2 = 2;
    private static final String ENTITY_ID = "id";
    private static final String BOOK_TITLE = "title";

    @Autowired
    BookRepository repository;

    @Autowired
    TestEntityManager em;

    @DisplayName(" должен сохранять книгу в базу данных")
    @Test
    void shouldSaveBook() {
        List<Author> authors = List.of(new Author(ENTITY_1_ID), new Author(ENTITY_2_ID));
        Genre genre = new Genre(ENTITY_1_ID, null);
        Book actualBook = repository.save(new Book(null, BOOK_TITLE, genre, authors));
        Book expectedBook = em.find(Book.class, ENTITY_4_ID);
        assertThat(actualBook).isEqualTo(expectedBook);
    }

    @DisplayName(" должен получать лист с книгами одного автора")
    @Test
    void shouldReturnListOfBooksOneAuthor() {
        val books = repository.findBooksByAuthorOrderById(new Author(ENTITY_1_ID));
        assertThat(books).hasSize(LIST_SIZE_2);
        assertThat(books.get(FIRST_ELEMENT_LIST)).hasFieldOrPropertyWithValue(ENTITY_ID, ENTITY_1_ID);
        assertThat(books.get(SECOND_ELEMENT_LIST)).hasFieldOrPropertyWithValue(ENTITY_ID, ENTITY_2_ID);
    }

    @DisplayName(" должен получать книгу по идентификатору с авторами")
    @Test
    void shouldReturnBookWithAuthorsById() {
        Book bookDb = repository.findWithAuthorsById(ENTITY_1_ID);
        assertThat(bookDb.getAuthor()).hasSize(LIST_SIZE_2);
        assertThat(bookDb.getAuthor().get(FIRST_ELEMENT_LIST)).hasFieldOrPropertyWithValue(ENTITY_ID, ENTITY_1_ID);
        assertThat(bookDb.getAuthor().get(SECOND_ELEMENT_LIST)).hasFieldOrPropertyWithValue(ENTITY_ID, ENTITY_2_ID);
    }

    @DisplayName(" должен удалять автора из таблицы books_author")
    @Test
    void shouldDeleteAuthorFromBook() {
        repository.deleteAuthor(ENTITY_1_ID, ENTITY_1_ID);
        Book book = em.find(Book.class, ENTITY_1_ID);
        assertThat(book.getAuthor()).hasSize(LIST_SIZE_1);
    }
}
